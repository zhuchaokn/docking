package com.benmu.platform.docking.serializers;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 基本的string类型
 * @author hanwei
 * @version 2017-03-28
 */
public class StringSerializer implements DockingSerializer{
    @Override
    public String serialize(Object source) {
        StringBuilder sb = new StringBuilder();
        if (source instanceof Map) {
            for (Map.Entry entry : ((Map<String,Object>) source).entrySet()) {
                sb.append("&").append(entry.getKey()).append("=").append(String.valueOf(entry.getValue()));
            }
            if (sb.length() > 0) {
                return sb.deleteCharAt(0).toString();
            }
        }
        return String.valueOf(source);
    }

    @Override
    public Object deserialize(Type type, String source) {
        return source;
    }

    @Override
    public SerializeMethod method() {
        return SerializeMethod.STRING;
    }

    //todo
    @Override
    public String contentType() {
        return "application/x-www-form-urlencoded;charset=UTF-8;";
    }
}
