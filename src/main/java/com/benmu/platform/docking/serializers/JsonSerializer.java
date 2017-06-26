package com.benmu.platform.docking.serializers;

import com.benmu.api.json.JsonUtil;
import java.lang.reflect.Type;
import org.springframework.stereotype.Service;

/**
 * Description: JsonSerializer
 *
 * @author hanwei
 * @version 2017-03-28
 */
@Service("JSON")
public class JsonSerializer implements DockingSerializer {

    @Override
    public String serialize(Object source) {
        return JsonUtil.toJson(source);
    }

    @Override
    public Object deserialize(Type type, String source) throws ClassNotFoundException {
        if (type.equals(Void.TYPE)) {
            return null;
        }
        return JsonUtil.of(source, new DynamicTypeReference(type));
    }
}
