package com.benmu.platform.docking.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class ConfigCenter {

    @Resource
    private ApplicationContext applicationContext;
    private Map<String, String> propertyMap = Maps.newHashMap();

    public String getProperty(String key) {
        return propertyMap.get(key);
    }

    @PostConstruct
    public void init() {
        propertyMap = loadLocalProperties();
    }

    private Map<String, String> loadLocalProperties() {
        PropertySourcesPlaceholderConfigurer configurer = applicationContext.getBean(
                PropertySourcesPlaceholderConfigurer.class);
        PropertySource localProperties = Lists.newArrayList(configurer.getAppliedPropertySources()).
                stream().
                filter(p -> p.getName().equals("localProperties")).
                collect(Collectors.toList()).
                get(0);
        return Arrays.stream(((PropertiesPropertySource) localProperties).getPropertyNames()).
                collect(Collectors.toMap(
                        p -> p,
                        p -> (String) localProperties.getProperty(p)));
    }
}
