package com.benmu.platform.docking.basic;

import com.benmu.platform.docking.DockingMethod;
import com.benmu.platform.docking.context.DockingContext;

/**
 * @author zhuchao on 2017/4/7.
 */
public interface Generator {

    void generate(DockingMethod dockingMethod, DockingContext context);
}
