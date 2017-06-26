package com.benmu.platform.docking.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import java.lang.reflect.Type;

/**
 * @author zhuchao on 2017/4/7.
 */
public class DynamicTypeReference extends TypeReference {

    private Type type;

    public DynamicTypeReference(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return this.type;
    }
}
