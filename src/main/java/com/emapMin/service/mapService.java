package com.emapMin.service;

import java.util.List;
import java.util.Map;

import com.emapMin.WeatherVO;
import com.emapMin.RouteDetailVO;
import com.emapMin.RouteVO;

public interface mapService {
	
	public List<WeatherVO> weatherDate(WeatherVO vo) throws Exception;
	public List<WeatherVO> weatherTime(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherLast1(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherLast2(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherLast2Test(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherWind(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeather(WeatherVO vo) throws Exception;
	public void getWeatherTempair1() throws Exception;
	public void getWeatherTempair2(WeatherVO vo) throws Exception;
	public void getWeatherTempair3(WeatherVO vo) throws Exception;
	public void getWeatherTempair4(WeatherVO vo) throws Exception;
	public void getWeatherTempair5(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherTempair6(WeatherVO vo) throws Exception;
	public void getWeatherTempwater1() throws Exception;
	public void getWeatherTempwater2(WeatherVO vo) throws Exception;
	public void getWeatherTempwater3(WeatherVO vo) throws Exception;
	public void getWeatherTempwater4(WeatherVO vo) throws Exception;
	public void getWeatherTempwater5(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherTempwater6(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherPopup(WeatherVO vo) throws Exception;
	
	public List<RouteVO> getRouteList(RouteVO vo) throws Exception;	
	public void RouteInsert(RouteVO vo) throws Exception;	
	public int RouteUpdate(RouteVO vo) throws Exception;
	public int RouteDelete(RouteDetailVO vo) throws Exception;	
	public List<RouteDetailVO> getRouteDetailList(RouteVO vo) throws Exception;
	public void RouteDetailInsert(RouteDetailVO vo) throws Exception;	
	public int RouteDetailDelete(RouteDetailVO vo) throws Exception;
}
