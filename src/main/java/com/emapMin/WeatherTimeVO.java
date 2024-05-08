package com.emapMin;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *  클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.11   이삼섭          최초 생성
 *
 * </pre>
 */
public class WeatherTimeVO implements Serializable {

	private String date = "";
	private String time = "";
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
