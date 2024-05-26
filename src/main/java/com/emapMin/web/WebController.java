/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.emapMin.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.emapMin.WeatherVO;
import com.emapMin.service.LibJson;
import com.emapMin.service.mapService;
import com.ibm.icu.text.SimpleDateFormat;
import com.emapMin.RouteDetailVO;
import com.emapMin.RouteVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
@CrossOrigin(origins = "http://localhost:5173")
@Controller
public class WebController {	

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "mapService")
    private mapService mapService;

	LibJson json = new LibJson(); //json 설정
	
	LocalDate now = LocalDate.now();
	 
    // 포맷 정의
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    // 포맷 적용
    String formatedNow = now.format(formatter);
	
	//초기화면
	@RequestMapping(value = "/index.do")
	public String selectSampleList(ModelMap model) throws Exception {
	
		//RouteVO vo = mapService.test();
		//System.out.println("vo.getRouteid : "+vo.getRouteid());	
		
		return "emapMin/map/map";
	}
	
	//초기화면
	@RequestMapping(value = "/test.do")
	public String selectTest(ModelMap model) throws Exception {
	
		//RouteVO vo = mapService.test();
		//System.out.println("vo.getRouteid : "+vo.getRouteid());	
		
		return "emapMin/map/maptest";
	}
    
	// 기상 정보
	//@RequestMapping("getWeather.do")
    @RequestMapping("/api/weather/all")
	public void getWeather(HttpServletRequest req, HttpServletResponse res) throws Exception {
		WeatherVO vo = new WeatherVO();
		//vo.setDate((String)req.getParameter("date"));
		String dateToTable = (String)req.getParameter("date"); // 20231203
		vo.setDate(dateToTable);

		dateToTable = "tb_" + dateToTable;
		vo.setTableName(dateToTable);
		//vo.setDate(formattedDate);

		String time = (String)req.getParameter("time");
		/*if (time.length() == 1) // 1자리수일 경우 앞에 0을 추가
			time = "0" + time;*/
		vo.setTime(time);

		String degreeStr = (String)req.getParameter("degree");
		float degree;
		if (degreeStr == null)
			degree = 1;
		else
			degree = Float.parseFloat(degreeStr);
		vo.setDegree(degree);
		
		String lat1Str = (String)req.getParameter("lat1");
		float lat1;
		if (lat1Str == null)
			lat1 = -90;
		else
			lat1 = Float.parseFloat(lat1Str);
		lat1 -= degree;
		vo.setLat1(lat1);
		
		String lat2Str = (String)req.getParameter("lat2");
		float lat2;
		if (lat2Str == null)
			lat2 = 90;
		else
			lat2 = Float.parseFloat(lat2Str);
		lat2 += degree;
		vo.setLat2(lat2);


		String lon1Str = (String)req.getParameter("lon1");
		float lon1;
		String lon2Str = (String)req.getParameter("lon2");
		float lon2;
		float lon3 = -1;
		float lon4 = -1;
		

		if (lon1Str == null)
			lon1 = 0;
		else {
			lon1 = Float.parseFloat(lon1Str);
			lon1 -= degree;
		}

		if (lon2Str == null)
			lon2 = 360;
		else {
			lon2 = Float.parseFloat(lon2Str);
			lon2 += degree;
		}

		float lon1Real = lon1; // 예 : -790
		float lon2Real = lon2; // 예 : -790
		float lon1Convert = 0;
		float lon2Convert = 0;
		//float lonRound = (Math.abs((float) Math.floor(lon1 / 360))) + 1;
		int lonRound = (Math.abs((int) (lon1 / 360))) + 1;

		String dataStr = (String)req.getParameter("data");
		int dataInt = Integer.parseInt(dataStr);
		//boolean dataBool = Boolean.parseBoolean(dataStr); // true인 경우 기온/수온
		boolean isTemp = false;
		if (dataInt == 1 && Math.abs(lon2Real - lon1Real) >= 360)
			isTemp = true;
		/*if (isTemp) { // 기온/수온이고 범위가 360도 이상인 경우
			lon1 = 0;
			lon2 = 360;
		} else { // 기온/수온 아닐 때*/
			if (lon1 < 0) {
				lon1Convert = 360 + (lon1 % 360); // 예 : 290
				lon2Convert = 360 + (lon2 % 360); // 예 : 290
				if (lon2 < 0) {
					lon1 = lon1Convert;
					lon2 = lon2Convert;
				} else {
					lon1 = 0;
					lon3 = lon1Convert;
					lon4 = 360;
				}
			} else if (lon2 > 360) {
				lon1Convert = lon1 % 360; // 예 : 290
				lon2Convert = lon2 % 360; // 예 : 290
				if (lon1 <= 360) {
					lon2 = 360 - degree;
					lon3 = 0; // 위와는 반대로
					lon4 = lon2Convert;
				}
			}
		//}
		
		vo.setLon1(lon1);
		vo.setLon2(lon2);
		vo.setLon3(lon3);
		vo.setLon4(lon4);

		List<WeatherVO> slist = mapService.getWeather(vo);
		
	    // 추가적인 정렬을 수행
	    slist.sort(Comparator.comparing(WeatherVO::getLat)
	            .thenComparing(WeatherVO::getLon));


		//if (!isTemp) { // 기온/수온 아닐 때
		    if (lon1Real < -(degree)) { // 0이 아닌 -0.5인 이유는 범위 가져올 때 기존 범위에서 0.5도를 뺀 범위까지 가져오므로
		    //if (lon1Real1 < -(degree) && lon2Real1 > 0 && lon2Real1 < 360) { // 0이 아닌 -0.5인 이유는 범위 가져올 때 기존 범위에서 0.5도를 뺀 범위까지 가져오므로
			//else if (lon1Real1 < -(degree) && lon2Real1 < -(degree)) { // 0이 아닌 -0.5인 이유는 범위 가져올 때 기존 범위에서 0.5도를 뺀 범위까지 가져오므로
				List<WeatherVO> updatedList1 = new ArrayList<>(slist.size());
		
				//boolean lonContinue = false; // 339.5가 있는지 여부로 340 포함한 연속성 체크
				// 예) 340에서 359.5까지의 범위값을 -20 ~ -0.5로 변환
			    for (WeatherVO weatherVO : slist) {
			        float lon = weatherVO.getLon();
			    	/*if (lon >= (lon1Convert - degree) && lon < lon1Convert)
			    		lonContinue = true;*/
					if (lon2Real >= 360 && lon >= 0 && lon < lon2Real - 360) { // lon2가 360도 초과 범위일 때 (우측 추가)
			            // 새로운 WeatherVO 객체를 만들어서 값 복사
			            WeatherVO updatedWeatherVO = new WeatherVO();
			            updatedWeatherVO.setLon(lon + (360 * lonRound));
			            // 다른 값은 그대로 유지
			            updatedWeatherVO.setLat(weatherVO.getLat());
			            updatedWeatherVO.setAir_temp(weatherVO.getAir_temp());
			            updatedWeatherVO.setWater_temp(weatherVO.getWater_temp());
			            updatedWeatherVO.setU_current(weatherVO.getU_current());
			            updatedWeatherVO.setV_current(weatherVO.getV_current());
			            updatedWeatherVO.setUgrd10m(weatherVO.getUgrd10m());
			            updatedWeatherVO.setVgrd10m(weatherVO.getVgrd10m());
			            //updatedWeatherVO.setFsdir(weatherVO.getFsdir());
			            //updatedWeatherVO.setFshgt(weatherVO.getFshgt());
			            updatedWeatherVO.setFwaveu(weatherVO.getFwaveu());
			            updatedWeatherVO.setFwavev(weatherVO.getFwavev());
			            // 새로운 객체를 리스트에 추가
			            updatedList1.add(updatedWeatherVO);
					}
			        if (lon >= lon1Convert && lon < 360) { // 좌측 추가
			            // 새로운 WeatherVO 객체를 만들어서 값 복사
			            WeatherVO updatedWeatherVO = new WeatherVO();
			            updatedWeatherVO.setLon(lon - (360 * lonRound));
			            // 다른 값은 그대로 유지
			            updatedWeatherVO.setLat(weatherVO.getLat());
			            updatedWeatherVO.setAir_temp(weatherVO.getAir_temp());
			            updatedWeatherVO.setWater_temp(weatherVO.getWater_temp());
			            updatedWeatherVO.setU_current(weatherVO.getU_current());
			            updatedWeatherVO.setV_current(weatherVO.getV_current());
			            updatedWeatherVO.setUgrd10m(weatherVO.getUgrd10m());
			            updatedWeatherVO.setVgrd10m(weatherVO.getVgrd10m());
			            //updatedWeatherVO.setFsdir(weatherVO.getFsdir());
			            //updatedWeatherVO.setFshgt(weatherVO.getFshgt());
			            updatedWeatherVO.setFwaveu(weatherVO.getFwaveu());
			            updatedWeatherVO.setFwavev(weatherVO.getFwavev());
			            // 새로운 객체를 리스트에 추가
			            updatedList1.add(updatedWeatherVO);
			        }
			    }
		
				// 기존 리스트에 추가
				slist.addAll(updatedList1);
			
				// 리스트 재정렬
				slist.sort(Comparator.comparing(WeatherVO::getLat)
				        .thenComparing(WeatherVO::getLon));
				
				// 연속성 없을 경우 340 이상은 제거
				//if (!lonContinue) {
					float lon1Final;

					if (isTemp) {
						lon1Final = lon1Convert;
						List<WeatherVO> filteredList2 = slist.stream()
						        //.filter(weather -> weather.getLon() > 0)
						        //.filter(weather -> weather.getLon() < 360)
						        .filter(weather -> weather.getLon() < lon1Final)
						        .collect(Collectors.toList());
						slist.clear(); // 기존 리스트를 비웁니다.
						slist.addAll(filteredList2); // 필터링된 리스트를 다시 추가합니다.
					} else {
						if (lon1Convert > lon2Real)
							lon1Final = lon1Convert;
						else
							lon1Final = lon2Real;
						List<WeatherVO> filteredList1 = slist.stream()
						        .filter(weather -> weather.getLon() < lon1Final)
						        .collect(Collectors.toList());
			
						slist.clear(); // 기존 리스트를 비웁니다.
						slist.addAll(filteredList1); // 필터링된 리스트를 다시 추가합니다.
		    		}
				//}
			}
	
		    else if (lon2Real > 360 + degree) {
				List<WeatherVO> updatedList2 = new ArrayList<>(slist.size());
		
				//boolean lonContinue = false; // 339.5가 있는지 여부로 340 포함한 연속성 체크
				// 예) 340에서 359.5까지의 범위값을 -20 ~ -0.5로 변환
			    for (WeatherVO weatherVO : slist) {
			        float lon = weatherVO.getLon();
			    	/*if (lon >= (lon1Convert - degree) && lon < lon1Convert)
			    		lonContinue = true;*/
			        if (lon <= lon2Convert && lon >= 0) {
			        //if ((lon >= lon1Convert && lon < 360) || (lon <= lon2Convert && lon >= 0)) {
			            // 새로운 WeatherVO 객체를 만들어서 값 복사
			            WeatherVO updatedWeatherVO = new WeatherVO();
			            updatedWeatherVO.setLon(lon + (360 * lonRound));
			            // 다른 값은 그대로 유지
			            updatedWeatherVO.setLat(weatherVO.getLat());
			            updatedWeatherVO.setAir_temp(weatherVO.getAir_temp());
			            updatedWeatherVO.setWater_temp(weatherVO.getWater_temp());
			            updatedWeatherVO.setU_current(weatherVO.getU_current());
			            updatedWeatherVO.setV_current(weatherVO.getV_current());
			            updatedWeatherVO.setUgrd10m(weatherVO.getUgrd10m());
			            updatedWeatherVO.setVgrd10m(weatherVO.getVgrd10m());
			            //updatedWeatherVO.setFsdir(weatherVO.getFsdir());
			            //updatedWeatherVO.setFshgt(weatherVO.getFshgt());
			            updatedWeatherVO.setFwaveu(weatherVO.getFwaveu());
			            updatedWeatherVO.setFwavev(weatherVO.getFwavev());
			            // 새로운 객체를 리스트에 추가
			            updatedList2.add(updatedWeatherVO);
			        }
			    }
		
				// 기존 리스트에 추가
				slist.addAll(updatedList2);
			
				// 리스트 재정렬
				slist.sort(Comparator.comparing(WeatherVO::getLat)
				        .thenComparing(WeatherVO::getLon));
				
				// 연속성 없을 경우 340 이상은 제거
				//if (!lonContinue) {
					float lon1Final = lon1Convert;
					float lon2Final = lon2Real;
					//float lon2Final = lon2Convert;
					if (isTemp) {
						List<WeatherVO> filteredList2 = slist.stream()
						        .filter(weather -> weather.getLon() < 360)
						        .collect(Collectors.toList());
						slist.clear(); // 기존 리스트를 비웁니다.
						slist.addAll(filteredList2); // 필터링된 리스트를 다시 추가합니다.
					} else {
						List<WeatherVO> filteredList2 = slist.stream()
						        //.filter(weather -> weather.getLon() >= lon1Final)
						        .filter(weather -> (weather.getLon() >= lon1Final) && (weather.getLon() <= lon2Final))
						        //.distinct()
						        .collect(Collectors.toList());
						slist.clear(); // 기존 리스트를 비웁니다.
						slist.addAll(filteredList2); // 필터링된 리스트를 다시 추가합니다.
					}
					
				//}
			}
		//}
		slist.remove(0);
		    
		if(slist.size() > 0) {
			json.Json(res, slist);
		}
	}
    
    //항로계획 전체 리스트
  	//@RequestMapping("getPlanList.do")
    @RequestMapping("/api/route/all")
  	public void getPlanList(HttpServletRequest req, HttpServletResponse res) throws Exception {
  		System.out.println("/api/route/all : start!");
  		RouteVO vo = new RouteVO();
  		List<RouteVO> slist = mapService.getRouteList(vo);
  		
  		if(slist.size() > 0) {	
  			json.Json(res, slist);
  		}				
  	}
    
    //항로계획 마스터 조회
  	//@RequestMapping("getRouteList.do")
    @RequestMapping("/api/route/master")
  	public void getRouteList(HttpServletRequest req, HttpServletResponse res) throws Exception {
  		System.out.println("/api/route/master : start!");
  		RouteVO vo = new RouteVO();
  		vo.setRouteid(Integer.parseInt(req.getParameter("id")));
  		List<RouteVO> slist = mapService.getRouteList(vo);		
  		
  		if(slist.size() > 0) {	
  			json.Json(res, slist);
  		}
  	}
    
    //항로계획 마스터 저장
  	//@RequestMapping("RouteSave.do")
    @RequestMapping("/api/route/mastersave")
  	public void RouteSave(HttpServletRequest req, HttpServletResponse res) throws Exception {
  		System.out.println("/api/route/mastersave : start!");
  		String id = req.getParameter("rt_id");
  		String r_name = req.getParameter("rt_rname");
  		String name = req.getParameter("rt_mkname");

  		RouteVO vo = new RouteVO();
  		List<String> list = new ArrayList<>();
  		try {
	  		if(id.equals("0")) {
	  			vo.setRoutename(r_name);
	  			vo.setMakename(name);
	  			mapService.RouteInsert(vo);
	  		} else {
	  			vo.setRouteid(Integer.parseInt(id));
	  			vo.setRoutename(r_name);
	  			vo.setMakename(name);
	  			mapService.RouteUpdate(vo);
	  		}
	  		list.add("S");
  		}catch (Exception e) {
  			list.add("E");
		}
  		json.Json(res, list);
  	}
    
    //항로계획 삭제
	//@RequestMapping("RouteDelete.do")
    @RequestMapping("/api/route/delete")
	public void RouteDelete(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("/api/route/delete : start!");
  		List<String> list = new ArrayList<>();
  		try {
			String id = req.getParameter("id");
			RouteDetailVO vo = new RouteDetailVO();
			vo.setRouteid(Integer.parseInt(id));
			mapService.RouteDetailDelete(vo);
			mapService.RouteDelete(vo);		
	  		list.add("S");
		}catch (Exception e) {
			list.add("E");
		}
		json.Json(res, list);
	}
    
    //항로계획 디테일 조회
  	//@RequestMapping("RouteDetailList.do")
    @RequestMapping("/api/route/detail")
  	public void RouteDetailList(HttpServletRequest req, HttpServletResponse res) throws Exception {
  		System.out.println("RouteDetailList : start!");
  		RouteVO vo = new RouteVO();
  		vo.setRouteid(Integer.parseInt(req.getParameter("id")));  		
  		List<RouteDetailVO> slist = mapService.getRouteDetailList(vo);		
  		
  		if(slist.size() > 0) {
  			json.Json(res, slist);
  		}				
  	}
    
    //항로계획 디테일 저장
  	//@RequestMapping("RouteDetailSave.do")
    @RequestMapping("/api/route/detailsave")
  	public void RouteDetailSave(HttpServletRequest req, HttpServletResponse res) throws Exception {
  		System.out.println("/api/route/detailsave : start!");
  		List<String> list = new ArrayList<>();
  		try {
	  		int cnt = Integer.parseInt(req.getParameter("cnt"));
	  		String id = req.getParameter("id");
	  		String[] arr_lon = req.getParameter("arr_lon").split(",");
	  		String[] arr_lat = req.getParameter("arr_lat").split(",");
	  		
	  		RouteDetailVO vo = new RouteDetailVO();
	  		vo.setRouteid(Integer.parseInt(id));
	  		mapService.RouteDetailDelete(vo);
	  		
	  		for(int i=0; i<cnt; i++) {
	  			vo = new RouteDetailVO();
	  			vo.setRouteid(Integer.parseInt(id));
	  			vo.setRouteseq((i+1));
	  			vo.setLon(arr_lon[i]);
	  			vo.setLat(arr_lat[i]);
	  			mapService.RouteDetailInsert(vo);
	  		}
	  		list.add("S");
		}catch (Exception e) {
			list.add("E");
		}
		json.Json(res, list);
  	}
}
