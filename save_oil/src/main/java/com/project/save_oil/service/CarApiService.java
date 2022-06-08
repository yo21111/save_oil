package com.project.save_oil.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.project.save_oil.domain.CarDataDto;

@Service
public class CarApiService {

	public List<CarDataDto> parseToDto(String search, String returnLine) throws ParseException {
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
