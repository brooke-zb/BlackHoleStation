package top.brookezb.bhs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.brookezb.bhs.interceptor.CSRFInterceptor;

/**
 * MVC配置类
 *
 * @author brooke_zb
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${csrf.path.include:/article/**}")
    private String[] csrfPathInclude;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("已添加CSRF拦截器 {}", csrfPathInclude);
        registry.addInterceptor(new CSRFInterceptor()).addPathPatterns(csrfPathInclude);
    }
}
