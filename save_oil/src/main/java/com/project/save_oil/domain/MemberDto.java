package com.project.save_oil.domain;

import com.project.save_oil.member.Member;
import com.sun.istack.Nullable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	private String id;
	private String name;
	private String password;
	private String carName;
	private String company;
	private String oilType;
	private String fuelEffi;

	public Member toEntity() {
		return Member.builder().id(id).name(name).password(password).carName(carName).company(company).oilType(oilType)
				.fuelEffi(fuelEffi).build();
	}
}
