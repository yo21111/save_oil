package com.project.save_oil.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.save_oil.api.ApiKey;
import com.project.save_oil.api.ConnectUrl;
import com.project.save_oil.service.KakaoApiFromAddrService;

@RestController
public class KakaoApiFromAdress {
	@Autowired
	private ApiKey API_KEY;
	@Autowired
	ConnectUrl connectUrl;
	@Autowired
	KakaoApiFromAddrService kafaService;

	@PostMapping("/address")
	public Map<String, Object> getKakaoApiFromAddress(@RequestBody String dataAddress) throws Exception {
		String apiKey = API_KEY.getKAKAO_ADRESS();
		String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
		String jsonString = null;
		Map<String, Object> map = null;

		dataAddress = URLEncoder.encode(dataAddress, "UTF-8");
		String addr = apiUrl + "?query=" + dataAddress;

		jsonString = connectUrl.connectApi(addr, apiKey);
		map = kafaService.parseToMap(jsonString);

		return map;
	}

}
