package com.benmu.platform.docking.http;

import org.apache.http.HttpResponse;

/**
 * @author zhuchao on 2017/4/7.
 */
public interface ResponseChecker {

    void check(HttpResponse response);
}
