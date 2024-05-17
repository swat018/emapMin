var googlemap;
var choice_idx="";
var date = new Date();
var year = date.getFullYear();
var month = ("0" + (1 + date.getMonth())).slice(-2);
var day = ("0" + date.getDate()).slice(-2);

let zoom;

let lev; // 스케일 공유
let dateInput;

let Day_Base;
let Day_Standard;
let Day_Full;
let Dusk_Base;
let Dusk_Standard;
let Dusk_Full;
let Night_Base;
let Night_Standard;
let Night_Full;
let Day4_Base1;

let layerMode = 1; // Base, Standard, Detail
let brightMode = 1; // 주간
let layerWind1; // 풍향/풍속 레이어
let layerFlow1; // 유향/유속 레이어
let layerWave1; // 파향/파주기 레이어

let addLayerWind;
let addLayerFlow;
let addLayerWave;
let addLayerTempair;
let addLayerTempwater;

let layerTempair; // 기온
let layerTempwater; // 수온

/*let weatherTime = new Date();
weatherTime = weatherTime.getHours();
weatherTime = (parseInt(weatherTime / 3) * 3) + "시";*/


let weatherTime = new Date();
/*let weatherTimeY = weatherTime.getFullYear();
let weatherTimeM = weatherTime.getMonth() + 1;
let weatherTimeD = weatherTime.getDate();
weatherTimeM = weatherTimeM < 10 ? '0' + weatherTimeM : weatherTimeM;
weatherTimeD = weatherTimeD < 10 ? '0' + weatherTimeD : weatherTimeD;
weatherTime = weatherTime.getHours();
weatherTime = (parseInt(weatherTime / 3) * 3) + "시";
weatherTime = weatherTime < 10 ? '0' + weatherTime : weatherTime;
weatherTime = weatherTimeY + '-' + weatherTimeM + '-' + weatherTimeD + ' ' + weatherTime;*/

//선박 표기 기본 설정 (색상, 글자 크기)
var shipStyle={
	font : "15",
	color : "blue"
};
var modStyleSelectInteraction; //선박 선택 이벤트

//35.5468629,129.3005359 울산
// 중앙
function mapInit(){
	var view = new ol.View({
		center: ol.proj.fromLonLat([128.100,36.000]),
		zoom: 4,
		//zoom: 7,
		maxResolution: 21600
		//minZoom: 2, // 최소 줌 설정
		//extent: ol.proj.transformExtent([73, -90, 136, 90], 'EPSG:4326', 'EPSG:3857'),
        //constrainResolution: false
		/*constrainResolution: true, // 줌 레벨을 정수로 제한합니다.
        constrainRotation: true // 회전을 제한합니다.*/
	});
	
	googlemap = new ol.layer.Tile({
		source: new ol.source.OSM(),
	});

    //마우스 좌표
    var mouseControlCoordinate = new ol.control.MousePosition({
        coordinateFormat: function(coordinate) {
        	//brent 위경도 -> 도분초 로 변경
			//return ol.coordinate.format(coordinate, '위도: {y}, 경도: {x}', 3);
        	//return "위도 : " + convertToDMS(coordinate[1]) + ", 경도 : " + convertToDMS(coordinate[0]);
        	return "위도 : " + convertToDMS(coordinate[1], "latitude") + ", 경도 : " + convertToDMS(coordinate[0], "longitude");
        },
        projection: 'EPSG:4326',//좌표계 설정
        className: 'scale1', //css 클래스 이름
        target: document.getElementById('mouseLocationStat'),//좌표를 뿌릴 element
    });

	map = new ol.Map({
		layers: [googlemap],
		target: 'dvMap',
		view: view,
		controls: new ol.control.defaults().extend([mouseControlCoordinate]),
	});
	map.on('moveend', onMoveEnd);



// 오픈레이어스 좌우영역 제한
map.on('moveend', function() {
	// 지도의 크기를 가져옴
	let mapSize = map.getSize();
	
	// 화면의 왼쪽 중앙 좌표를 계산
	let leftCoordinate = map.getCoordinateFromPixel([0, 0]); // 좌측 좌표
	let rightPixel = mapSize[0]; // 우측 좌표(x)
	let rightCoordinate = map.getCoordinateFromPixel([rightPixel, 0]); // 화면 좌표를 지도 좌표로 변환

	// 화면상의 중심 좌표
	var currentCenter = view.getCenter();

	// 화면상의 중심 좌표와 왼쪽 좌표의 차이 계산
	var dx = currentCenter[0] - leftCoordinate[0];

	let lonLeft = ol.proj.transform([leftCoordinate[0], 0], 'EPSG:3857', 'EPSG:4326')[0];
	let lonRight = ol.proj.transform([rightCoordinate[0], 0], 'EPSG:3857', 'EPSG:4326')[0];

	// 경도 제한
	let newCenter;
	if (lonLeft < -180 || lonRight > 540) {
		if (lonLeft < -180) {
			lonLeft = 360 + (lonLeft % 360);
			
			newCenter = ol.proj.transform([lonLeft, 0], 'EPSG:4326', 'EPSG:3857');
			newCenter = [newCenter[0] + dx, currentCenter[1]];
		} else {
			lonRight = lonRight % 360;
			
			newCenter = ol.proj.transform([lonRight, 0], 'EPSG:4326', 'EPSG:3857');
			newCenter = [newCenter[0] - dx, currentCenter[1]];
		}
		
		view.setCenter(newCenter); // 변경된 경도로 지도 중심 재설정
	}
});




	// 해도 레이어
	wmsInit();
	map.removeLayer(googlemap); //배경맵 삭제
    vectorInit(); //베이스 vector레이어\
    GeojsonReadFeatures(); //geojson wfs 레이어로 변환. 주석처리
    shipSelectEvent(); //선박 선택 이벤트
}

/*function updateTime(setTime) { // 시간 변경
    let newWeatherTime = new Date(weatherTime);
    newWeatherTime.setHours(newWeatherTime.getHours() + setTime);
    let newWeatherTimeY = newWeatherTime.getFullYear();
    let newWeatherTimeM = newWeatherTime.getMonth() + 1;
    let newWeatherTimeD = newWeatherTime.getDate();
    newWeatherTimeM = newWeatherTimeM < 10 ? '0' + newWeatherTimeM : newWeatherTimeM;
    newWeatherTimeD = newWeatherTimeD < 10 ? '0' + newWeatherTimeD : newWeatherTimeD;
    newWeatherTime = newWeatherTime.getHours();
    newWeatherTime = (parseInt(newWeatherTime / 3) * 3) + "시";
    newWeatherTime = newWeatherTime < 10 ? '0' + newWeatherTime : newWeatherTime;
    newWeatherTime = newWeatherTimeY + '-' + newWeatherTimeM + '-' + newWeatherTimeD + ' ' + newWeatherTime;
    weatherTime = newWeatherTime;
}*/

// 위도/경도를 도분초 단위로 변환
function convertToDMS(data, type) {
	/*const sign = (data < 0) ? 'W' : 'E'; // 부호 처리
	const absData = Math.abs(data); // 절대값 변환
	const degrees = Math.floor(absData); // 도
	const minutesDecimal = (absData - degrees) * 60; // 분
	const minutes = Math.floor(minutesDecimal);
	const seconds = ((minutesDecimal - minutes) * 60).toFixed(2); // 초
	return `${degrees}° ${minutes}' ${seconds}" ${sign}`;*/
	if (type === 'longitude') {
		if (data < -180)
			data = 180 + ((data + 180) % 360);
		else if (data > 180)
			data = -(180 - ((data - 180) % 360));
	}
	const sign = (data < 0) ? (type === 'latitude' ? 'S' : 'W') : (type === 'latitude' ? 'N' : 'E'); // 부호 처리
    const absData = Math.abs(data); // 절대값 변환
    const degrees = Math.floor(absData); // 도
    const minutesDecimal = (absData - degrees) * 60; // 분
    //let minutes = Math.floor(minutesDecimal);
    let minutes = minutesDecimal.toFixed(3);
    //const seconds = ((minutesDecimal - minutes) * 60).toFixed(0); // 초
    if (minutes < 10)
    	minutes = "0" + minutes;
    //return `${degrees}° ${minutes}.${seconds}' ${sign}`;
    return `${degrees}° ${minutes}' ${sign}`;
}

// 레이어 변경
function layerChange(layerMap, layerMode) {
    if (layerMap[layerMode]) {
        const { layerRemove, layerGroup } = layerMap[layerMode];
        
        layerRemove.forEach(layer => {
            if (map.getLayers().getArray().includes(layer)) {
                map.removeLayer(layer);
            }
        });
        
        if (!map.getLayers().getArray().includes(layerGroup)) {
            map.addLayer(layerGroup);
            layerGroup.setZIndex(-1);
        }
    }
}

//geojson wfs 레이어로 변환
function GeojsonReadFeatures() {
	$.ajax({
        type:"get",
        url:"geojson/sship.geojson",
        dataType:"json",
		async: false,
        success: function(data){
        	shipSource.clear();
        	var features = new ol.format.GeoJSON().readFeatures(data);
        	//ship_layer.getSource().addFeatures(new ol.format.GeoJSON().readFeatures(data));
        	for(var i=0; i<features.length; i++) {
        		features[i].getGeometry().transform( 'EPSG:4326',  'EPSG:3857');
        		//features[i].pointFeature.id = "ship_"+features[i].properties.MMSI;
        		features[i].setStyle(
    					new ol.style.Style({		            
    			            image: new ol.style.Icon({
    				          	src: 'images/emap/shipIcon.png',
    				          	anchor: [0.8, 0.8],		
    				          	rotateWithView: true,
								rotation: features[i].getProperties().Course!=null?features[i].getProperties().Course:0
    		        		}),
    			            text: new ol.style.Text({
    			                textAlign: 'center',
    			                font:  'bold '+shipStyle.font+'px Arial',
    			                fill: new ol.style.Fill({color: shipStyle.color}),
    			                stroke: new ol.style.Stroke({color:'#ffffff', width:0}),
    			                text: ""+features[i].getProperties().imoNumber,
    			                offsetX: 0,
    			                offsetY: -25,
    			                overflow:true,
    			            })
    			      	})
    				);
        	}
        	ship_layer.getSource().addFeatures(features);
        },
        error:function(){
            console.log("통신에러");
        }
    });
}
