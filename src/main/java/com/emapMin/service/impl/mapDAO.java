package com.emapMin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.emapMin.WeatherVO;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * @Class Name : CmmUseDAO.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 데이터 접근 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Repository("mapDAO")
public class mapDAO extends EgovComAbstractDAO {

    /**
     * test
     *
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
   	public List<WeatherVO> weatherDate(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.weatherDate", vo);
    }

    @SuppressWarnings("unchecked")
   	public List<WeatherVO> weatherTime(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.weatherTime", vo);
    }

    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeatherLast1(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherLast1", vo);
    }

    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeatherLast2(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherLast2", vo);
    }

    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeatherLast2Test(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherLast2Test", vo);
    }

    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeather(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeather", vo);
    }

    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeatherPopup(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherPopup", vo);
    }
}
