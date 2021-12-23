package top.brookezb.bhs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.brookezb.bhs.interceptor.CsrfInterceptor;
import top.brookezb.bhs.interceptor.DynamicAuthInterceptor;
import top.brookezb.bhs.proerties.CsrfPathProperties;
import top.brookezb.bhs.service.UserService;
import top.brookezb.bhs.utils.RedisUtils;

import java.util.List;

/**
 * MVC配置类
 *
 * @author brooke_zb
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CsrfPathProperties.class)
public class WebMvcConfig implements WebMvcConfigurer {
    private final List<String> csrfPathInclude;
    private final List<String> csrfPathExclude;
    private final RedisUtils redisUtils;
    private final UserService userService;

    public WebMvcConfig(CsrfPathProperties csrfPathProperties, RedisUtils redisUtils, UserService userService) {
        this.csrfPathInclude = csrfPathProperties.getInclude();
        this.csrfPathExclude = csrfPathProperties.getExclude();
        this.redisUtils = redisUtils;
        this.userService = userService;
    }

    /**
     * 添加拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加动态登录拦截器
        registry.addInterceptor(new DynamicAuthInterceptor(redisUtils, userService)).addPathPatterns("/**");

        // 添加CSRF拦截器
        registry.addInterceptor(new CsrfInterceptor()).addPathPatterns(csrfPathInclude).excludePathPatterns(csrfPathExclude);
        log.info("已添加CSRF拦截器 拦截{} 排除{}", csrfPathInclude, csrfPathExclude);
    }
}
