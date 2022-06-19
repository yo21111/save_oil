package com.project.save_oil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.save_oil.trace.logtrace.LogTrace;
import com.project.save_oil.trace.logtrace.ThreadLocalLogTrace;

@SpringBootApplication
public class SaveOilApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaveOilApplication.class, args);
	
	}
}
