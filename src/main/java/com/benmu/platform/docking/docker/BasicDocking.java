package com.benmu.platform.docking.docker;

import com.benmu.http.client.base.SyncHttpResponse;
import com.benmu.platform.docking.DockingMethod;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;

/**
 * @author zhuchao on 2017/4/6.
 */
public class BasicDocking implements HttpDocking {


    @Override
    public Object doExecute(DockingMethod dockingMethod, Object... args) {

    }

    protected String buildUrl(DockingMethod dockingMethod, Object... args) {
        //
    }
}
