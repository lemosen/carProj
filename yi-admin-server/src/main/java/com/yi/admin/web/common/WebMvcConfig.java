//package com.yi.admin.web.common;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * web相关
// * 
// * @author xuyh
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//	@Autowired
//	private JwtInterceptor jwtInterceptor;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
//			.excludePathPatterns("/")
//			.excludePathPatterns("/auth/**")
//			.excludePathPatterns("/error/**");
//		WebMvcConfigurer.super.addInterceptors(registry);
//	}
//
//}
