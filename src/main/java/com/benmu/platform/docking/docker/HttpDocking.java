package com.benmu.platform.docking.docker;

import com.benmu.platform.docking.DockingMethod;

/**
 * @author zhuchao on 2017/4/6.
 */
public interface HttpDocking {

    Object doExecute(DockingMethod dockingMethod, Object... args);
}
