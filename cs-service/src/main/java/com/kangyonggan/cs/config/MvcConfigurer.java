package com.kangyonggan.cs.config;

import com.kangyonggan.common.web.ParamsInterceptor;
import com.kangyonggan.cs.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    /**
     * 白名单
     */
    private static List<String> whiteList = new ArrayList<>();

    static {
        initWhiteList();
    }

    /**
     * 初始化白名单
     */
    private static void initWhiteList() {
        whiteList.add("/api/login");
        whiteList.add("/api/logout");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 处理请求
        registry.addInterceptor(new ParamsInterceptor()).addPathPatterns("/**");

        // 登录认证、身份认证
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**").excludePathPatterns(whiteList);
    }
}
