package com.benmu.platform.docking.annotations;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @see RequestMethod
 *
 * @author hanwei
 * @version 2017-03-28
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DockingHeader {

    //name 与 value 以=分割
    String header();

    //name 与 value 以=分割
    String[] headers();

}
