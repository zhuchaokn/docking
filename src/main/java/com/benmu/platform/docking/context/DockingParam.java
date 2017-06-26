package com.benmu.platform.docking.context;

import java.lang.reflect.Parameter;

/**
 * @author zhuchao on 2017/4/7.
 */
public class DockingParam {

    private String name;
    private Parameter parameter;
    private Object object;

    public DockingParam(String name, Parameter parameter, Object object) {
        this.name = name;
        this.parameter = parameter;
        this.object = object;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
