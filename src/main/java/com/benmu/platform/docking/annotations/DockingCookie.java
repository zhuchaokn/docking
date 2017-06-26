package com.benmu.platform.docking.annotations;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * Description: DockingCookie
 *
 * @see RequestMethod
 *
 * should support class which can be use to get cookie and class hold your cookie first.
 *
 * @author hanwei
 * @version 2017-03-28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DockingCookie {
    String value() default "";//对应cookie center的key，用于鉴别CookieLoader
}
