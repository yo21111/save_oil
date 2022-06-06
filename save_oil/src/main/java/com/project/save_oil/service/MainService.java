package com.project.save_oil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.save_oil.member.MemberService;

@Service
public class MainService {
	@Autowired
	MemberService mService;
	
	public double mainProc(Integer money, Integer oilPrice, Double distance,  String id) throws Exception {
		String fuelEffi = mService.findById(id).getFuelEffi();
		distance /= 1000; //km로 변환
		double fuel_effi = Double.parseDouble(fuelEffi);
		double result = money - ((((double)money / oilPrice) - (distance / fuel_effi)) * oilPrice);
		
		return result;
	}
	
}
