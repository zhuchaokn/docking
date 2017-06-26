package com.benmu.platform.docking;

import com.benmu.platform.docking.annotations.Docking;
import com.benmu.platform.docking.annotations.DockingCookie;
import com.benmu.platform.docking.annotations.DockingHeader;
import com.benmu.platform.docking.annotations.DockingMapping;
import com.benmu.platform.docking.executor.HttpExecutor;
import com.benmu.web.spring.support.LazyMap;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: DockingProxy
 *
 * @author hanwei
 * @version 2017-03-28
 */
public class DockingProxy implements InvocationHandler {

    private static final String DOCKING_NAME = Docking.class.getSimpleName();

    /**
     * 被代理类（interface）上的注解
     */
    private Map<String, Object> classAnnotations = Maps.newHashMap();
    private HttpExecutor httpExecutor;
    /**
     * 避免每次调用都用反射去拿一堆东西
     */
    private LazyMap<Method, DockingMethod> dockingMethodMap = new LazyMap<Method, DockingMethod>() {
        @Override
        protected DockingMethod load(Method key) {
            return buildDockingMethod(key);
        }
    };

    public DockingProxy(HttpExecutor httpExecutor, Object[] classAnnotations) {
        this.httpExecutor = httpExecutor;
        this.classAnnotations = Arrays.stream(classAnnotations)
                .collect(Collectors
                        .toMap(t -> ((Annotation) t).annotationType().getSimpleName(), t -> t));

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DockingMethod dockingMethod = dockingMethodMap.get(method);
        return httpExecutor.doExecute(dockingMethod, args);
    }

    private DockingMethod buildDockingMethod(Method method) {
        Docking proxyDocking = (Docking) Preconditions
                .checkNotNull(classAnnotations.get(DOCKING_NAME));
        DockingMapping methodMapping = Preconditions
                .checkNotNull(method.getAnnotation(DockingMapping.class));
        DockingCookie dockingCookie = method.getAnnotation(DockingCookie.class);
        DockingHeader dockingHeader = method.getAnnotation(DockingHeader.class);
        DockingMethod dockingMethod = new DockingMethod();
        dockingMethod.setDocking(proxyDocking);
        dockingMethod.setDockingCookie(dockingCookie);
        dockingMethod.setDockingHeader(dockingHeader);
        dockingMethod.setParameters(Arrays.stream(method.getParameters())
                .collect(Collectors.toList()));
        dockingMethod.setMethodMapping(methodMapping);
        return dockingMethod;
    }
}
