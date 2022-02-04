package com.brookezb.bhs.annotation;

import java.lang.annotation.*;

/**
 * 鉴权注解，直接放行
 *
 * @author brooke_zb
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

public @interface PermitAll {
}
