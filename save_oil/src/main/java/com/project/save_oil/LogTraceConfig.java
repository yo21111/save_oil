package com.project.save_oil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.save_oil.trace.logtrace.FieldLogTrace;
import com.project.save_oil.trace.logtrace.LogTrace;

@Configuration
public class LogTraceConfig {
	
	// 싱글톤으로 빈을 등록
	@Bean
	public LogTrace logTrace() {
		return new FieldLogTrace();
	}
}
