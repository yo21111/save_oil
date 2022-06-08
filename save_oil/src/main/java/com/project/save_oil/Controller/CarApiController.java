package com.project.save_oil.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.save_oil.api.ApiKey;
import com.project.save_oil.domain.CarDataDto;
import com.project.save_oil.domain.SearchDto;

@Controller
public class CarApiController {
	@Autowired
	private ApiKey API_KEY;
	
	@PostMapping("/data")
	@ResponseBody
	public ResponseEntity<List<CarDataDto>> findCarData(@RequestBody SearchDto search) {
		StringBuffer result = new StringBuffer();

		try {
			String apiUrl = "https://api.odcloud.kr/api/15083023/v1/uddi:f0fcf58b-c8f6-4f62-a2c7-b3614454a1f9"
					+ "?serviceKey="+API_KEY.getCAR_API()
					+ "&page=1"  
					+ "&perPage=2560";
			URL url = new URL(apiUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			BufferedReader br;

			if (urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() <= 300) {
				br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
			}

			String returnLine;

			while ((returnLine = br.readLine()) != null) {
				result.append(returnLine + "\n");
			}

			List<CarDataDto> list = parseToDto(search.getSearch(), result.toString());
			br.close();
			urlConnection.disconnect();

			return new ResponseEntity<List<CarDataDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CarDataDto>>(HttpStatus.BAD_REQUEST);
		}

	}

	private List<CarDataDto> parseToDto(String search, String returnLine) throws ParseException {
		List<CarDataDto> list = new ArrayList<>();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(returnLine);
		JSONArray dataList = (JSONArray) jsonObject.get("data");

		for (int i = 0; i < dataList.size(); i++) {
			CarDataDto cdDto = new CarDataDto();

			JSONObject data = (JSONObject) dataList.get(i);
			cdDto.setCarName(data.get("모델명").toString());
			cdDto.setCompany(data.get("제조사").toString());
			cdDto.setOilType(data.get("유종").toString());
			cdDto.setFuelEffi(data.get("복합연비").toString());

			if (validation(cdDto, search)) {
				list.add(cdDto);
			}
		}

		return list;
	}

	public boolean validation(CarDataDto cdDto, String search) {
		boolean result = false;

		String name = cdDto.getCarName();
		String comp = cdDto.getCompany();

		name = name.toLowerCase();
		comp = comp.toLowerCase();
		if (search == null) {
			search = "";
		} else {
			search = search.toLowerCase();
		}
		if (name.indexOf(search) != -1 || comp.indexOf(search) != -1) {
			result = true;
		} else if (search.length() == 0) {
			result = true;
		}

		if (cdDto.getOilType().equals("전기")) {
			result = false;
		}

		return result;
	}
}
