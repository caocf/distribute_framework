package cn.afterturn.http.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析XML文件 对应规则 _a转成A
 * 
 * @author JueYue 2014年2月19日--下午5:03:05
 */
@SuppressWarnings("unchecked")
public class XMLParseUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLParseUtil.class);

    /**
     * 获取实体类
     * 
     * @date 2014年2月19日
     * @param xml
     * @param clazz
     * @return
     */
    public static <T> T getEntity(String xml, Class<?> clazz, String encoding) {
        Object obj = null;
        xml = xml.trim();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("XML内容:" + xml);
        }
        try {
            obj = clazz.newInstance();
            Document document = null;
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(encoding);
            try {
                document = saxReader.read(new ByteArrayInputStream(xml.getBytes(encoding)));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage(), e);
            }
            Element root = document.getRootElement();
            List<Element> beans = root.elements();
            readThisBeanOfElement(obj, beans, clazz);
        } catch (Exception e) {
            throw new RuntimeException("XML解析异常" + e.getMessage(), e.getCause());
        }
        return (T) obj;
    }

    private static String getRealName(String name) {
        StringBuffer realName = new StringBuffer();
        char[] chars = name.toCharArray();
        for (int i = 0, le = chars.length; i < le; i++) {
            if (chars[i] == '_') {
                i++;
                realName.append(Character.toUpperCase(chars[i]));
            } else {
                realName.append(chars[i]);
            }
        }
        return realName.toString();
    }

    private static String getText(String text) {
        return text.replaceAll("\\n\\t", "");
    }

    @SuppressWarnings("rawtypes")
    private static void readThisBeanOfElement(Object obj, List<Element> beans, Class<?> clazz)
                                                                                              throws InstantiationException,
                                                                                              IllegalAccessException,
                                                                                              ClassNotFoundException {
        Map<String, Field> fields = ClassUtil.getClassFields(clazz);
        ReflectorUtil helper = ReflectorUtil.fromCache(clazz);
        String realName;
        for (Element bean : beans) {
            realName = null;
            if (fields.containsKey(getRealName(bean.getName()))) {
                realName = getRealName(bean.getName());
            } else if (fields.containsKey(bean.getName())) {
                realName = bean.getName();
            }
            if (realName != null) {
                Field field = fields.get(realName);
                if (ClassUtil.isCollection(field.getType())) {
                    List<Element> tempBeans = bean.elements();
                    List list = new ArrayList(tempBeans.size());
                    helper.setValue(obj, realName, list);
                    Class<?> tempClazz = Class.forName(field.getGenericType().toString()
                        .replace("java.util.List<", "").replace(">", ""));
                    Object temp;
                    for (int i = 0; i < tempBeans.size(); i++) {
                        temp = tempClazz.newInstance();
                        list.add(temp);
                        readThisBeanOfElement(temp, tempBeans.get(i).elements(), tempClazz);
                    }
                } else if (!ClassUtil.isJavaClass(field)) {
                    Object temp = field.getType().newInstance();
                    helper.setValue(obj, realName, temp);
                    readThisBeanOfElement(temp, bean.elements(), field.getType());
                } else {
                    helper.setValue(obj, realName, getText(bean.getText()));
                }
            }
        }
    }

}
