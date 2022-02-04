package com.brookezb.bhs.config;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.brookezb.bhs.aspect.AuthenticationAspect;
import com.brookezb.bhs.handler.GlobalExceptionHandler;
import com.brookezb.bhs.proerties.CsrfPathProperties;
import com.brookezb.bhs.proerties.OssProperties;
import com.brookezb.bhs.utils.MailUtils;
import com.brookezb.bhs.utils.OssUtils;
import com.brookezb.bhs.utils.RedisUtils;

/**
 * @author brooke_zb
 */
@Configuration
@Import({
        OssUtils.class,
        RedisUtils.class,
        MailUtils.class,
        AuthenticationAspect.class,
        WebMvcConfig.class,
        GlobalExceptionHandler.class
})
@EnableConfigurationProperties({CsrfPathProperties.class, OssProperties.class, MailProperties.class})
public class BlackHoleStationAutoConfiguration {
}
