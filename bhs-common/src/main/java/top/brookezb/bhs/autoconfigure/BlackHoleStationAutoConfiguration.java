package top.brookezb.bhs.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.brookezb.bhs.aspect.AuthenticationAspect;
import top.brookezb.bhs.utils.OSSUtils;
import top.brookezb.bhs.utils.RedisUtils;

/**
 * @author brooke_zb
 */
@Configuration
@Import({
        OSSUtils.class,
        RedisUtils.class,
        AuthenticationAspect.class
})
@EnableConfigurationProperties
public class BlackHoleStationAutoConfiguration {

}
