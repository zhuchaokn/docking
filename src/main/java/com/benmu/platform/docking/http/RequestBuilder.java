package com.benmu.platform.docking.http;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.context.DockingContext;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * @author zhuchao on 2017/4/7.
 */
public interface RequestBuilder {

    HttpUriRequest build(DockingMethod method, DockingContext context);
}
