package top.brookezb.bhs.annotation;

import java.lang.annotation.*;

/**
 * 鉴权注解，验证所需权限
 *
 * @author brooke_zb
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

public @interface RequirePermission {
    /**
     * 需要的权限名
     */
    String[] values();

    /**
     * 权限关系，默认为or
     */
    Relation relation() default Relation.OR;

    enum Relation {
        OR, AND
    }
}
