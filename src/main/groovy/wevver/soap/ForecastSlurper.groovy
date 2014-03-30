package wevver.soap

import groovyx.net.http.AsyncHTTPBuilder

import java.util.logging.Logger

import static groovyx.net.http.ContentType.XML

/**
 * Async Weather forecast SOAP slurper.
 */
class ForecastSlurper {

    static final Logger log = Logger.getLogger(ForecastSlurper.name)

    // Async HTTPBuilder for retrieving our remote SOAP, weather requests
    def http = new AsyncHTTPBuilder(
            poolSize : 4,
            uri : 'http://graphical.weather.gov/',
            contentType : XML )

    String YESTERDAY = new Date().minus(1).format('YYYY-MM-dd')

    /**
     * Make a GET request for the weather forecast XML.
     *
     * @param city The City object to use for our query.
     * @param callback The closure to call upon success.
     * @return Nothing of interest.
     */
    def getForecast(city, callback) {
        def result = http.get(path: 'xml/SOAP_server/ndfdSOAPclientByDay.php',
                query:[
                        whichClient: 'NDFDgenByDay',
                        format: '24 hourly',
                        numDays: 7,
                        Unit: 'e',
                        lat: city.latitude,
                        lon: city.longitude,
                        startDate: YESTERDAY
                ]) { resp, xml ->
            log.info 'retrieved async weather response, now attempting to slurp XML'
            def forecast = slurp(xml)
            if (forecast) {
                log.info "adding forecast for $city"
                callback([city: city.name,
                          state: city.state,
                          max_temps: forecast.max_temps,
                          min_temps: forecast.min_temps])
            }
        }
        result.get() // fire off the GET request
    }

    /**
     * Slurps the supplied weather XML object and plucks out the max and min temperatures.
     *
     * @param xml The weather XML object
     * @return The forecast data containing max and min temperature arrays
     */
    @SuppressWarnings("GrMethodMayBeStatic")
    def slurp(xml) {
        if (!xml) {
            return null
        }

        def temps = xml.data.parameters.temperature
        def maxs = temps.findAll { it.@type == 'maximum' }.value.collect { it.text()?.toInteger() }
        def mins = temps.findAll { it.@type == 'minimum' }.value.collect { it.text()?.toInteger() }
        [max_temps: maxs, min_temps: mins]
    }
}
