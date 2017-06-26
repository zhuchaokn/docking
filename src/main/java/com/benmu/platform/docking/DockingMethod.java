package com.benmu.platform.docking;

import com.benmu.platform.docking.annotations.Docking;
import com.benmu.platform.docking.annotations.DockingCookie;
import com.benmu.platform.docking.annotations.DockingHeader;
import com.benmu.platform.docking.annotations.DockingMapping;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Description: DockingMethod
 *
 * @author hanwei
 * @version 2017-03-28
 */
public class DockingMethod {

    private String methodName;
    private List<Parameter> parameters;

    private Docking docking;
    private DockingMapping methodMapping;

    private DockingHeader dockingHeader;

    private DockingCookie dockingCookie;
    private Type returnType;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Docking getDocking() {
        return docking;
    }

    public void setDocking(Docking docking) {
        this.docking = docking;
    }

    public DockingMapping getMethodMapping() {
        return methodMapping;
    }

    public void setMethodMapping(DockingMapping methodMapping) {
        this.methodMapping = methodMapping;
    }

    public DockingHeader getDockingHeader() {
        return dockingHeader;
    }

    public void setDockingHeader(DockingHeader dockingHeader) {
        this.dockingHeader = dockingHeader;
    }

    public DockingCookie getDockingCookie() {
        return dockingCookie;
    }

    public void setDockingCookie(DockingCookie dockingCookie) {
        this.dockingCookie = dockingCookie;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
}
