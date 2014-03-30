import scripts.CitiesFileEacher as eacher

/**
 * Poll the weather forecast API at periodic intervals to simulate real data.
 */

random = new Random()
HOST = 'http://localhost:8080'

// Load up the cities.csv file and pop in into a handy array
CITIES = []
eacher.loadCitiesEachLine() { CITIES << it }

numCities = CITIES.size()
println " ++ Read $numCities cities from CSV file"

count = 0
while (count < numCities) {

    // Generate 20 random weather forecast requests
    (0..random.nextInt(20)).each {
        int idx = random.nextInt(numCities)
        String url = "$HOST/api/wevver?city=${CITIES[idx].name}&state=${CITIES[idx].state}"
        println url.replaceAll(' ', '+').toURL().getText()

        count++
    }

    println " ++ now processed $count forecast records"
    sleep(2000)
}

println ' ++ Done'