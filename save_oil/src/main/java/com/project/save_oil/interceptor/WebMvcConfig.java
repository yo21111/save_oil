package com.project.save_oil.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] exclude = {"/login", "register"};

		LoginInterceptor loginInterceptor = new LoginInterceptor();
		registry.addInterceptor(loginInterceptor)
		.excludePathPatterns(exclude);
	}
	
}
