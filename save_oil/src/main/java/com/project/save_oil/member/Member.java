package com.project.save_oil.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity(name = "member")
@Getter
@Setter
@Table(schema = "saveoil")
public class Member{
	@Id
	@Column(nullable = false)
	private String id;

	private String name;

	@Column(nullable = false)
	private String password;

	private String carName;

	private String company;

	private String oilType;

	private String fuelEffi;

	@Builder
	public Member(String id, String name, String password, String carName, String company, String oilType,
			String fuelEffi) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.carName = carName;
		this.company = company;
		this.oilType = oilType;
		this.fuelEffi = fuelEffi;
	}

	public Member (Member mem) {
		this.id = mem.getId();
		this.name = mem.getName();
		this.password = mem.getPassword();
		this.carName = mem.getCarName();
		this.company = mem.getCompany();
		this.oilType = mem.getOilType();
		this.fuelEffi = mem.getFuelEffi();
	}

	public void update(String name, String password, String carName, String company, String oilType, String fuelEffi) {
		this.name = name;
		this.password = password;
		this.carName = carName;
		this.company = company;
		this.oilType = oilType;
		this.fuelEffi = fuelEffi;
	}

}
