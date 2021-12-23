package top.brookezb.bhs.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.brookezb.bhs.aspect.AuthenticationAspect;
import top.brookezb.bhs.config.WebMvcConfig;
import top.brookezb.bhs.handler.GlobalExceptionHandler;
import top.brookezb.bhs.proerties.CsrfPathProperties;
import top.brookezb.bhs.proerties.OssProperties;
import top.brookezb.bhs.utils.OssUtils;
import top.brookezb.bhs.utils.RedisUtils;

/**
 * @author brooke_zb
 */
@Configuration
@Import({
        OssUtils.class,
        RedisUtils.class,
        AuthenticationAspect.class,
        WebMvcConfig.class,
        GlobalExceptionHandler.class
})
@EnableConfigurationProperties({CsrfPathProperties.class, OssProperties.class})
public class BlackHoleStationAutoConfiguration {
}
