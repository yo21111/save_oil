package com.project.save_oil.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.save_oil.api.ApiKey;
import com.project.save_oil.api.ConnectUrl;
import com.project.save_oil.domain.CarDataDto;
import com.project.save_oil.domain.SearchDto;
import com.project.save_oil.service.CarApiService;

@Controller
public class CarApiController {
	@Autowired
	private ApiKey API_KEY;
	@Autowired
	ConnectUrl connectUrl;
	@Autowired
	CarApiService carApiService;
	
	@PostMapping("/data")
	@ResponseBody
	public ResponseEntity<List<CarDataDto>> findCarData(@RequestBody SearchDto search) {
		try {
			String apiUrl = "https://api.odcloud.kr/api/15083023/v1/uddi:f0fcf58b-c8f6-4f62-a2c7-b3614454a1f9"
					+ "?serviceKey="+API_KEY.getCAR_API()
					+ "&page=1"  
					+ "&perPage=2560";
			String result = connectUrl.connectApi(apiUrl);
			List<CarDataDto> list = carApiService.parseToDto(search.getSearch(), result);
			
			return new ResponseEntity<List<CarDataDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CarDataDto>>(HttpStatus.BAD_REQUEST);
		}

	}

}
