package com.emapMin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.emapMin.WeatherVO;
import com.emapMin.service.mapService;
import com.emapMin.RouteDetailVO;
import com.emapMin.RouteVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : EgovCmmUseServiceImpl.java
 * @Description : 怨듯넻肄붾뱶�벑 �쟾泥� �뾽臾댁뿉�꽌 怨듭슜�빐�꽌 �궗�슜�빐�빞 �븯�뒗 �꽌鍮꾩뒪瑜� �젙�쓽�븯湲곗쐞�븳 �꽌鍮꾩뒪 援ы쁽 �겢�옒�뒪
 * @Modification Information
 *
 *    �닔�젙�씪       �닔�젙�옄         �닔�젙�궡�슜
 *    -------        -------     -------------------
 *    2009. 3. 11.     �씠�궪�꽠
 *
 * @author 怨듯넻 �꽌鍮꾩뒪 媛쒕컻�� �씠�궪�꽠
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("mapService")
public class mapServiceImpl extends EgovAbstractServiceImpl implements mapService {

	@Resource(name = "mapDAO")
	private mapDAO mapDAO;

	/**
	 * 怨듯넻肄붾뱶瑜� 議고쉶�븳�떎.
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<WeatherVO> weatherDate(WeatherVO vo) throws Exception {
		return mapDAO.weatherDate(vo);
	}

	@Override
	public List<WeatherVO> weatherTime(WeatherVO vo) throws Exception {
		return mapDAO.weatherTime(vo);
	}

	@Override
	public List<WeatherVO> getWeatherLast1(WeatherVO vo) throws Exception {
		return mapDAO.getWeatherLast1(vo);
	}

	@Override
	public List<WeatherVO> getWeatherLast2(WeatherVO vo) throws Exception {
		return mapDAO.getWeatherLast2(vo);
	}

	@Override
	public List<WeatherVO> getWeatherLast2Test(WeatherVO vo) throws Exception {
		return mapDAO.getWeatherLast2Test(vo);
	}

	@Override
	public List<WeatherVO> getWeatherWind(WeatherVO vo) throws Exception {
		return mapDAO.getWeatherWind(vo);
	}
	@Override
	public List<WeatherVO> getWeather(WeatherVO vo) throws Exception {
		return mapDAO.getWeather(vo);
	}

	@Override
	public void getWeatherTempair1() throws Exception {
		mapDAO.getWeatherTempair1();
	}
	@Override
	public void getWeatherTempair2(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempair2(vo);
	}
	@Override
	public void getWeatherTempair3(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempair3(vo);
	}
	@Override
	public void getWeatherTempair4(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempair4(vo);
	}
	@Override
	public void getWeatherTempair5(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempair5(vo);
	}
	@Override
	public List<WeatherVO> getWeatherTempair6(WeatherVO vo) throws Exception {
		return mapDAO.getWeatherTempair6(vo);
	}

	@Override
	public void getWeatherTempwater1() throws Exception {
		mapDAO.getWeatherTempwater1();
	}
	@Override
	public void getWeatherTempwater2(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempwater2(vo);
	}
	@Override
	public void getWeatherTempwater3(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempwater3(vo);
	}
	@Override
	public void getWeatherTempwater4(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempwater4(vo);
	}
	@Override
	public void getWeatherTempwater5(WeatherVO vo) throws Exception {
		mapDAO.getWeatherTempwater5(vo);
	}
	@Override
	public List<WeatherVO> getWeatherTempwater6(WeatherVO vo) throws Exception {
		return mapDAO.getWeatherTempwater6(vo);
	}
	
	@Override
	public List<WeatherVO> getWeatherPopup(WeatherVO vo) throws Exception {
		return mapDAO.getWeatherPopup(vo);
	}
	
	@Override
	public List<RouteVO> getRouteList(RouteVO vo) throws Exception {
		return mapDAO.getRouteList(vo);
	}

	@Override
	public void RouteInsert(RouteVO vo) throws Exception {
		mapDAO.RouteInsert(vo);
	}

	@Override
	public int RouteUpdate(RouteVO vo) throws Exception {
		return mapDAO.RouteUpdate(vo);
	}

	@Override
	public int RouteDelete(RouteDetailVO vo) throws Exception {
		return mapDAO.RouteDelete(vo);
	}
	
	@Override
	public List<RouteDetailVO> getRouteDetailList(RouteVO vo) throws Exception {
		return mapDAO.getRouteDetailList(vo);
	}

	@Override
	public void RouteDetailInsert(RouteDetailVO vo) throws Exception {
		mapDAO.RouteDetailInsert(vo);
	}

	@Override
	public int RouteDetailDelete(RouteDetailVO vo) throws Exception {
		return mapDAO.RouteDetailDelete(vo);
	}
}
