package com.benmu.platform.docking.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.stereotype.Component;

/**
 * @author zhuchao on 2017/4/7.
 */
@Component
public class BenmuDockingHttpClient implements DockingHttpClient {

    @Override
    public HttpResponse execute(HttpUriRequest request) {
        //todo
        return null;
    }
}
