package com.benmu.platform.docking;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

/**
 * @author hanwei
 * @version 2017-03-28
 */
public interface Tamper {

    public void alter(BeanDefinitionHolder beanDefinitionHolder);
}
