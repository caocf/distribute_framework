package org.durcframework.open.client;

import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class PostUtil {
	
	private static final String UTF8 = "UTF-8";
	
	public static String post(String url, Map<String, String> params) throws Exception {
		return post(url, params, UTF8);
	}
	
	/**
	 * post请求接口
	 * 
	 * @param url
	 *            请求的url
	 * @param params
	 *            参数Map
	 * @param encode
	 *            编码 UTF-8
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params,
			String encode) throws Exception {
		if(encode == null) {
			encode = UTF8;
		}
		 // 使用 POST 方式提交数据
		PostMethod method = new PostMethod(url);
		try {
			Set<String> keys = params.keySet();
			NameValuePair[] values = new NameValuePair[keys.size()];
			int i = 0;
			for (String key : keys) {
				NameValuePair v = new NameValuePair();
				v.setName(key);
				v.setValue(params.get(key));
				values[i++] = v;
			}
			
			method.setRequestBody(values);
			
			HttpClient client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			
			int state = client.executeMethod(method); // 返回的状态
			
			if (state != HttpStatus.SC_OK) {
				throw new Exception("HttpStatus is " + state);
			}
			
			String response = method.getResponseBodyAsString();

			response = new String(response.getBytes(encode), encode);
			
			return response; // response就是最后得到的结果
		} catch (Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
	}
	
}
