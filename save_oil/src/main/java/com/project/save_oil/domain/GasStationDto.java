package com.project.save_oil.domain;

import lombok.Data;

@Data
public class GasStationDto {
	private String UNI_ID;
	
	private Double save;
	
	// 기름 값
	private Integer oilPrice;
	// 주유소까지의 거리 (m 단위)
	private Double distance;
	// 주유소 상표
	private String POLL_DIV_CD;
	// 주유소 상호
	private String OS_NM;

	// 도로명 주소
	private String NEW_ADR;

	// 전화번호
	private String TEL;

	// 주유소 x, y좌표
	private String GIS_X_COOR;
	private String GIS_Y_COOR;

	// 업종 구분 (N:주유소, Y:자동차충전소, C:주유소/충전소 겸업)
	private String LPG_YN;
	// 세차장 존재 여부
	private String CAR_WASH_YN;
	// 편의점 존재 여부
	private String CVS_YN;

}
