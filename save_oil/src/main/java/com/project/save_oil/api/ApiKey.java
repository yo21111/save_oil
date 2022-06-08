package com.project.save_oil.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ApiKey {
	
	@Value("${KAKAO-ADDRESS}")
	private String KAKAO_ADRESS;

	@Value("${CAR-API}")
	private String CAR_API;
	
	@Value("${TMAP-API}")
	private String TMAP_API;
}
