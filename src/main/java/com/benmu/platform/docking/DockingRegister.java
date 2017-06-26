package com.benmu.platform.docking;

import com.benmu.platform.docking.executor.HttpExecutor;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

/**
 * Description: DockingRegister
 *
 * 注册 @Docker 代理类
 *
 * @author hanwei
 * @version 2017-03-28
 */
public class DockingRegister implements BeanDefinitionRegistryPostProcessor, InitializingBean,
        ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockingRegister.class);

    private String basePackages;
    private ApplicationContext applicationContext;
    /**
     * beanDefinitionHolder 修改器
     */
    private List<Tamper> tampers;

    public DockingRegister() {
        this("");
    }

    public DockingRegister(String basePackages) {
        basePackages = basePackages + "," + "com.benmu.platform.docking";
        this.basePackages = basePackages;
        this.tampers = Lists.newArrayList();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
            throws BeansException {
        Set<BeanDefinitionHolder> beanSet = new DockingScanner(registry).scanPackages(StringUtils
                .tokenizeToStringArray(basePackages,
                        ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
        if (CollectionUtils.isEmpty(beanSet)) {
            LOGGER.error("no beanDefinition found in {}", basePackages);
        }
        beanSet.forEach(holder -> {
            tampers.forEach(t -> t.alter(holder));
            registry.registerBeanDefinition(holder.getBeanName(), holder.getBeanDefinition());
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        // do nothing
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HttpExecutor httpExecutor = applicationContext.getBean(HttpExecutor.class);
        tampers.add(holder -> {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getPropertyValues().add("interfaceName", definition.getBeanClassName());
            definition.getPropertyValues().add("httpExecutor", httpExecutor);
            definition.setBeanClass(DockingFactoryBean.class);
        });
    }

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
