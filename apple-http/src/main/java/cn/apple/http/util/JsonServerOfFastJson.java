package cn.afterturn.http.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * json 工具类 json版本
 * @author JueYue
 * @date 2015年5月26日
 */
@SuppressWarnings("unchecked")
public class JsonServerOfFastJson implements IJsonServer {

    @Override
    public <T> T parseJson(String json, Class<?> clazz) {
        return (T) JSON.parseObject(json, clazz);
    }

    @Override
    public <T> List<T> parseJsonList(String json, Class<?> clazz) {
        return (List<T>) JSON.parseArray(json, clazz);
    }

    @Override
    public String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

}
