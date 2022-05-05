package com.brookezb.bhs.config;

import com.brookezb.bhs.interceptor.AuthenticationInterceptor;
import com.brookezb.bhs.interceptor.CsrfInterceptor;
import com.brookezb.bhs.interceptor.DynamicAuthInterceptor;
import com.brookezb.bhs.proerties.CorsProperties;
import com.brookezb.bhs.proerties.CsrfProperties;
import com.brookezb.bhs.service.UserService;
import com.brookezb.bhs.utils.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置类
 *
 * @author brooke_zb
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final CsrfProperties csrfProperties;
    private final CorsProperties corsProperties;
    private final RedisUtils redisUtils;
    private final UserService userService;

    /**
     * 添加拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加鉴权拦截器
        registry.addInterceptor(new AuthenticationInterceptor(userService));

        // 添加动态登录拦截器
        registry.addInterceptor(new DynamicAuthInterceptor(redisUtils, userService)).addPathPatterns("/**");

        // 添加CSRF拦截器
        registry.addInterceptor(new CsrfInterceptor())
                .addPathPatterns(csrfProperties.getIncludes())
                .excludePathPatterns(csrfProperties.getExcludes());
        log.info("已添加CSRF拦截器 拦截{} 排除{}", csrfProperties.getIncludes(), csrfProperties.getExcludes());
    }

    /**
     * 跨域配置
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.getOrigins())
                .allowedMethods(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(60 * 60 * 24);
    }
}
