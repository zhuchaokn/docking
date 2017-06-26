package com.benmu.platform.docking.http;

import com.benmu.platform.docking.DockingMethod;
import org.apache.http.HttpResponse;

/**
 * @author zhuchao on 2017/4/7.
 */
public interface HttpResponseParser {

    Object parse(DockingMethod dockingMethod, HttpResponse response);
}
