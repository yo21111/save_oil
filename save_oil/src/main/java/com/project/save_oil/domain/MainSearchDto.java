package com.project.save_oil.domain;

import lombok.Data;

@Data
public class MainSearchDto {
	// 현재 위치 주소명
	private String address;
	// 주유하는 기름 종류 = 휘발유 : B027, 경유 : D047, 고급휘발유 : B034
	private String oilType;
	// 찾으려는 검색 반경 = 최대 5000m(m 단위) : 1, 3, 5km
	private Integer radius;
	// 검색 정렬 기준 = 1 :가격순, 2 :거리순
	private String sort;
	// 위도 데이터 - 현재 위치
	private String x;
	// 경도 데이터 - 현재 위치
	private String y;
	// 주유 예정 금액
	private Integer money;
}
