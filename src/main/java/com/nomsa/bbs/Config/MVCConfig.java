package com.nomsa.bbs.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    private static final String[] EXCLUDE_PATHS = {
            "/v2/api-docs",
            "/configuration/security",
            "/configuration/ui",
            "/swagger-resources",
            "/swagger-ui.html",
            "/webjars/**",
            "/users/signUp",
            "/users/signIn",
            "/posts",
            "/error/**"
    };

    @Autowired
    private JWTInterceptor jwtInterceptor;

    /*
    @ERROR Spring Excute Chain ISSUE
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }
    */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .exposedHeaders("infranics")
                .allowedMethods("GET", "POST")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
