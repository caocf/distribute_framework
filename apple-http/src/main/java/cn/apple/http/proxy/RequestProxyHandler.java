package cn.afterturn.http.proxy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.http.annotation.IRequestIgnore;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestResultEnum;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import cn.afterturn.http.entity.enums.SignTypeEnmu;
import cn.afterturn.http.entity.params.ParamsEntity;
import cn.afterturn.http.util.BaseAnnotation;
import cn.afterturn.http.util.CalculateSignUtil;
import cn.afterturn.http.util.ClassUtil;
import cn.afterturn.http.util.IJsonServer;
import cn.afterturn.http.util.ReflectorUtil;
import cn.afterturn.http.util.XMLParseUtil;

/**
 * 请求切面代理
 * 
 * @author JueYue 2014年2月20日--上午10:41:07
 */
public class RequestProxyHandler implements InvocationHandler, Serializable {

    private static final long     serialVersionUID = 6732992402113927625L;

    private static final Logger   LOGGER           = LoggerFactory
                                                       .getLogger(RequestProxyHandler.class);

    private static IRequestMethod defaltMethodParams;

    @Autowired
    private HttpClient            httpClient;
    @Autowired
    private IJsonServer           jsonServer;

    /**
     * 获取基础URL
     * 
     * @param iRequest
     * @param paramsEntityList
     * @return
     */
    private String getBaseUrl(IRequestMethod iRequest, List<ParamsEntity> paramsEntityList) {
        if (iRequest != null && StringUtils.isNotBlank(iRequest.url())) {
            return iRequest.url();
        }
        for (int i = 0, le = paramsEntityList.size(); i < le; i++) {
            if ("URL".equalsIgnoreCase(paramsEntityList.get(i).getName())) {
                return (String) paramsEntityList.get(i).getValue();
            }
        }
        throw new RuntimeException("没有找到请求地址");
    }

    private IRequestMethod getDefaltMethodParams() {
        if (defaltMethodParams != null) {
            return defaltMethodParams;
        }
        try {
            return BaseAnnotation.class.getMethod("requestMethod").getAnnotation(
                IRequestMethod.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("获取默认注解失败");
        }

    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    private Class<?> getListRealType(String genericReturnType) {
        String realType = genericReturnType.replace("java.util.List<", "").replace(">", "");
        try {
            return Class.forName(realType);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private List<ParamsEntity> getParamsEntityList(Object[] parameters, Method method,
                                                   IRequestMethod iRequest) throws Exception {
        Annotation[][] annotations = method.getParameterAnnotations();
        List<ParamsEntity> list = new ArrayList<ParamsEntity>();
        for (int i = 0, le = parameters.length; i < le; i++) {
            if (annotations[i] != null && annotations[i].length > 0
                && annotations[i][0] instanceof IRequestIgnore) {
                continue;
            }
            if (ClassUtil.isJavaClass(parameters[i].getClass())) {
                addJavaClassValue(annotations[i], parameters[i], iRequest, list);
            } else {
                addUserClassValue(annotations[i], parameters[i], iRequest, list);
            }
        }
        if (!iRequest.sign().equals(SignTypeEnmu.NULL)) {
            list.add(new ParamsEntity(iRequest.signName(), CalculateSignUtil.signCal(
                iRequest.sign(), iRequest.defaultKey(), list)));
        }
        return list;
    }

    /**
     * 添加用户自定义类型参数
     * @param annotations
     * @param object
     * @param iRequest
     * @param list
     * @throws Exception 
     */
    private void addUserClassValue(Annotation[] annotations, Object object,
                                   IRequestMethod iRequest, List<ParamsEntity> list)
                                                                                    throws Exception {
        ParamsEntity prefix = new ParamsEntity();
        for (int k = 0, ale = annotations.length; k < ale; k++) {
            if (annotations[k] instanceof IRequestParam) {
                prefix.setName(((IRequestParam) annotations[k]).value());
                prefix.setOrder(((IRequestParam) annotations[k]).order());
                prefix.setSign(((IRequestParam) annotations[k]).isSign());
            }
        }
        addUserClassValueRecycle(prefix, object, list, iRequest);
    }

    /**
     * 循环入参
     * @param prefix
     * @param object
     * @param list
     * @param iRequest 
     * @throws Exception 
     */
    private void addUserClassValueRecycle(ParamsEntity prefix, Object object,
                                          List<ParamsEntity> list, IRequestMethod iRequest)
                                                                                           throws Exception {
        ReflectorUtil reflectorUtil = ReflectorUtil.fromCache(object.getClass());
        Field filed;
        ParamsEntity entity;
        for (int i = 0; i < reflectorUtil.getFieldList().size(); i++) {
            filed = reflectorUtil.getFieldList().get(i);
            if (filed.getAnnotation(IRequestIgnore.class) != null) {
                continue;
            }
            entity = new ParamsEntity();
            if (filed.getAnnotation(IRequestParam.class) != null) {
                IRequestParam params = filed.getAnnotation(IRequestParam.class);
                entity.setName(prefix.getName() + params.value());
                entity.setOrder(params.order());
                entity.setSign(params.isSign());
            } else {
                entity.setName(prefix.getName() + filed.getName());
            }
            if (ClassUtil.isJavaClass(filed.getType())) {
                if (iRequest.isTranscoding()) {
                    entity.setValue(URLEncoder.encode(String.valueOf(reflectorUtil.getValue(object,
                        filed.getName())), iRequest.encode().getValue()));
                } else {
                    entity
                        .setValue(String.valueOf(reflectorUtil.getValue(object, filed.getName())));
                }
                list.add(entity);
            } else {
                addUserClassValueRecycle(entity, reflectorUtil.getValue(object, filed.getName()),
                    list, iRequest);
            }
        }
    }

    /**
     * 添加java 自带类型参数
     * @param annotations
     * @param parameters
     * @param iRequest
     * @param list
     * @throws Exception
     */
    private void addJavaClassValue(Annotation[] annotations, Object parameters,
                                   IRequestMethod iRequest, List<ParamsEntity> list)
                                                                                    throws Exception {
        ParamsEntity entity = new ParamsEntity();
        for (int k = 0, ale = annotations.length; k < ale; k++) {
            if (annotations[k] instanceof IRequestParam) {
                entity.setName(((IRequestParam) annotations[k]).value());
                entity.setOrder(((IRequestParam) annotations[k]).order());
                entity.setSign(((IRequestParam) annotations[k]).isSign());
            }
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new RuntimeException("这个参数没有注释名称:" + parameters.toString());
        }
        if (!"URL".equalsIgnoreCase(entity.getName())) {
            if (iRequest.isTranscoding()) {
                entity.setValue(URLEncoder.encode(String.valueOf(parameters), iRequest.encode()
                    .getValue()));
            } else {
                entity.setValue(String.valueOf(parameters));
            }
        } else {
            entity.setValue(String.valueOf(parameters));
        }
        list.add(entity);
    }

    /**
     * 拼接URL
     * 
     * @param paramsEntityList
     * @param iRequest
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getParamsForGet(String baseUrl, List<ParamsEntity> paramsEntityList,
                                   IRequestMethod iRequest) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        boolean isAddAnd = false;
        if (baseUrl.contains("?")) {
            isAddAnd = true;
        }
        for (int i = 0, le = paramsEntityList.size(); i < le; i++) {
            if ("URL".equalsIgnoreCase(paramsEntityList.get(i).getName())) {
                continue;
            }
            if (isAddAnd) {
                stringBuilder.append("&");
            } else {
                stringBuilder.append("?");
                isAddAnd = true;
            }
            stringBuilder.append(paramsEntityList.get(i).getName());
            stringBuilder.append("=");
            stringBuilder.append(paramsEntityList.get(i).getValue());
        }
        return stringBuilder.toString();
    }

    private NameValuePair[] getRequestBodyForPost(List<ParamsEntity> paramsEntityList,
                                                  HttpMethodParams httpMethodParams) {
        NameValuePair[] data = new NameValuePair[paramsEntityList.size()];
        for (int i = 0; i < paramsEntityList.size(); i++) {
            data[i] = new NameValuePair(paramsEntityList.get(i).getName(), paramsEntityList.get(i)
                .getValue());
        }
        return data;
    }

    private RequestEntity getRequestBodyForWebservice(String encode, String content,
                                                      List<ParamsEntity> paramsEntityList)
                                                                                          throws Exception {
        Collections.sort(paramsEntityList);
        Object[] paramsArr = new Object[paramsEntityList.size()];
        for (int i = 0, le = paramsEntityList.size(); i < le; i++) {
            paramsArr[i] = paramsEntityList.get(i).getValue();
        }
        String soapXml = String.format(content, paramsArr);
        byte bytes[] = soapXml.getBytes(encode);
        InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);
        RequestEntity requestEntity = new InputStreamRequestEntity(inputStream, bytes.length,
            "application/soap+xml; charset=" + encode);
        return requestEntity;
    }

    private String getResponseResult(IRequestMethod iRequest, List<ParamsEntity> paramsEntityList)
                                                                                                  throws Exception {
        if (iRequest.type().equals(RequestTypeEnum.POST)) {
            return getResponseResultByPost(iRequest, paramsEntityList);
        } else if (iRequest.type().equals(RequestTypeEnum.GET)) {
            return getResponseResultByGet(iRequest, paramsEntityList);
        } else if (iRequest.type().equals(RequestTypeEnum.WEBSERVICE)) {
            return getResponseResultByWebService(iRequest, paramsEntityList);
        } else if (iRequest.type().equals(RequestTypeEnum.REST)) {
            return getResponseResultByRest(iRequest, paramsEntityList);
        }
        return null;
    }

    /**
     * 获取数据get请求
     * 
     * @param iRequest
     * @param paramsEntityList
     * @return
     */
    private String getResponseResultByGet(IRequestMethod iRequest,
                                          List<ParamsEntity> paramsEntityList) throws IOException {
        String url = getParamsForGet(getBaseUrl(iRequest, paramsEntityList), paramsEntityList,
            iRequest);
        return getResponseResultByUrl(iRequest, url);
    }

    /**
     * 获取数据 post请求
     * 
     * @param iRequest
     * @param paramsEntityList
     * @return
     */
    private String getResponseResultByPost(IRequestMethod iRequest,
                                           List<ParamsEntity> paramsEntityList) {
        PostMethod postMethod = new PostMethod(getBaseUrl(iRequest, paramsEntityList));
        postMethod.getParams().setSoTimeout(iRequest.connectTimeout() * 1000);
        postMethod.getParams().setContentCharset(iRequest.encode().getValue());
        postMethod.setRequestBody(getRequestBodyForPost(paramsEntityList, postMethod.getParams()));
        try {
            int status = httpClient.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                return postMethod.getResponseBodyAsString();
            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("请求状态{},参数{},信息{}", status, jsonServer.toJson(paramsEntityList),
                        postMethod.getResponseBodyAsString());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return null;
    }

    /**
     * Rest请求 2014年5月15日
     * 
     * @param iRequest
     * @param paramsEntityList
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getResponseResultByRest(IRequestMethod iRequest,
                                           List<ParamsEntity> paramsEntityList)
                                                                               throws UnsupportedEncodingException {
        Collections.sort(paramsEntityList);
        String url = getBaseUrl(iRequest, paramsEntityList);
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        for (int i = 0; i < paramsEntityList.size(); i++) {
            if (!paramsEntityList.get(i).getName().equalsIgnoreCase("URL")) {
                url += "/" + paramsEntityList.get(i).getValue();
            }
        }
        return getResponseResultByUrl(iRequest, url);
    }

    private String getResponseResultByUrl(IRequestMethod iRequest, String url) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("请求URL:{}", url);
        }
        GetMethod getMethod = null;
        try {
            getMethod = new GetMethod(url);
            getMethod.getParams().setSoTimeout(iRequest.connectTimeout() * 1000);
            getMethod.getParams().setContentCharset(iRequest.encode().getValue());
            int status = httpClient.executeMethod(getMethod);
            if (status == HttpStatus.SC_OK) {
                return getMethod.getResponseBodyAsString();
            } else {
                LOGGER.error("请求返回结果{},请求地址{},请求参数{}", status, url);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            // 释放连接
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return null;
    }

    /**
     * 获取数据 webserver请求
     * 
     * @param iRequest
     * @param paramsEntityList
     * @return
     * @throws Exception 
     */
    private String getResponseResultByWebService(IRequestMethod iRequest,
                                                 List<ParamsEntity> paramsEntityList)
                                                                                     throws Exception {
        PostMethod postMethod = new PostMethod(getBaseUrl(iRequest, paramsEntityList));
        postMethod.getParams().setSoTimeout(iRequest.connectTimeout() * 1000);
        postMethod.getParams().setContentCharset(iRequest.encode().getValue());
        postMethod.setRequestEntity(getRequestBodyForWebservice(iRequest.encode().getValue(),
            iRequest.webserver(), paramsEntityList));
        try {
            int status = httpClient.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                return postMethod.getResponseBodyAsString();
            } else {
                LOGGER.error("请求返回结果{},请求地址{},请求参数{}", status,
                    getBaseUrl(iRequest, paramsEntityList), jsonServer.toJson(paramsEntityList));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return null;
    }

    private Object getReturnObject(Method method, String responseResult, IRequestMethod iRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("responseResult is  : {}", responseResult);
        }
        if (responseResult == null) {
            return null;
        }
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            if (returnType.isAssignableFrom(String.class)) {
                return responseResult;
            }
            // 基本类型
            if (returnType.isPrimitive()) {
                Number number = jsonServer.parseJson(responseResult, BigDecimal.class);
                if ("int".equals(returnType.getSimpleName())) {
                    return number.intValue();
                }
                if ("long".equals(returnType.getSimpleName())) {
                    return number.longValue();
                }
                if ("double".equals(returnType.getSimpleName())) {
                    return number.doubleValue();
                }
            }
            if (iRequest.result().equals(RequestResultEnum.JSON)) {
                // List类型
                if (returnType.isAssignableFrom(List.class)) {
                    returnType = getListRealType(method.getGenericReturnType().toString());
                    return jsonServer.parseJsonList(responseResult, returnType);
                }
                return jsonServer.parseJson(responseResult, returnType);
            } else {
                return XMLParseUtil.getEntity(responseResult, returnType, iRequest.encode()
                    .getValue());
            }
        }
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            IRequestMethod iRequest = method.getAnnotation(IRequestMethod.class);
            if (iRequest == null) {
                iRequest = getDefaltMethodParams();
            }
            List<ParamsEntity> paramsEntityList = getParamsEntityList(args, method, iRequest);
            return getReturnObject(method, getResponseResult(iRequest, paramsEntityList), iRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public void setHttpClient(HttpClient httpclient) {
        this.httpClient = httpclient;
    }

}
