package com.benmu.platform.docking.annotations;

import com.benmu.platform.docking.serializers.SerializeMethod;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description: DockingMapping
 *
 * @author hanwei
 * @version 2017-03-28
 * @see org.springframework.web.bind.annotation.RequestMethod
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DockingMapping {

    String value() default "";

    RequestMethod method() default RequestMethod.POST;

    SerializeMethod request() default SerializeMethod.JSON;

    SerializeMethod response() default SerializeMethod.JSON;

    String requestCustomizeSerializer() default "";

    String responseCustomizeSerializer() default "";
}
