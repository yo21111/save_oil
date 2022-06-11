package com.project.save_oil.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.project.save_oil.board.BoardDto;

@Component
public class BoardValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BoardDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BoardDto boardDto = (BoardDto)target;
		
		if(!StringUtils.hasLength(boardDto.getTitle())) {
			errors.rejectValue("title", "required");
		}
	}
	
}
