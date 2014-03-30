package wevver.service

/**
 * Interface describing what we expect from the Weather Forecast Service.
 */
interface Wevver {

    def getForecast(cityName, state)

    def getForecast(city)

    def getAllForecasts()

    def addForecast(city, forecast)

    def clearForecast(cityName, state)

    int numForecasts()
}