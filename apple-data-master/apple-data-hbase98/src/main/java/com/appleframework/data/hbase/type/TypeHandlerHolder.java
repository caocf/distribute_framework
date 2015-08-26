package com.appleframework.data.hbase.type;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.appleframework.data.hbase.exception.SimpleHBaseException;
import com.appleframework.data.hbase.util.ClassUtil;
import com.appleframework.data.hbase.util.Util;

/**
 * The holder of typeHandler's instance.
 * 
 * @author xinzhi
 * */
public class TypeHandlerHolder {

    /**
     * TypeHandler'Type -> TypeHandler's instance cache.
     * */
    private static ConcurrentMap<String, TypeHandler> typeHandlerCache = new ConcurrentHashMap<String, TypeHandler>();

    /**
     * Find TypeHandler instance using type's class name.
     * 
     * @param type TypeHandler's class name.
     * @return TypeHandler instance.
     * */
    public static TypeHandler findTypeHandler(String type) {
        Util.checkEmptyString(type);

        if (typeHandlerCache.get(type) == null) {
            try {
                Class<?> c = ClassUtil.forName(type);
                typeHandlerCache.putIfAbsent(type,
                        (TypeHandler) c.newInstance());
            } catch (Exception e) {
                throw new SimpleHBaseException(e);
            }
        }
        return typeHandlerCache.get(type);
    }
}
