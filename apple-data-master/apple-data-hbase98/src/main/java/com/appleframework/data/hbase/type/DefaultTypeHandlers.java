package com.appleframework.data.hbase.type;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.appleframework.data.hbase.type.handler.*;
import com.appleframework.data.hbase.util.ClassUtil;
import com.appleframework.data.hbase.util.Util;

/**
 * Default TypeHandler.
 * 
 * @author xinzhi
 * */
public class DefaultTypeHandlers {
    private static EnumHandler                enumHandler     = new EnumHandler();
    private static Map<Class<?>, TypeHandler> defaultHandlers = new HashMap<Class<?>, TypeHandler>();

    static {
        defaultHandlers.put(String.class, new StringHandler());
        defaultHandlers.put(Date.class, new DateHandler());

        defaultHandlers.put(Boolean.class, new BooleanHandler());

        defaultHandlers.put(Character.class, new CharacterHandler());

        defaultHandlers.put(Byte.class, new ByteHandler());
        defaultHandlers.put(Short.class, new ShortHandler());
        defaultHandlers.put(Integer.class, new IntegerHandler());
        defaultHandlers.put(Long.class, new LongHandler());
        defaultHandlers.put(Float.class, new FloatHandler());
        defaultHandlers.put(Double.class, new DoubleHandler());
    }

    public static TypeHandler findDefaultHandler(Class<?> type) {
        Util.checkNull(type);

        type = ClassUtil.tryConvertToBoxClass(type);

        if (type.isEnum()) {
            return enumHandler;
        }

        return defaultHandlers.get(type);
    }
}
