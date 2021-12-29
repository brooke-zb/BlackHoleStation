package top.brookezb.bhs.annotation;

import java.lang.annotation.*;

/**
 * 鉴权注解，验证是否作者本人操作
 *
 * @author brooke_zb
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

public @interface RequireAuthor {
}
