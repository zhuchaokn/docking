package com.benmu.platform.docking.serializers;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author zhuchao on 2017/4/7.
 */
@Service
public class SerializerCenter {

    private Map<String, DockingSerializer> serializerMap = Maps.newHashMap();
    @Resource
    private ApplicationContext applicationContext;

    public DockingSerializer getSerializer(String name) {
        return serializerMap.get(name);
    }

    @PostConstruct
    public void init() {
        serializerMap = applicationContext.getBeansOfType(DockingSerializer.class);
    }
}
