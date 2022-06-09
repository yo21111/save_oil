package com.project.save_oil.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.project.save_oil.domain.MainSearchDto;

@Component
public class MainValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MainSearchDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MainSearchDto mainDto = (MainSearchDto) target;
		// 검증 시작
		if (!StringUtils.hasText(mainDto.getAddress())) {
			errors.rejectValue("address", "required");
		}
		if (mainDto.getMoney() == null || mainDto.getMoney() < 5000 || mainDto.getMoney() > 1000000) {
			errors.rejectValue("money", "range", new Object[] { 5000, 1000000 }, null);
		}

	}

}
