package com.project.save_oil.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoApiFromAdress {

	@PostMapping("/address")
	public Map<String, Object> getKakaoApiFromAddress(@RequestBody String dataAddress) {
		String apiKey = "6780a1cac7918156f6e9b6e1e4b74f92";
		String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
		String jsonString = null;
		Map<String, Object> map = null;

		try {
			dataAddress = URLEncoder.encode(dataAddress, "UTF-8");
			String addr = apiUrl + "?query=" + dataAddress;

			URL url = new URL(addr);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

			BufferedReader rd = null;
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuffer docJson = new StringBuffer();

			String line;

			while ((line = rd.readLine()) != null) {
				docJson.append(line);
			}

			jsonString = docJson.toString();
			map = parseToMap(jsonString);
			rd.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}

	private Map<String, Object> parseToMap(String jsonString) throws ParseException {

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
		JSONArray dataList = (JSONArray) jsonObject.get("documents");

		String x = "";
		String y = ""; 
		for (int i = 0; i < dataList.size(); i++) {
			JSONObject data = (JSONObject) dataList.get(i);
			if(data.containsKey("x")) {
				x = data.get("x").toString();
			}
			if(data.containsKey("y")) {
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
	
	public List<String> transFormPoint(List<String> list) {
		String apiKey = "6780a1cac7918156f6e9b6e1e4b74f92";
		String apiUrl = "https://dapi.kakao.com/v2/local/geo/transcoord.json";
		String jsonString = null;

		try {
			String addr = apiUrl + "?x=" + list.get(0) + "&y="+list.get(1)
				+ "&output_coord=KTM";

			URL url = new URL(addr);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

			BufferedReader rd = null;
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuffer docJson = new StringBuffer();

			String line;

			while ((line = rd.readLine()) != null) {
				docJson.append(line);
			}
			
			jsonString = docJson.toString();
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
			JSONArray dataList = (JSONArray) jsonObject.get("documents");
			
			String x = "";
			String y = ""; 
			for (int i = 0; i < dataList.size(); i++) {
				JSONObject data = (JSONObject) dataList.get(i);
				if(data.containsKey("x")) {
					x = data.get("x").toString();
				}
				if(data.containsKey("y")) {
					y = data.get("y").toString();
				}
			}
			
			list.set(0, x);
			list.set(1, y);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
}
