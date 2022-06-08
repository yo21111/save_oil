package com.project.save_oil.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;

@Component
public class ConnectUrl {
	
	// json 요청을 Get 방식으로 하는 경우
	public String connectApi(String apiUrl) throws Exception{
		StringBuffer result = new StringBuffer();
		
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
		return result.toString();
	}
	
	// 카카오 API와 같이 Header에 Authorization을 넣는 경우
	public String connectApi(String apiUrl, String appKey) throws Exception{
		URL url = new URL(apiUrl);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Authorization", "KakaoAK " + appKey);

		BufferedReader rd = null;
		rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer docJson = new StringBuffer();

		String line;

		while ((line = rd.readLine()) != null) {
			docJson.append(line);
		}
		rd.close();
		
		return docJson.toString();
	}
}
