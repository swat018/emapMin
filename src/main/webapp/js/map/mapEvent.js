function onMoveEnd(evt) {
	  var map1 = evt.map;  
	  var lev = map1.getView().getZoom();
	  var resol = map1.getView().getResolutionForZoom(lev);	  
	  
	  var INCHES_PER_UNIT = 39.37;
	  var DOTS_PER_INCH = 72;
	  
	  var scale = INCHES_PER_UNIT * DOTS_PER_INCH * resol;
	  scale = Math.round(scale);
	  //lev = lev - 2; // 레벨 2 제한 때문에 -2 처리
	  $("#mapZoomLevelStat").html("축척=>1:"+scale+"[LEVEL:"+lev+"]");
}

function getLayer(id) {
	let lyr=null;
	var layers = map.getLayers().getArray();
	for(let i in layers) {
        const l = layers[i];
        const thisLayerId = layers[i].get('id');

        if(id === thisLayerId) {
            lyr = l;
            break;
        }
    }
	return lyr;
}

function getFeatureId(layer, id) {
	for(var i=0; i<layer.getSource().getFeatures().length; i++) {
		var feature = layer.getSource().getFeatures()[i];
		if(feature.get("id") == id) {
			return feature;
		}
	}
}

//선박 선택 이벤트
function shipSelectEvent(){
	$("#close_shipdetail").on('click',function(e){
		$("#shipdetail").hide();
		shipDetailSource.clear();
	});
	//TODO selected feature style 변경
        modStyleSelectInteraction = new ol.interaction.Select({
            condition: ol.events.condition.click,
            layers: function (lyr) {
                let id = lyr.get('id');
                if (id === "shipLayer") {
                    return true;
				}
                return false;
            }
        });
        modStyleSelectInteraction.setActive(true);

        modStyleSelectInteraction.on('select', function (e) {
            const selectedList = e.selected;
	        // 이전에 선택된 선박에 대한 처리
			e.target.getFeatures().clear();
			shipDetailSource.clear();
			var pointFeature = new ol.Feature({
				geometry: new ol.geom.Point([Number(selectedList[0].getGeometry().getCoordinates()[0]),Number(selectedList[0].getGeometry().getCoordinates()[1])])
			});
			pointFeature.setStyle(
			    new ol.style.Style({		            
			    	image: new ol.style.Icon({
				          	src: 'images/emap/shipIconSelect.png',
				          	anchor: [0.8, 0.8],	
				          	rotateWithView: true,
				          	rotation: selectedList[0].getProperties().Course!=null?selectedList[0].getProperties().Course:0		          	
		        		}),
			            text: new ol.style.Text({
			                textAlign: 'center',
			                font:  'bold '+shipStyle.font+'px Arial',
			                fill: new ol.style.Fill({color: shipStyle.color}),
			                stroke: new ol.style.Stroke({color:'#ffffff', width:0}),
			                text: ""+selectedList[0].getProperties().imoNumber,
			                offsetX: 0,
			                offsetY: -25,
			                overflow:true,
			            })
			    })
			);
			shipDetail_layer.getSource().addFeature(pointFeature);
            console.log(selectedList);
            $("#txt_mmsi").text(selectedList[0].getProperties().imoNumber);
            $("#txt_theading").text(selectedList[0].getProperties().Course);
            $("#txt_alarmStateTyoe").text(selectedList[0].getProperties().alarmStateTyoe);
            $("#txt_speed").text(selectedList[0].getProperties().Speed);
            $("#shipdetail").show();
        });
        map.addInteraction(modStyleSelectInteraction);
}
