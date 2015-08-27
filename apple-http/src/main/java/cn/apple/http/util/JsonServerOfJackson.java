package cn.afterturn.http.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 * 
 * @author JueYue
 * @date 2014年2月24日 下午7:25:57
 */
@SuppressWarnings("unchecked")
public class JsonServerOfJackson implements InitializingBean, IJsonServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonServerOfJackson.class);

    private static ObjectMapper mapper;

    private JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    @Override
    public <T> T parseJson(String json, Class<?> clss) {
        try {
            return (T) mapper.readValue(json, clss);
        } catch (Exception e) {
            LOGGER.error("解析JSON串失败:{}", json);
            LOGGER.error("解析JSON串失败", e);
        }
        return null;
    }

    @Override
    public <T> List<T> parseJsonList(String json, Class<?> clss) {
        JavaType javaType = getCollectionType(ArrayList.class, clss);
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            LOGGER.error("解析JSON串失败:{}", json);
            LOGGER.error("解析JSON串失败", e);
        }
        return null;
    }

    @Override
    public String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("JSON序列化失败:{}", obj);
            LOGGER.error("JSON序列化失败", e);
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = new ObjectMapper();
        //允许空
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
