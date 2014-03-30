package wevver.service

import org.junit.Test
import wevver.service.SimpleWevverService
import wevver.service.Wevver

/**
 * Old school JUnit test for our wevver
 */
class WevverServiceTest extends GroovyTestCase {

    Wevver wevver = new SimpleWevverService();

    void setUp() {
        log.info 'Adding two sample forecasts'
        wevver.addForecast([name: 'OAKLAND', state: 'CA'],
                [city: 'OAKLAND', state: 'CA', max_temps: [1, 3, 5, 7], min_temps: [2, 4, 6, 8]])
        wevver.addForecast([name: 'DUNDEE', state: 'TX'],
                [city: 'DUNDEE', state: 'TX', max_temps: [11, 13, 15, 17], min_temps: [12, 14, 16, 18]])
    }

    @Test
    void testNumForecasts() {
        log.info 'checking wevver service has data'
        assert wevver.numForecasts() == 2
    }

    @Test
    void testGetAllForecasts() {
        log.info 'checking all forecasts'
        def forecasts = wevver.getAllForecasts()
        assert forecasts.size() == 2
        forecasts.each { f ->
            assert f != null
            assertNotNull("Forecast $f has no city", f.city)
            assertNotNull("Forecast $f has no state", f.state)
            assertNotNull("Forecast $f has no max_temps", f.max_temps)
            assertNotNull("Forecast $f has no min_temps", f.min_temps)
        }
    }

    @Test
    void testGetSingleForecasts() {
        log.info 'checking forecast for OAKLAND, CA'
        def f = wevver.getForecast('OAKLAND', 'CA')
        assert f != null
        assertEquals(f.city, 'OAKLAND')
        assert f.state == 'CA'
        assertNotNull("Forecast $f has no max_temps", f.max_temps)
        assertNotNull("Forecast $f has no min_temps", f.min_temps)
    }

    @Test
    void testClearForecast() {
        log.info 'clearing forecast for OAKLAND, CA'
        int numForecasts = wevver.numForecasts()
        assert numForecasts > 0

        def f = wevver.clearForecast('OAKLAND', 'CA')
        assert f != null
        assertEquals(f.city, 'OAKLAND')
        assert wevver.numForecasts() == --numForecasts
    }

    @Test
    void testClearForecastForNonExistentCity() {
        log.info 'clearing forecast for non-existent record'
        assert wevver.clearForecast('DUNDEE', 'XX') == null
    }

}