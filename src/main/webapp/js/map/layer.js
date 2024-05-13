var geoserverWmsUrl = "http://navioncorp.asuscomm.com:8089/geoserver/wms";
function wmsInit(){

	const urlBefore = 'http://navioncorp.asuscomm.com:8080/TileMap/';
	const urlAfter = '/{z}/{x}/{-y}.png';.0

    Day_Base = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Day_Base' + urlAfter
        })
    });
    map.addLayer(Day_Base); // 기본값
    
    Day_Standard = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Day_Standard' + urlAfter
        })
    });
    
    Day_Full = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Day_Full' + urlAfter

        })
    });

    Dusk_Base = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Dusk_Base' + urlAfter
        })
    });

    Dusk_Standard = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Dusk_Standard' + urlAfter
        })
    });

    Dusk_Full = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Dusk_Full' + urlAfter
        })
    });

    Night_Base = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Night_Base' + urlAfter
        })
    });

    Night_Standard = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Night_Standard' + urlAfter
        })
    });

    Night_Full = new ol.layer.Tile({
        source: new ol.source.XYZ({
            url: urlBefore + 'Night_Full' + urlAfter
        })
    });

   let ocean = new ol.layer.Tile({
		id : 'ocean',
    	title: 'ocean',
    	opacity: 1,
        source: new ol.source.TileWMS({
            url: geoserverWmsUrl,
            serverType: 'geoserver',
            crossOrigin: 'anonymous',            
            params: { 
            	'VERSION': '1.1.0' , 
                'LAYERS': 'emap:ocean',
                'CRS' : 'EPSG:3857',
            },            
        })
   });
   let worldcountries = new ol.layer.Tile({
		id : 'worldcountries',
    	title: 'worldcountries',
    	opacity: 1,
        source: new ol.source.TileWMS({
            url: geoserverWmsUrl,
            serverType: 'geoserver',
            crossOrigin: 'anonymous',            
            params: { 
            	'VERSION': '1.1.0' , 
                'LAYERS': 'emap:worldcountries',
                'CRS' : 'EPSG:3857',
            },            
        })
   });

	// layerMode에 따라 라디오 버튼 체크
	/*if (layerMode === 1) {
	    document.querySelector('input[name="mapSetting"][value="1"]').checked = true;
	} else if (layerMode === 2) {
	    document.querySelector('input[name="mapSetting"][value="2"]').checked = true;
	} else if (layerMode === 3) {
	    document.querySelector('input[name="mapSetting"][value="3"]').checked = true;
	}*/

	var modeSelect = document.getElementById("modeSelect");
	modeSelect.addEventListener("change", function() {
    // 라디오 버튼 선택 시
	//radioSetting.addEventListener("change", function(event) {
		const baseLayers = [Day_Base, Dusk_Base, Night_Base];
		const standardLayers = [Day_Standard, Dusk_Standard, Night_Standard];
		const fullLayers = [Day_Full, Dusk_Full, Night_Full];
		
		let allLayers1 = map.getLayers().getArray(); // 모든 추가된 레이어 정보 확인
		const removeLayerIfPresent = (layer) => {
			if (allLayers1.includes(layer)) {
				map.removeLayer(layer);
			}
		};
		
		// 레이어들 삭제
		baseLayers.forEach(removeLayerIfPresent);
		standardLayers.forEach(removeLayerIfPresent);
		fullLayers.forEach(removeLayerIfPresent);

        var selectedValue = event.target.value;
        if (selectedValue === '1') {
        	layerMode = 1;
        } else if (selectedValue === '2') {
        	layerMode = 2;
        } else if (selectedValue === '3') {
        	layerMode = 3;
        }

		if (brightMode == 1) {
			if (layerMode == 1) {
		    	map.addLayer(Day_Base);
				Day_Base.setZIndex(-1);
			} else if (layerMode == 2) {
		    	map.addLayer(Day_Standard);
				Day_Standard.setZIndex(-1);
			} else { // 3
		    	map.addLayer(Day_Full);
				Day_Full.setZIndex(-1);
		    }
		} else if (brightMode == 2) {
			if (layerMode == 1) {
		    	map.addLayer(Dusk_Base);
				Dusk_Base.setZIndex(-1);
			} else if (layerMode == 2) {
		    	map.addLayer(Dusk_Standard);
				Dusk_Standard.setZIndex(-1);
			} else { // 3
		    	map.addLayer(Dusk_Full);
				Dusk_Full.setZIndex(-1);
		    }
		} else if (brightMode == 3) {
			if (layerMode == 1) {
		    	map.addLayer(Night_Base);
				Night_Base.setZIndex(-1);
			} else if (layerMode == 2) {
		    	map.addLayer(Night_Standard);
				Night_Standard.setZIndex(-1);
			} else {// 3
		    	map.addLayer(Night_Full);
				Night_Full.setZIndex(-1);
			}
		}
    });

	// 밝기 선택시 동작
	var brightSelect = document.getElementById("brightSelect");

	brightSelect.addEventListener("change", function() {
		const baseLayers = [Day_Base, Dusk_Base, Night_Base];
		const standardLayers = [Day_Standard, Dusk_Standard, Night_Standard];
		const fullLayers = [Day_Full, Dusk_Full, Night_Full];
			
		let allLayers2 = map.getLayers().getArray(); // 모든 추가된 레이어 정보 확인
		const removeLayerIfPresent = (layer) => {
		  if (allLayers2.includes(layer)) {
		    map.removeLayer(layer);
		  }
		};
		
		baseLayers.forEach(removeLayerIfPresent);
		standardLayers.forEach(removeLayerIfPresent);
		fullLayers.forEach(removeLayerIfPresent);
		//map.removeLayer(Day4_Base1);
		map.removeLayer(ocean);
		map.removeLayer(worldcountries);

		var brightRange = brightSelect.value;
		brightMode = brightRange;

		if (brightMode == 1) { // 주간
			if (layerMode == 1) {
		    	map.addLayer(Day_Base);
				Day_Base.setZIndex(-1);
			} else if (layerMode == 2) {
		    	map.addLayer(Day_Standard);
				Day_Standard.setZIndex(-1);
			} else { // 3
		    	map.addLayer(Day_Full);
				Day_Full.setZIndex(-1);
			}
		} else if (brightMode == 2) { // 야간
			if (layerMode == 1) {
				map.removeLayer(Day_Base); //TTEST
		    	map.addLayer(Dusk_Base);
				Dusk_Base.setZIndex(-1);
			} else if (layerMode == 2) {
		    	map.addLayer(Dusk_Standard);
				Dusk_Standard.setZIndex(-1);
			} else { // 3
		    	map.addLayer(Dusk_Full);
				Dusk_Full.setZIndex(-1);
			}
		} else if (brightMode == 3) { // 새벽/일몰
			if (layerMode == 1) {
		    	map.addLayer(Night_Base);
				Night_Base.setZIndex(-1);
			} else if (layerMode == 2) {
		    	map.addLayer(Night_Standard);
				Night_Standard.setZIndex(-1);
			} else { // 3
		    	map.addLayer(Night_Full);
				Night_Full.setZIndex(-1);
			}
		} else if (brightMode == 4) { // 블랙테마
			map.addLayer(ocean);
			ocean.setZIndex(-1);
			//make_sld("ocean", "Polygon1_1", "0E182F", null);
			make_sld("ocean", "Polygon1_1", "17284F", null);
			map.addLayer(worldcountries);
			worldcountries.setZIndex(-1);
			make_sld("worldcountries", "Polygon1_2", "1E1E1E", null);
		}
	});
	
	/*let blackthemeCheckbox = document.getElementById('checkBlacktheme'); // 블랙테마

	blackthemeCheckbox.addEventListener('change', function() {
		if (blackthemeCheckbox.checked) { // 체크시
			
		} else { // 체크 해제시
			
		}
	});*/
}