package com.project.save_oil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.save_oil.api.ApiKey;
import com.project.save_oil.api.ConnectUrl;

@Service
public class KakaoApiFromAddrService {
	@Autowired
	private ApiKey API_KEY;
	@Autowired
	ConnectUrl connectUrl;

	public Map<String, Object> parseToMap(String jsonString) throws Exception {

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
		JSONArray dataList = (JSONArray) jsonObject.get("documents");

		String x = "";
		String y = "";
		for (int i = 0; i < dataList.size(); i++) {
			JSONObject data = (JSONObject) dataList.get(i);
			if (data.containsKey("x")) {
				x = data.get("x").toString();
			}
			if (data.containsKey("y")) {
				y = data.get("y").toString();
			}
		}

		Map<String, Object> map = new ConcurrentHashMap<>();
		List<String> list = new ArrayList<>();
		list.add(x);
		list.add(y);
		list = transFormPoint(list);
		map.put("list", list);

		return map;
	}

	public List<String> transFormPoint(List<String> list) throws Exception {
		String apiKey = API_KEY.getKAKAO_ADRESS();
		String apiUrl = "https://dapi.kakao.com/v2/local/geo/transcoord.json";
		String jsonString = null;

		String addr = apiUrl + "?x=" + list.get(0) + "&y=" + list.get(1) + "&output_coord=KTM";

		jsonString = connectUrl.connectApi(addr, apiKey);

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
		JSONArray dataList = (JSONArray) jsonObject.get("documents");

		String x = "";
		String y = "";
		for (int i = 0; i < dataList.size(); i++) {
			JSONObject data = (JSONObject) dataList.get(i);
			if (data.containsKey("x")) {
				x = data.get("x").toString();
			}
			if (data.containsKey("y")) {
				y = data.get("y").toString();
			}
		}

		list.set(0, x);
		list.set(1, y);

		return list;
	}
}
