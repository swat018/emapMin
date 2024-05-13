<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib/taglib.jsp"%>
<jsp:useBean id="today" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="yyyyMMddHHmmss" var="nowDate"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<title>Map</title>
<!-- css -->
<link rel="stylesheet" href="<c:url value="/js/libs/openlayers/ol-v5.30/ol.css"/>">
<link rel="stylesheet" href="<c:url value="/js/libs/openlayers/ol-ext/ol-ext.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/common.css?version=${nowDate}"/>"/>


<!-- 지도 스크립트 -->
<!-- JS -->
<!-- jquery -->
<script type="text/javascript" src="<c:url value="/js/libs/jquery/jquery-3.4.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery/jquery.migrate-3.0.0.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery/jquery-ui-1.11.4.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery/jquery.tablescroll.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery/jquery.blockUI.js"/>"></script>
<!-- jquery fileUpload -->
<script type="text/javascript" src="<c:url value="/js/libs/jquery/jquery.form.min.js"/>"></script>

<!-- openlayers -->
<script type="text/javascript" src="<c:url value="/js/libs/openlayers/ol-v5.30/ol.js"/>"></script>
<!-- openlayers -->
<script type="text/javascript" src="<c:url value="/js/libs/openlayers/ol-ext/ol-ext.min.js"/>"></script>

<!-- proj -->
<script type="text/javascript" src="<c:url value="/js/libs/proj4/proj4.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/proj4/epsg.js"/>"></script>

<!-- 작업파일 -->
<script type="text/javascript" src="<c:url value="/js/map/customDragInteraction.js?version=${nowDate}"/>"></script>

<script type="text/javascript" src="<c:url value="/js/map/mapOptions.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/style.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/map.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/layer.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/wfsLayer.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/interaction.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/measure.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/mapEvent.js?version=${nowDate}"/>"></script>
<script type="text/javascript" src="<c:url value="/js/map/layer_style.js?version=${nowDate}"/>"></script>

<script>
let map;
window.onload = function(){
	mapInit();
	// bundle.js가 먼저 로드되어 map 오류 발생 해결
    const bundleScript = document.createElement('script');
    bundleScript.src = 'js/map/canvasLayer/bundle.js';
    document.head.appendChild(bundleScript);
}
</script>
<style>
  #selectDate::-webkit-inner-spin-button,
  #selectDate::-webkit-calendar-picker-indicator {
    display: inline-block;
  }

  #selectDate::-webkit-datetime-edit {
    display: none;
  }
</style>
</head>
<body>

		<div id="container">
			<div class="con_center">
				<div class="menuBar">
					<table class="menuBarTable">
						<tr>
							<td width="22px"></td>
							<td>
								<!-- <input type="checkbox" id="checkWind" class="customCheckbox">
										<label for="checkWind" class="checkboxLabelCustom">&nbsp;풍향/풍속</label>
								&nbsp;<input type="checkbox" id="checkFlow" class="customCheckbox">
										<label for="checkFlow" class="checkboxLabelCustom">&nbsp;유향/유속</label>
								&nbsp;<input type="checkbox" id="checkWaveheight" class="customCheckbox">
										<label for="checkWaveheight" class="checkboxLabelCustom">&nbsp;파향/파주기</label>
								&nbsp;<input type="checkbox" id="checkTempair" class="customCheckbox">
										<label for="checkTempair" class="checkboxLabelCustom">&nbsp;기온</label>
								&nbsp;<input type="checkbox" id="checkTempwater" class="customCheckbox">
										<label for="checkTempwater" class="checkboxLabelCustom">&nbsp;수온</label> -->
								<input type="radio" id="checkWind" name="weatherOption" class="customRadio"> 풍향/풍속
							    &nbsp;<input type="radio" id="checkFlow" name="weatherOption" class="customRadio"> 유향/유속
							    &nbsp;<input type="radio" id="checkWave" name="weatherOption" class="customRadio"> 파향/파주기
							    &nbsp;<input type="radio" id="checkTempair" name="weatherOption" class="customRadio"> 기온
							    &nbsp;<input type="radio" id="checkTempwater" name="weatherOption" class="customRadio"> 수온
							</td>
							<td width="10px"></td>
							<td>
								<div id="weatherTime"></div>
							</td>
							<td width="2px"></td>
							<td>
								<div>
									<input type="date" id="selectDate" style="appearance: none; border: none; background-color: black; color:white">
								</div>
							</td>
							<td width="10px"></td>
							<td>
								<table>
									<tr height="2px"><td></td></tr>
									<tr>
										<td>
											<button id="timeMinus12" style="width: 20px; height: 15px; background-color: black; color: white; border-width: 1px; border-style: solid; border-color: white"><<</button> <button id="timeMinus3" style="width: 20px; height: 15px; background-color: black; color: white; border-width: 1px; border-style: solid; border-color: white"><</button> <button id="timeNow" style="width: 35px; height: 20px; background-color: black; color: white; border-width: 1px; border-style: solid; border-color: white">현재</button> <button id="timePlus3" style="width: 20px; height: 15px; background-color: black; color: white; border-width: 1px; border-style: solid; border-color: white">></button> <button id="timePlus12" style="width: 20px; height: 15px; background-color: black; color: white; border-width: 1px; border-style: solid; border-color: white">>></button>
										</td>
									</tr>
								</table>
							</td>
							<td width="20px"></td>
							<td>
								<select id="brightSelect" style='background-color: black; color: white'>
									<option value=1 selected>DAY</option>
									<option value=2>DUSK</option>
									<option value=3>NIGHT</option>
									<option value=4>Black Theme</option>
								</select>
							</td>
							<td width="5px">
							</td>
							<td>
								<select id="modeSelect" style='background-color: black; color: white'>
									<option value=1 selected>Base</option>
									<option value=2>Standard</option>
									<option value=3>Full</option>
								</select>
							</td>
						</tr>
					</table>
				</div>
				<div class="map" id="dvMap" style="width:100%; height:100%;"></div>
			</div>
</body>
</html>
