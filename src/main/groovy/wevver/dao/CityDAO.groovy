package wevver.dao

import wevver.domain.City

/**
 * City DAO interface.
 */
interface CityDAO {

    def findCity(name)

    def findCity(name, state)

    def allCities()

    int numCities()

}