package wevver.soap

import org.junit.Test

/**
 * Tests for the Forecast XML slurper.
 */
class ForecastSlurperTest extends GroovyTestCase {

    def XML = '''<?xml version="1.0"?>
    <dwml version="1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://www.nws.noaa.gov/forecasts/xml/DWMLgen/schema/DWML.xsd">
      <head>
        <product srsName="WGS 1984" concise-name="dwmlByDay" operational-mode="official">
          <title>NOAA's National Weather Service Forecast by 24 Hour Period</title>
          <field>meteorological</field>
          <category>forecast</category>
          <creation-date refresh-frequency="PT1H">2014-03-29T15:41:51Z</creation-date>
        </product>
        <source>
          <more-information>http://www.nws.noaa.gov/forecasts/xml/</more-information>
          <production-center>Meteorological Development Laboratory<sub-center>Product Generation Branch</sub-center></production-center>
          <disclaimer>http://www.nws.noaa.gov/disclaimer.html</disclaimer>
          <credit>http://www.weather.gov/</credit>
          <credit-logo>http://www.weather.gov/images/xml_logo.gif</credit-logo>
          <feedback>http://www.weather.gov/feedback.php</feedback>
        </source>
      </head>
      <data>
        <location>
          <location-key>point1</location-key>
          <point latitude="42.94" longitude="-115.38"/>
        </location>
        <moreWeatherInformation applicable-location="point1">http://forecast.weather.gov/MapClick.php?textField1=42.94&amp;textField2=-115.38</moreWeatherInformation>
        <time-layout time-coordinate="local" summarization="24hourly">
          <layout-key>k-p24h-n3-1</layout-key>
          <start-valid-time>2014-03-29T06:00:00-06:00</start-valid-time>
          <end-valid-time>2014-03-30T06:00:00-06:00</end-valid-time>
          <start-valid-time>2014-03-30T06:00:00-06:00</start-valid-time>
          <end-valid-time>2014-03-31T06:00:00-06:00</end-valid-time>
          <start-valid-time>2014-03-31T06:00:00-06:00</start-valid-time>
          <end-valid-time>2014-04-01T06:00:00-06:00</end-valid-time>
        </time-layout>
        <time-layout time-coordinate="local" summarization="12hourly">
          <layout-key>k-p12h-n6-2</layout-key>
          <start-valid-time>2014-03-29T06:00:00-06:00</start-valid-time>
          <end-valid-time>2014-03-29T18:00:00-06:00</end-valid-time>
          <start-valid-time>2014-03-29T18:00:00-06:00</start-valid-time>
          <end-valid-time>2014-03-30T06:00:00-06:00</end-valid-time>
          <start-valid-time>2014-03-30T06:00:00-06:00</start-valid-time>
          <end-valid-time>2014-03-30T18:00:00-06:00</end-valid-time>
          <start-valid-time>2014-03-30T18:00:00-06:00</start-valid-time>
          <end-valid-time>2014-03-31T06:00:00-06:00</end-valid-time>
          <start-valid-time>2014-03-31T06:00:00-06:00</start-valid-time>
          <end-valid-time>2014-03-31T18:00:00-06:00</end-valid-time>
          <start-valid-time>2014-03-31T18:00:00-06:00</start-valid-time>
          <end-valid-time>2014-04-01T06:00:00-06:00</end-valid-time>
        </time-layout>
        <time-layout time-coordinate="local" summarization="24hourly">
          <layout-key>k-p3d-n1-3</layout-key>
          <start-valid-time>2014-03-29T06:00:00-06:00</start-valid-time>
          <end-valid-time>2014-04-01T06:00:00-06:00</end-valid-time>
        </time-layout>
        <parameters applicable-location="point1">
          <temperature type="maximum" units="Fahrenheit" time-layout="k-p24h-n3-1">
            <name>Daily Maximum Temperature</name>
            <value>56</value>
            <value>53</value>
            <value>54</value>
          </temperature>
          <temperature type="minimum" units="Fahrenheit" time-layout="k-p24h-n3-1">
            <name>Daily Minimum Temperature</name>
            <value>40</value>
            <value>32</value>
            <value>36</value>
          </temperature>
          <probability-of-precipitation type="12 hour" units="percent" time-layout="k-p12h-n6-2">
            <name>12 Hourly Probability of Precipitation</name>
            <value>86</value>
            <value>71</value>
            <value>41</value>
            <value>8</value>
            <value>14</value>
            <value>21</value>
          </probability-of-precipitation>
          <weather time-layout="k-p24h-n3-1">
            <name>Weather Type, Coverage, and Intensity</name>
            <weather-conditions weather-summary="Rain">
              <value coverage="definitely" intensity="light" weather-type="rain" qualifier="none"/>
            </weather-conditions>
            <weather-conditions weather-summary="Chance Rain Showers">
              <value coverage="chance" intensity="light" weather-type="rain showers" qualifier="none"/>
            </weather-conditions>
            <weather-conditions weather-summary="Slight Chance Rain Showers">
              <value coverage="slight chance" intensity="light" weather-type="rain showers" qualifier="none"/>
            </weather-conditions>
          </weather>
          <conditions-icon type="forecast-NWS" time-layout="k-p24h-n3-1">
            <name>Conditions Icons</name>
            <icon-link>http://www.nws.noaa.gov/weather/images/fcicons/ra90.jpg</icon-link>
            <icon-link>http://www.nws.noaa.gov/weather/images/fcicons/hi_shwrs40.jpg</icon-link>
            <icon-link>http://www.nws.noaa.gov/weather/images/fcicons/hi_shwrs20.jpg</icon-link>
          </conditions-icon>
          <hazards time-layout="k-p3d-n1-3">
            <name>Watches, Warnings, and Advisories</name>
            <hazard-conditions xsi:nil="true"/>
          </hazards>
        </parameters>
      </data>
    </dwml>'''

    ForecastSlurper forecastSlurper = new ForecastSlurper()

    @Test
    void testSlurperLoadsCorrectTemperaturesFromXML() {
        def xml = new XmlSlurper().parseText(XML)
        assert xml != null

        def forecast = forecastSlurper.slurp(xml)
        assert [56, 53, 54] == forecast.max_temps
        assert [40, 32, 36] == forecast.min_temps
    }

    @Test
    void testSlurperLoadsCallsback() {
        def city = [name: 'OAKLAND', state: 'CA', latitude: 37.772323, longitude: -122.214897]
        forecastSlurper.getForecast(city) { forecast ->
            log.info "Retrieved -> $forecast"
            assert forecast != null
            assert forecast.min_temps.size() > 0
            assert forecast.max_temps.size() > 0
        }
    }

}