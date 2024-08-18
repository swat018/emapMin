package com.emapMin.service.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emapMin.WeatherVO;
import com.emapMin.RouteDetailVO;
import com.emapMin.RouteVO;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * @Class Name : CmmUseDAO.java
 * @Description : 怨듯넻肄붾뱶�벑 �쟾泥� �뾽臾댁뿉�꽌 怨듭슜�빐�꽌 �궗�슜�빐�빞 �븯�뒗 �꽌鍮꾩뒪瑜� �젙�쓽�븯湲곗쐞�븳 �뜲�씠�꽣 �젒洹� �겢�옒�뒪
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
@Repository("mapDAO")
public class mapDAO extends EgovComAbstractDAO {

    //@Autowired
    //private SqlSessionTemplate sqlSessionTemplate;

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
   	public List<WeatherVO> getWeatherWind(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherWind", vo);
    }
    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeather(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeather", vo);
    }

    @SuppressWarnings("unchecked")
   	public void getWeatherTempair1() throws Exception {
    	insert("map.getWeatherTempair1");
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempair2(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempair2", vo);
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempair3(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempair3", vo);
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempair4(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempair4", vo);
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempair5(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempair5", vo);
    }
    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeatherTempair6(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherTempair6", vo);
    }

    @SuppressWarnings("unchecked")
   	public void getWeatherTempwater1() throws Exception {
    	insert("map.getWeatherTempwater1");
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempwater2(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempwater2", vo);
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempwater3(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempwater3", vo);
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempwater4(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempwater4", vo);
    }
    @SuppressWarnings("unchecked")
   	public void getWeatherTempwater5(WeatherVO vo) throws Exception {
    	insert("map.getWeatherTempwater5", vo);
    }
    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeatherTempwater6(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherTempwater6", vo);
    }

    /*@SuppressWarnings("unchecked")
    public List<WeatherVO> selectList(String queryId, Object parameter) {
        return sqlSessionTemplate.selectList(queryId, parameter);
    }*/

    @SuppressWarnings("unchecked")
   	public List<WeatherVO> getWeatherPopup(WeatherVO vo) throws Exception {
    	return (List<WeatherVO>) list("map.getWeatherPopup", vo);
    }
    
    @SuppressWarnings("unchecked")
   	public List<RouteVO> getRouteList(RouteVO vo) throws Exception {
    	return (List<RouteVO>) list("map.getRouteList",vo);
    }
    
    @SuppressWarnings("unchecked")
   	public void RouteInsert(RouteVO vo) throws Exception {
    	insert("map.RouteInsert", vo);
    }
    
    @SuppressWarnings("unchecked")
   	public int RouteUpdate(RouteVO vo) throws Exception {
    	return (int) update("map.RouteUpdate", vo);
    }
    
    @SuppressWarnings("unchecked")
   	public int RouteDelete(RouteDetailVO vo) throws Exception {
    	return (int) update("map.RouteDelete", vo);
    }
    
    @SuppressWarnings("unchecked")
   	public List<RouteDetailVO> getRouteDetailList(RouteVO vo) throws Exception {
    	return (List<RouteDetailVO>) list("map.getRouteDetailList", vo);
    }
    
    @SuppressWarnings("unchecked")
   	public void RouteDetailInsert(RouteDetailVO vo) throws Exception {
    	insert("map.RouteDetailInsert", vo);
    }
    
    @SuppressWarnings("unchecked")
   	public int RouteDetailDelete(RouteDetailVO vo) throws Exception {
    	return (int) update("map.RouteDetailDelete", vo);
    }
}
