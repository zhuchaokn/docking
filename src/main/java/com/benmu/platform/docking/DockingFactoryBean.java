package com.benmu.platform.docking;

import com.benmu.platform.docking.executor.HttpExecutor;
import java.lang.reflect.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Description: DockingFactoryBean
 *
 * @author hanwei
 * @version 2017-03-28
 */
public class DockingFactoryBean<T> implements FactoryBean<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockingFactoryBean.class);

    private String interfaceName;
    private HttpExecutor httpExecutor;

    @Override
    public T getObject() throws Exception {
        Class clazz = Class.forName(interfaceName);
        DockingProxy proxy = new DockingProxy(httpExecutor, clazz.getAnnotations());
        Object object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxy);
        return (T) object;
    }

    @Override
    public Class<T> getObjectType() {
        if (interfaceName == null) {
            return null;
        }
        try {
            return (Class<T>) Class.forName(interfaceName);
        } catch (ClassNotFoundException e) {
            LOGGER.error("class not found:{}", interfaceName, e);
        }
        return null;
    }

    public void setHttpExecutor(HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

}
