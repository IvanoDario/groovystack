package wevver.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import wevver.dao.CityDAO
import wevver.service.Wevver

import java.util.logging.Logger

/**
 * Front controller than manages all routes for our application and dispatches accordingly.
 */
@Controller
class FrontController {

    static final Logger log = Logger.getLogger(FrontController.name)

    // Spring loaded
    @Autowired private CityDAO cityDAO;
    @Autowired private Wevver wevver;

    @RequestMapping('/api/city')
    public @ResponseBody city(
            @RequestParam(value='name', required=true) String name,
            @RequestParam(value='state', required=false) String state) {

        // call relevant DAO method depending on presence of state parameter.
        state ? cityDAO.findCity(name, state) : cityDAO.findCity(name)
    }

    @RequestMapping('/api/cities')
    public @ResponseBody cities() {
        cityDAO.allCities()
    }

    @RequestMapping('/api/wevver')
    public @ResponseBody wevver(
            @RequestParam(value='city', required=true) String city,
            @RequestParam(value='state', required=true) String state) {

        log.info "checking forecast for city $city and state $state"
        wevver.getForecast(city, state) ?: wevver.getForecast(cityDAO.findCity(city, state));
    }

    @RequestMapping('/api/wevvers')
    public @ResponseBody wevvers() {
        log.info 'retrieving all known forecasts'
        wevver.getAllForecasts();
    }

    @RequestMapping('/api/clear')
    public @ResponseBody clearWevver(
            @RequestParam(value='city', required=true) String city,
            @RequestParam(value='state', required=true) String state) {

        log.info "Forecast cleared for city $city and state $state"
        wevver.clearForecast(city, state)
    }

    @RequestMapping('/')
    public String index() {
        return 'index'
    }
}