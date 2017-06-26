package com.benmu.platform.docking.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * @author zhuchao on 2017/4/7.
 */
public interface DockingHttpClient {

    HttpResponse execute(HttpUriRequest request);
}
