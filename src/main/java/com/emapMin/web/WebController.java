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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.emapMin.WeatherVO;
import com.emapMin.service.LibJson;
import com.emapMin.service.mapService;
import com.ibm.icu.text.SimpleDateFormat;

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

	@RequestMapping("weatherDate.do")
	public void weatherDate(HttpServletRequest req, HttpServletResponse res) throws Exception {
		WeatherVO vo = new WeatherVO();

		List<WeatherVO> slist = mapService.weatherDate(vo);		
		if(slist.size() > 0) {
			json.Json(res, slist);
		}
	}
	
	@RequestMapping("weatherTime.do")
	public void weatherTime(HttpServletRequest req, HttpServletResponse res) throws Exception {
		WeatherVO vo = new WeatherVO();
		String dateToTable = (String)req.getParameter("date"); // 20231203
		vo.setDate(dateToTable);

		dateToTable = "tb_" + dateToTable;
		vo.setTableName(dateToTable);

		List<WeatherVO> slist = mapService.weatherTime(vo);		
		if(slist.size() > 0) {
			json.Json(res, slist);
		}
	}
	
	// 현재 기상
	@RequestMapping("getWeatherLast1.do")
	public void getWeatherLast1(HttpServletRequest req, HttpServletResponse res) throws Exception {
		WeatherVO vo = new WeatherVO();
		String dateToTable = (String)req.getParameter("date"); // 20231203
		dateToTable = "tb_" + dateToTable;
		vo.setTableName(dateToTable);

		List<WeatherVO> slist = mapService.getWeatherLast1(vo);		
		if(slist.size() > 0) {			
			json.Json(res, slist);
		}
	}
	
	// 현재 기상
	@RequestMapping("getWeatherLast2.do")
	public void getWeatherLast2(HttpServletRequest req, HttpServletResponse res) throws Exception {
		WeatherVO vo = new WeatherVO();
		String dateToTable = (String)req.getParameter("date"); // 20231203
		dateToTable = "tb_" + dateToTable;
		vo.setTableName(dateToTable);

		List<WeatherVO> slist = mapService.getWeatherLast2(vo);

	    // 추가적인 정렬을 수행
	    slist.sort(Comparator.comparing(WeatherVO::getLat)
	            .thenComparing(WeatherVO::getLon));

		if(slist.size() > 0) {			
			json.Json(res, slist);
		}
	}
	
	// 현재 기상
	@RequestMapping("getWeatherLast2Test.do")
	public void getWeatherLast2Test(HttpServletRequest req, HttpServletResponse res) throws Exception {
		WeatherVO vo = new WeatherVO();
		String dateToTable = (String)req.getParameter("date"); // 20231203
		String time = dateToTable;

		LocalDate time_ = LocalDate.parse(time, DateTimeFormatter.BASIC_ISO_DATE);
        String time__ = time_.format(DateTimeFormatter.ISO_LOCAL_DATE);
		
		dateToTable = "tb_" + dateToTable;
		vo.setTableName(dateToTable);
		
		time = time__ + " " + (String)req.getParameter("time") + ":00:00";
		vo.setTime(time);

		List<WeatherVO> slist = mapService.getWeatherLast2Test(vo);
	    
	    // 추가적인 정렬을 수행
	    slist.sort(Comparator.comparing(WeatherVO::getLat)
	            .thenComparing(WeatherVO::getLon));

		if(slist.size() > 0) {			
			json.Json(res, slist);
		}
	}

	// 기상 정보
	@RequestMapping("getWeather.do")
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

		String lon1Str = (String)req.getParameter("lon1");
		float lon1;
		if (lon1Str == null)
			lon1 = 0;
		else
			lon1 = Float.parseFloat(lon1Str);
		lon1 -= degree;
		vo.setLon1(lon1);
		String lon2Str = (String)req.getParameter("lon2");
		float lon2;
		if (lon2Str == null)
			lon2 = 360;
		else
			lon2 = Float.parseFloat(lon2Str);
		lon2 += degree;
		vo.setLon2(lon2);
		
		List<WeatherVO> slist = mapService.getWeather(vo);		

	    // 추가적인 정렬을 수행
	    slist.sort(Comparator.comparing(WeatherVO::getLat)
	            .thenComparing(WeatherVO::getLon));

		if(slist.size() > 0) {			
			json.Json(res, slist);
		}
	}

	// 기상 정보 (팝업)
	@RequestMapping("getWeatherPopup.do")
	public void getWeatherPopup(HttpServletRequest req, HttpServletResponse res) throws Exception {
		WeatherVO vo = new WeatherVO();
		String dateToTable = (String)req.getParameter("date"); // 20231203
		vo.setDate(dateToTable);

		dateToTable = "tb_" + dateToTable;
		vo.setTableName(dateToTable);

		String lat = (String)req.getParameter("lat");
		float floatLat = Float.parseFloat(lat);
		vo.setLat(floatLat);
		String lon = (String)req.getParameter("lon");
		float floatLon = Float.parseFloat(lon);
		vo.setLon(floatLon);

		List<WeatherVO> slist = mapService.getWeatherPopup(vo);		
		if(slist.size() > 0) {
			json.Json(res, slist);
		}
	}
}
