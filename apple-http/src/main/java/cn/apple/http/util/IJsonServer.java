package cn.afterturn.http.util;

import java.util.List;

/**
 * JSON 解析接口
 * @author JueYue
 * @date 2015年5月26日
 */
public interface IJsonServer {
    /**
     * 反解析单个类
     * @param json
     * @param clss
     * @return
     */
    public <T> T parseJson(String json, Class<?> clss);

    /**
     * 反解析List
     * @param json
     * @param clss
     * @return
     */
    public <T> List<T> parseJsonList(String json, Class<?> clss);

    /**
     * toJson
     * @param obj
     * @return
     */
    public String toJson(Object obj);

}
