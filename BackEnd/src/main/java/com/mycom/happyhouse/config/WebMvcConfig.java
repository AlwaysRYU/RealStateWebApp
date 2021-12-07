package com.mycom.happyhouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mycom.happyhouse.common.LoginInterceptor;

public class WebMvcConfig implements WebMvcConfigurer{
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	
	// 얘는 지금 HTML 페이지구조로 되어있음.
	// 
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
		.addPathPatterns("/**")
		//.excludePathPatterns("/login/**", "/register/**"); // static folder // jsp 사용할 경우
		.excludePathPatterns(
				"/",
				"/index.html",
				"favicon.ico", 
				"/css/**",
				"/js/**",
				"/login/**",
				"/register/**",
				"/boards/**",
				"/notice/**"				
				);
	}
	
//	@Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/static/login.html");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }
}
