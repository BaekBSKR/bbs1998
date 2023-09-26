package com.maker.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maker.service.MovieService;
import com.maker.vo.MovieVO;

@Component
public class MovieAPIController {
	@Autowired
	private MovieService mSvc;
	
	public void getMovie() {
		System.out.println("json 호출 시작");
    	String movieInfoUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/"
    		+ "search_json2.jsp?collection=kmdb_new2&detail=N&listCount=100"
    		+ "&ServiceKey=A0D2TLUK94B6N6478AKI";

	    URL url = null;
	    HttpURLConnection con = null;
	    JSONObject result = null;
	    StringBuilder sb = new StringBuilder();
	    JSONObject InfoData = null;
	    BufferedReader br = null;	
			
		try {
			url = new URL(movieInfoUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "application/json");
			con.setDoOutput(true);
			con.disconnect();
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			while(br.ready()) {
				sb.append(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());

		if(sb != null) {
			try {
				result = (JSONObject) new JSONParser().parse(sb.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			// 전체 JSON 호출 후 JSONObject 생성.
			JSONObject infoResult = (JSONObject) result;
		
			// JSONObject에서 Data Array데이터를 get하여 JSONArray에 저장한다.
			JSONArray arrayList = (JSONArray) infoResult.get("Data");
		
			// Data Array에서 0번째 Array데이터를 get하여 JSONObject에 저장한다.
			JSONObject resultList = (JSONObject) arrayList.get(0);
				
			// JSONObject에서 Result Array데이터를 get하여 JSONArray에 저장한다.
			JSONArray movieList = (JSONArray) resultList.get("Result");
				
			for(int i = 0; i < movieList.size(); i ++) {
				// movieList Array에서 i번째 Array데이터를 get하여 DB에 저장한다.
				JSONObject movie = (JSONObject) movieList.get(i);
					
				// 제목
				String mTitle = (String) movie.get("title");
					
				// 감독
				JSONObject directors = (JSONObject) movie.get("directors");
				JSONArray director = (JSONArray) directors.get("director");
				JSONObject directorDetail = (JSONObject) director.get(0);
				String mDirect = (String) directorDetail.get("directorNm");
					
				// 배우
				JSONObject actors = (JSONObject) movie.get("actors");
				JSONArray actor = (JSONArray) actors.get("actor");
				JSONObject actorDetail = (JSONObject) actor.get(0);
				String mChar = (String) actorDetail.get("actorNm");
					
				// 줄거리
				JSONObject plots = (JSONObject) movie.get("plots");
				JSONArray plot = (JSONArray) plots.get("plot");
				JSONObject plotDetail = (JSONObject) plot.get(0);
				String mComment = (String) plotDetail.get("plotText");
					
				// 개봉연도
				String mOpen = (String) movie.get("prodYear");
				
				// 장르
				String mGenre = (String) movie.get("genre");
				
				// 등급
				String mRating = (String) movie.get("rating");
				
				// 전체 정보
				if(!mGenre.equals("에로") & !mTitle.isEmpty()
						& !mDirect.isEmpty() & !mChar.isEmpty()
						& !mComment.isEmpty() & !mOpen.isEmpty()
						& !mGenre.isEmpty() & !mRating.isEmpty()) {
//					System.out.println("영화" + i);
//					System.out.println("제목 : " + mTitle);
//					System.out.println("감독 : " + mDirect);
//					System.out.println("배우 : " + mChar);
//					System.out.println("줄거리 : " + mComment);
//					System.out.println("개봉연도 : " + mOpen);
//					System.out.println("장르 : " + mGenre);
//					System.out.println("등급 : " + mRating);
//					System.out.println("====================================");
					MovieVO movieVO = new MovieVO(mTitle, mComment,
							mDirect, mChar, mOpen);

					mSvc.register(movieVO);
					
				}
			}
		}
	}
}
