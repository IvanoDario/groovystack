package wevver.dao

import org.junit.Test
import wevver.domain.City

/**
 * Unit test for our mongo based City DAO.
 */
class CityDAOTest extends GroovyTestCase {

    CityDAO dao = new MongoCityDAO()

    @Test
    void testfindCity() {
        def oaklands = dao.findCity('OAKLAND')
        assert oaklands.size() > 1
        oaklands.each{ assert it.name == 'OAKLAND'}
    }

    @Test
    void testfindSingleCity() {
        def oakland = dao.findCity('OAKLAND', 'CA')
        assert oakland != null
        assert oakland.name == 'OAKLAND'
        assert oakland.state == 'CA'
        // Bay Area, +ve lat, -ve lng
        assert oakland.latitude > 0.0
        assert oakland.longitude < 0.0
    }

    @Test
    void testDuffCityIsNull() {
        def dee = dao.findCity('DUNDEE', 'XX')
        assertNull(dee)
    }

    @Test
    void testTotalCitiesCount() {
        assert dao.numCities() > 0
    }

    @Test
    void testAllCities() {
        def all = dao.allCities()
        assert all.size() == dao.numCities()
        all.each { c ->
            assert c != null
            assertNotNull("city.name ${c.name} is null", c.name)
            assertNotNull("city.state ${c.state} is null", c.state)
            assertFalse("city.latitude ${c.latitude} is wrong", c.latitude == 0.0)
            assertFalse("city.longitude ${c.longitude} is wrong", c.longitude == 0.0)
        }
    }
}
