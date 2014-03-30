package wevver.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import wevver.soap.ForecastSlurper

import java.util.logging.Logger

/**
 * Simple Implementation of our Weather forecast service.
 */
@Service
@Component
class SimpleWevverService implements Wevver {

    static final Logger log = Logger.getLogger(SimpleWevverService.name)

    ForecastSlurper slurper = new ForecastSlurper()
    def forecasts = [:]

    def getForecast(cityName, state) {
        log.info "Checking if we already have a forecast for $cityName and $state"
        forecasts[[city: cityName, state: state]]
    }

    def getForecast(city) {
        log.info "Retrieving remote forecast for $city"
        slurper.getForecast(city) { forecasts[[city: city.name, state: city.state]] = it }
    }

    def getAllForecasts() {
        forecasts.values()
    }

    def clearForecast(cityName, state) {
        log.info "Clearing forecast for $cityName and $state"
        forecasts.remove([city: cityName, state: state])
    }

    def addForecast(city, forecast) {
        log.info "Adding forecast for $city"
        forecasts[[city: city.name, state: city.state]] = forecast
    }

    int numForecasts() {
        forecasts.size()
    }
}