
@Grab(group='com.gmongo', module='gmongo', version='1.3')

import com.gmongo.GMongo
import scripts.CitiesFileEacher as eacher

/**
 * Load the list of cities from a CSV file into groovy-wevver mongodb
 */
mongo = new GMongo()
db = mongo.getDB("groovy-wevver")
assert db != null

println ' >> Connected to MongoDB at ' + new Date()
db.cities.drop()

// Pull in our handy CityFileEacher
count = 0
eacher.loadCitiesEachLine() {
    db.cities << it
    count++
}

// Check all is now in order
assert db.cities.count() == count
println " << Finished loading $count cities - ${new Date()} into mongo"
