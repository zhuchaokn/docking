package com.benmu.platform.docking.docker;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.serializers.DockingSerializer;
import com.benmu.platform.docking.cookie.CookieCenter;
import com.benmu.util.UnsafeUtil;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhuchao on 2017/4/6.
 */
public class GetDocking implements HttpDocking {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object doExecute(DockingMethod method, Object... args) {
        Map<String, Object> params = buildParamMap(method, args);
        //解析url
        String url = dynamicBuildUrl(checkNotNull(method.getUrl()), params);
        //构建get请求参数
        HttpGet request = new HttpGet(buildGetUrl((url), params));
        //处理header
        List<Header> headers = buildHeader(method.getDockingHeader());
        if (headers != null) {
            headers.forEach(request::setHeader);
        }
        //处理cookie
        if (method.getDockingCookie() != null) {
            CookieLoader loader = CookieCenter.get(method.getDockingCookie().value());
            CookieStore cookieStore = loader.load();
            request.setHeader("Cookie", parseCookie(cookieStore));
        }
        String response = null;
        try {
            HttpResponse httpResponse = client.execute(request);
            response = getContent(httpResponse);
        } catch (Exception e) {
            logger.error("http get error:{}", method.getUrl());
            throw UnsafeUtil.throwException(e);
        }
        DockingSerializer responseSerializer = getDockingSerializer(
                method.getResponseSerializeMethod(),
                method.getResponseCustomizeSerializerClass());
        return responseSerializer.deserialize(method.getReturnType(), response);
    }
}
