package com.project.save_oil.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.save_oil.domain.GasStationDto;
import com.project.save_oil.domain.MainSearchDto;
import com.project.save_oil.member.MemberService;

@Service
public class MainService {
	@Autowired
	MemberService mService;

	public double mainProc(Integer money, Integer oilPrice, Double distance, String id) throws Exception {
		String fuelEffi = mService.findById(id).getFuelEffi();
		distance /= 1000; // km로 변환
		double fuel_effi = Double.parseDouble(fuelEffi);
		double result = money - ((((double) money / oilPrice) - (distance / fuel_effi)) * oilPrice);

		result = Math.floor(result * 10) / 10;

		return result;
	}

	public String changeGasName(String POLL_DIV_CD) {
		String result = "";

		switch (POLL_DIV_CD) {
		case "SKE":
			result = "SK에너지";
			break;
		case "GSC":
			result = "GS칼텍스";
			break;
		case "HDO":
			result = "현대오일뱅크";
			break;
		case "SOL":
			result = "S-OIL";
			break;
		case "RTO":
			result = "알뜰(자영)";
			break;
		case "RTX":
			result = "알뜰(고속도로)";
			break;
		case "NHO":
			result = "알뜰(농협)";
			break;
		case "ETC":
			result = "";
			break;
		case "E1G":
			result = "E1";
			break;
		case "SKG":
			result = "SK가스";
			break;
		}

		return result;
	}

	public String searchPageProc(MainSearchDto mainDto) {
		StringBuffer result = new StringBuffer();
		String returnLine;

		try {
			// 1. mainDto 내용을 바탕으로 반경내 주유소 결과 가져오기
			String apiUrl = "http://www.opinet.co.kr/api/aroundAll.do" + "?code=F220607178" + "&x=" + mainDto.getX()
					+ "&y=" + mainDto.getY() + "&radius=" + mainDto.getRadius() + "&sort=" + mainDto.getSort()
					+ "&prodcd=" + mainDto.getOilType() + "&out=json";
			URL url = new URL(apiUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			BufferedReader br;

			if (urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() <= 300) {
				br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
			}

			while ((returnLine = br.readLine()) != null) {
				result.append(returnLine + "\n");
			}

			br.close();
			urlConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public List<GasStationDto> parseToDto(String id, Integer money, String returnLine) throws Exception {
		List<GasStationDto> list = new ArrayList<>();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(returnLine);
		JSONObject dataObject = (JSONObject) jsonObject.get("RESULT");
		JSONArray dataList = (JSONArray) dataObject.get("OIL");

		// 2. 해당 내용을 반복문을 통해 List로 넣을 때 손익계산 로직 실행 후 Dto에 담기
		// 내용이 너무 많을 경우 최대 15개까지 출력
		int listSize = Math.min(dataList.size(), 15);

		for (int i = 0; i < listSize; i++) {
			GasStationDto gsDto = new GasStationDto();

			JSONObject data = (JSONObject) dataList.get(i);
			String UNI_ID = data.get("UNI_ID").toString();
			String POLL_DIV_CD = data.get("POLL_DIV_CD").toString();
			String OS_NM = data.get("OS_NM").toString();
			String price = data.get("PRICE").toString();
			String distance = data.get("DISTANCE").toString();
			String GIS_X_COOR = data.get("GIS_X_COOR").toString();
			String GIS_Y_COOR = data.get("GIS_Y_COOR").toString();

			gsDto.setUNI_ID(UNI_ID);
			gsDto.setPOLL_DIV_CD(changeGasName(POLL_DIV_CD));
			gsDto.setOS_NM(OS_NM);
			gsDto.setOilPrice(Integer.parseInt(price));
			gsDto.setDistance(Math.floor(Double.parseDouble(distance)) / 1000);
			gsDto.setGIS_X_COOR(GIS_X_COOR);
			gsDto.setGIS_Y_COOR(GIS_Y_COOR);
			gsDto.setSave(mainProc(money, Integer.parseInt(price), Double.parseDouble(distance), id));

			list.add(gsDto);
		}

		return list;
	}

	public String resultPageProc(String UNI_ID) {
		// UNI_ID 값을 바탕으로 주유소 세부 정보 가져오기
		StringBuffer result = new StringBuffer();

		try {
			// 1. mainDto 내용을 바탕으로 반경내 주유소 결과 가져오기
			String apiUrl = "http://www.opinet.co.kr/api/detailById.do" + "?code=F220607178" + "&id=" + UNI_ID
					+ "&out=json";
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

			br.close();
			urlConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public GasStationDto parseToDto(String returnLine) throws Exception {

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(returnLine);
		JSONObject dataObject = (JSONObject) jsonObject.get("RESULT");
		JSONArray dataList = (JSONArray) dataObject.get("OIL");
		GasStationDto gsDto = new GasStationDto();

		// 2. 해당 내용을 반복문을 통해 List로 넣을 때 손익계산 로직 실행 후 Dto에 담기
		for (int i = 0; i < dataList.size(); i++) {

			JSONObject data = (JSONObject) dataList.get(i);
			String UNI_ID = data.get("UNI_ID").toString();
			String POLL_DIV_CD = data.get("POLL_DIV_CO").toString();
			String NEW_ADR = data.get("NEW_ADR").toString();
			String TEL = data.get("TEL").toString();
			String LPG_YN = data.get("LPG_YN").toString();
			String CAR_WASH_YN = data.get("CAR_WASH_YN").toString();
			String CVS_YN = data.get("CVS_YN").toString();
			String OS_NM = data.get("OS_NM").toString();
			String GIS_X_COOR = data.get("GIS_X_COOR").toString();
			String GIS_Y_COOR = data.get("GIS_Y_COOR").toString();

			gsDto.setUNI_ID(UNI_ID);
			gsDto.setPOLL_DIV_CD(changeGasName(POLL_DIV_CD));
			gsDto.setNEW_ADR(NEW_ADR);
			gsDto.setTEL(TEL);
			gsDto.setLPG_YN(LPG_YN);
			gsDto.setCAR_WASH_YN(CAR_WASH_YN);
			gsDto.setCVS_YN(CVS_YN);
			gsDto.setOS_NM(OS_NM);
			gsDto.setGIS_X_COOR(GIS_X_COOR);
			gsDto.setGIS_Y_COOR(GIS_Y_COOR);

		}

		return gsDto;
	}
}
