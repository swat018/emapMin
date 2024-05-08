package com.emapMin.service;

import java.util.List;
import java.util.Map;

import com.emapMin.WeatherVO;

public interface mapService {
	
	public List<WeatherVO> weatherDate(WeatherVO vo) throws Exception;
	public List<WeatherVO> weatherTime(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherLast1(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherLast2(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherLast2Test(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeather(WeatherVO vo) throws Exception;
	public List<WeatherVO> getWeatherPopup(WeatherVO vo) throws Exception;
}
