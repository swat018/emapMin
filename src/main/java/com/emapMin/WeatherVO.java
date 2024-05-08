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
public class WeatherVO implements Serializable {
    
	private String date = "";
	private String tableName = "";
	private String time = "";
	private float lat;
	private float lat1;
	private float lat2;
	private float lon;
	private float lon1;
	private float lon2;
	private float degree;
    private String air_temp = "";
    private String water_temp = "";
    private String u_current = "";
    private String v_current = "";
    private String ugrd10m = "";
    private String vgrd10m = "";
    private String fsdir = "";
    private String fshgt = "";
    private String fwaveu = "";
    private String fwavev = "";
    
    
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLat1() {
		return lat1;
	}
	public void setLat1(float lat1) {
		this.lat1 = lat1;
	}
	public float getLat2() {
		return lat2;
	}
	public void setLat2(float lat2) {
		this.lat2 = lat2;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public float getLon1() {
		return lon1;
	}
	public void setLon1(float lon1) {
		this.lon1 = lon1;
	}
	public float getLon2() {
		return lon2;
	}
	public void setLon2(float lon2) {
		this.lon2 = lon2;
	}
	public float getDegree() {
		return degree;
	}
	public void setDegree(float degree) {
		this.degree = degree;
	}
	public String getAir_temp() {
		return air_temp;
	}
	public void setAir_temp(String air_temp) {
		this.air_temp = air_temp;
	}
	public String getWater_temp() {
		return water_temp;
	}
	public void setWater_temp(String water_temp) {
		this.water_temp = water_temp;
	}
	public String getU_current() {
		return u_current;
	}
	public void setU_current(String u_current) {
		this.u_current = u_current;
	}
	public String getV_current() {
		return v_current;
	}
	public void setV_current(String v_current) {
		this.v_current = v_current;
	}
	public String getUgrd10m() {
		return ugrd10m;
	}
	public void setUgrd10m(String ugrd10m) {
		this.ugrd10m = ugrd10m;
	}
	public String getVgrd10m() {
		return vgrd10m;
	}
	public void setVgrd10m(String vgrd10m) {
		this.vgrd10m = vgrd10m;
	}
	public String getFsdir() {
		return fsdir;
	}
	public void setFsdir(String fsdir) {
		this.fsdir = fsdir;
	}
	public String getFshgt() {
		return fshgt;
	}
	public void setFshgt(String fshgt) {
		this.fshgt = fshgt;
	}
	public String getFwaveu() {
		return fwaveu;
	}
	public void setFwaveu(String fwaveu) {
		this.fwaveu = fwaveu;
	}
	public String getFwavev() {
		return fwavev;
	}
	public void setFwavev(String fwavev) {
		this.fwavev = fwavev;
	}
    

}
