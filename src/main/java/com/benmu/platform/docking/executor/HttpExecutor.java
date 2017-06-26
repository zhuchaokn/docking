package com.benmu.platform.docking.executor;

import com.benmu.platform.docking.DockingMethod;

/**
 * @author zhuchao on 2017/4/7.
 */
public interface HttpExecutor {

    Object doExecute(DockingMethod dockingMethod, Object... args);
}
