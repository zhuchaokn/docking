package com.benmu.platform.docking.http;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.context.DockingContext;
import com.google.common.base.Joiner;
import java.util.stream.Collectors;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhuchao on 2017/4/7.
 */
public class DefaultRequestBuilder implements RequestBuilder {

    @Override
    public HttpUriRequest build(DockingMethod method, DockingContext context) {
        HttpUriRequest request = null;
        if (method.getMethodMapping().method() == RequestMethod.GET) {
            request = new HttpGet(context.getHost() + context.getPath());
        } else if (method.getMethodMapping().method() == RequestMethod.POST) {
            HttpPost post = new HttpPost(context.getHost() + context.getPath());
            try {
                post.setEntity(new StringEntity(context.getBody()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            request = post;
        } else {
            throw new IllegalArgumentException(
                    "unsupported method" + method.getMethodMapping().method());
        }
        context.getHeaderList().forEach(request::setHeader);
        request.setHeader(new BasicHeader("Cookie", Joiner.on(";")
                .join(context.getCookieList().stream()
                        .map(cookie -> cookie.getName() + "=" + cookie.getValue()).collect(
                                Collectors.toList()))));
        return request;
    }
}
