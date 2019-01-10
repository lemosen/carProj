package com.yi.webserver.web.config.common;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yi.webserver.web.auth.resolver.CurrentUserMethodArgumentResolver;
import com.yi.webserver.web.auth.resolver.SessionDataMethodArgumentResolver;
import com.yi.webserver.web.config.interceptor.JwtTokenInterceptor;
import com.yi.webserver.web.config.interceptor.LoginRequiredInterceptor;
import com.yihz.common.utils.meta.BaseEnumConverterFactory;

@EnableWebMvc
@Configuration
public class CustomMVCConfig implements WebMvcConfigurer {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtTokenInterceptor jwtTokenInterceptor;
	@Autowired
	private LoginRequiredInterceptor loginRequiredInterceptor;

	@Autowired
	private CurrentUserMethodArgumentResolver currentStaffMethodArgumentResolver;
	@Autowired
	private SessionDataMethodArgumentResolver sessionDataMethodArgumentResolver;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new BaseEnumConverterFactory());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(sessionDataMethodArgumentResolver);
		argumentResolvers.add(currentStaffMethodArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtTokenInterceptor);
		registry.addInterceptor(loginRequiredInterceptor);
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setEncoding("UTF-8");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setFilter(characterEncodingFilter);
		return registrationBean;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter
				.setSupportedMediaTypes(Lists.newArrayList(new MediaType("text", "plain", Charset.forName("UTF-8")), new MediaType("text", "html", Charset.forName("UTF-8"))));
		converters.add(stringHttpMessageConverter);

		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jackson2HttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(new MediaType("application", "json", Charset.forName("UTF-8")),
				new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"))));

		jackson2HttpMessageConverter.setObjectMapper(objectMapper);
		converters.add(jackson2HttpMessageConverter);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true);
		configurer.favorParameter(true);

		configurer.defaultContentType(MediaType.APPLICATION_JSON);

		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/index.html").addResourceLocations("classpath:/static/index.html");

		registry.addResourceHandler("/web/**").addResourceLocations("classpath:/static/web/");

		registry.addResourceHandler("/MP_verify_mF8h9CDuwXGuBFpi.txt").addResourceLocations("classpath:/static/MP_verify_mF8h9CDuwXGuBFpi.txt");
	}

}