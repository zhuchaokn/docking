package com.benmu.platform.docking.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

/**
 * @author zhuchao on 2017/4/7.
 */
public class DockingContext implements Serializable {

    private String host;
    private String path;
    private Map<String, DockingParam> argumentMap = Maps.newHashMap();
    private List<Header> headerList = Lists.newArrayList();
    private List<Cookie> cookieList = Lists.newArrayList();
    private String body;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, DockingParam> getArgumentMap() {
        return argumentMap;
    }

    public void setArgumentMap(
            Map<String, DockingParam> argumentMap) {
        this.argumentMap = argumentMap;
    }

    public List<Header> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<Header> headerList) {
        this.headerList = headerList;
    }

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
