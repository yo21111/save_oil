package com.project.save_oil.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.project.save_oil.domain.MainSearchDto;
import com.project.save_oil.domain.MemberDto;
import com.project.save_oil.member.MemberService;

@Component
public class MemberValidator implements Validator {
	@Autowired
	MemberService memberService;

	@Override
	public boolean supports(Class<?> clazz) {
		return MainSearchDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberDto member = (MemberDto) target;
		try {
			if (memberService.findById(member.getId()) != null) {
				errors.rejectValue("id", "duplicate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!StringUtils.hasText(member.getId())) {
			errors.rejectValue("id", "required");
		}
		if (!StringUtils.hasText(member.getName())) {
			errors.rejectValue("name", "required");
		}
		if (!StringUtils.hasText(member.getPassword())) {
			errors.rejectValue("password", "required");
		}
		if (!StringUtils.hasText(member.getCarName())) {
			errors.rejectValue("carName", "required");
		}

	}

}
