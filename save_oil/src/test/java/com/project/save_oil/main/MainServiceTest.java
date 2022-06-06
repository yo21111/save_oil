package com.project.save_oil.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.save_oil.service.MainService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainServiceTest {
	@Autowired
	MainService mService;
	
	@Test
	public void find() throws Exception {
		
		Integer money = 50000;
		Integer oilPrice = 1920;
		Double distance = 821.7;
		String id = "test1234";
		//14.1
		double result1 = mService.mainProc(money, oilPrice, distance, id);
		double result2 = mService.mainProc(money, 1900, 900.3, id);
		
		System.out.println("result1 : " + result1);
		System.out.println("result2 : " + result2);
		
//		assertThat(fuelEffi).isEqualTo("14.1");
	}
}
