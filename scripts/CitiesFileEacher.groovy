package scripts

/**
 * Load the list of cities, strip out the quotes and call our closure.
 */
class CitiesFileEacher {

    static loadCitiesEachLine(closure) {
        String filePath = 'src/test/resources/cities.csv'
        println " ++ Loading cities from file $filePath at ${new Date()}"

        int count = 0
        new File(filePath).eachLine() {
            // clean up the text line ex - "ROBERTS","ID","43.698922","-112.173195"
            def t = it.replaceAll('"', '').split(',')

            // call the closure with the City document
            closure([name: t[0], state: t[1], latitude: t[2].toDouble(), longitude: t[3].toDouble()])

            count++
            if ((count % 100) == 0) {
                println " >> Processed $count cities ..."
            }
        }
    }
}