//https://docs.geoserver.org/stable/en/user/styling/sld/reference/index.html 참고
//lynm : 레이어 id
//type : 도형 타입
function make_sld(lynm, type, color1, color2) {
	var text_SLD = "\
					<?xml version='1.0' encoding='UTF-8'?>\
					<StyledLayerDescriptor version='1.0.0' \
					 xsi:schemaLocation='http://www.opengis.net/sld StyledLayerDescriptor.xsd' \
					 xmlns='http://www.opengis.net/sld' \
					 xmlns:ogc='http://www.opengis.net/ogc' \
					 xmlns:xlink='http://www.w3.org/1999/xlink' \
					 xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\
					  <NamedLayer>\
					    <Name>emap:" + lynm + "</Name>\
					    <UserStyle>\
					      <Title>Default Polygon</Title>\
					      <Abstract>A sample style that draws a polygon</Abstract>\
					      <FeatureTypeStyle>\
					        <Rule>\
					"

    if(type == "Polygon1") {
    	text_SLD += "\
					          <PolygonSymbolizer>\
            					<Fill>\
					              <CssParameter name='fill'>#" + color1 + "</CssParameter>\
					            </Fill>\
					          </PolygonSymbolizer>\
					"
    } else if(type == "Polygon1_1") { // 경도 경계에 흰색 선 생기는 것 방지 : 테두리를 최소화하고 색상 동일화
    	text_SLD += "\
					          <PolygonSymbolizer>\
            					<Fill>\
					              <CssParameter name='fill'>#" + color1 + "</CssParameter>\
					            </Fill>\
					            <Stroke>\
					              <CssParameter name='stroke'>#" + color1 + "</CssParameter>\
					              <CssParameter name='stroke-width'>1</CssParameter>\
					            </Stroke>\
					          </PolygonSymbolizer>\
					"
    } else if(type == "Polygon1_2") { // 경도 경계에 흰색 선 생기는 것 방지 : 테두리를 최소화하고 색상 동일화
    	text_SLD += "\
					          <PolygonSymbolizer>\
            					<Fill>\
					              <CssParameter name='fill'>#" + color1 + "</CssParameter>\
					            </Fill>\
					            <Stroke>\
					              <CssParameter name='stroke'>#6689CA</CssParameter>\
					              <CssParameter name='stroke-width'>1</CssParameter>\
					            </Stroke>\
					          </PolygonSymbolizer>\
					"
    } else if(type == "Polygon2") {
    	text_SLD += "\
					          <PolygonSymbolizer>\
					            <Fill>\
					              <CssParameter name='fill'>#" + color1 + "</CssParameter>\
					            </Fill>\
					            <Stroke>\
					              <CssParameter name='stroke'>#" + color1 + "</CssParameter>\
					              <CssParameter name='stroke-width'>0</CssParameter>\
					            </Stroke>\
					          </PolygonSymbolizer>\
					        </Rule>\
							<Rule>\
					          <Title>0to20</Title>\
					          <ogc:Filter>\
					            <ogc:And>\
					              <ogc:PropertyIsGreaterThanOrEqualTo>\
					                <ogc:PropertyName>DRVAL1</ogc:PropertyName>\
					                <ogc:Literal>0</ogc:Literal>\
					              </ogc:PropertyIsGreaterThanOrEqualTo>\
					              <ogc:PropertyIsLessThan>\
					                <ogc:PropertyName>DRVAL1</ogc:PropertyName>\
					                <ogc:Literal>20</ogc:Literal>\
					              </ogc:PropertyIsLessThan>\
					            </ogc:And>\
					          </ogc:Filter>\
					          <PolygonSymbolizer>\
					            <Fill>\
					              <CssParameter name='fill'>#" + color1 + "</CssParameter>\
					            </Fill>\
					            <Stroke>\
					              <CssParameter name='stroke'>#" + color1 + "</CssParameter>\
					              <CssParameter name='stroke-width'>1</CssParameter>\
					            </Stroke>\
					          </PolygonSymbolizer>\
					        </Rule>\
					        <Rule>\
					          <Title>20to100</Title>\
					          <ogc:Filter>\
					            <ogc:And>\
					              <ogc:PropertyIsGreaterThanOrEqualTo>\
					                <ogc:PropertyName>DRVAL1</ogc:PropertyName>\
					                <ogc:Literal>20</ogc:Literal>\
					              </ogc:PropertyIsGreaterThanOrEqualTo>\
					              <ogc:PropertyIsLessThan>\
					                <ogc:PropertyName>DRVAL1</ogc:PropertyName>\
					                <ogc:Literal>100</ogc:Literal>\
					              </ogc:PropertyIsLessThan>\
					            </ogc:And>\
					          </ogc:Filter>\
					          <PolygonSymbolizer>\
					            <Fill>\
					              <CssParameter name='fill'>#" + color2 + "</CssParameter>\
					            </Fill>\
					            <Stroke>\
					              <CssParameter name='stroke'>#" + color2 + "</CssParameter>\
					              <CssParameter name='stroke-width'>1</CssParameter>\
					            </Stroke>\
					          </PolygonSymbolizer>\
					        </Rule>\
					        <Rule>\
					          <Title>100great</Title>\
					          <ogc:Filter>\
					              <ogc:PropertyIsGreaterThanOrEqualTo>\
					                <ogc:PropertyName>DRVAL1</ogc:PropertyName>\
					                <ogc:Literal>100</ogc:Literal>\
					              </ogc:PropertyIsGreaterThanOrEqualTo>\
					          </ogc:Filter>\
					          <PolygonSymbolizer>\
					            <Fill>\
					              <CssParameter name='fill'>#" + color2 + "</CssParameter>\
					            </Fill>\
					            <Stroke>\
					              <CssParameter name='stroke'>#" + color2 + "</CssParameter>\
					              <CssParameter name='stroke-width'>1</CssParameter>\
					            </Stroke>\
					          </PolygonSymbolizer>\
					"
    }
    
    text_SLD += "\
				        </Rule>\
				      </FeatureTypeStyle>\
				    </UserStyle>\
				  </NamedLayer>\
				</StyledLayerDescriptor>\
				"

	var source = getLayer(lynm).getSource();
    //var source = getLayerGroup(lynm).getSource();
    source.updateParams({ 'STYLES': '' ,'SLD_BODY': text_SLD });
}