package com.benmu.platform.docking.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Description: Docking
 *
 * Annotation on an interface, indicating that this interface is used for Http docking
 *
 * @author hanwei
 * @version 2017-03-28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Docking {

    /** bean name */
    String value() default "";

    /** http host */
    String host() default "";
}
