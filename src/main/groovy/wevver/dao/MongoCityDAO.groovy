package wevver.dao

import com.gmongo.GMongo
import org.apache.log4j.Logger
import org.springframework.stereotype.Component
import wevver.domain.City

/**
 * Mongo based City DAO object.
 */
@Component
class MongoCityDAO implements CityDAO {

    static final Logger log = Logger.getLogger(MongoCityDAO.name)
    def db
    def cities = new WeeCache()

    def findCity(name, state) {
        log.info "finding one single city: $name with state: $state"
        def city = null
        def cached = cities.get(name, state)
        if (cached) {
            log.info "Wee cache hit for $cached"
            city = cached
        }

        def res = cities().findOne(name: name, state: state);
        if (res) {
            city = new City(name: res.name, state: res.state, longitude: res.longitude, latitude: res.latitude)
            cities.put(city)
        }
        return city
    }

    def findCity(name) {
        log.info "finding city(s) $name"
        List res = []
        cities().find(name: name).each{
            def c = new City(name: it.name, state: it.state, longitude: it.longitude, latitude: it.latitude)
            cities.put(c)
            res << c
        }
        return res
    }

    def allCities() {
        log.info 'finding all cities (good luck)'
        def res = []
        cities().find().each{ c ->
            res << new City(name: c.name, state: c.state, longitude: c.lontitude, latitude: c.latitude)
        }
        return res
    }

    int numCities() {
        cities().count();
    }

    private cities() {
        if (db == null) {
            log.debug "loading GMongo client"
            def mongo = new GMongo()
            db = mongo.getDB("groovy-wevver")
            assert db != null
        }
        return db.cities
    }

    /**
     * V. Simple cache for storing our City objects
     */
    class WeeCache {
        def cache = [:]

        def get(name, state) {
            // Use the combination of name and state as cache key
            cache[[name: name, state:state]]
        }

        def put(city) {
            def key = [name: city.name, state: city.state]
            if (! cache[key]) {
                cache[key] = city
            }
        }
    }

}