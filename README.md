
    .oPYo.                                            o         8
    8    8                                            8         8
    8      oPYo. .oPYo. .oPYo. o    o o    o .oPYo.  o8P .oPYo. 8  .o
    8   oo 8  `' 8    8 8    8 Y.  .P 8    8 Yb..     8  .oooo8 8oP'
    8    8 8     8    8 8    8 `b..d' 8    8   'Yb.   8  8    8 8 `b.
    `YooP8 8     `YooP' `YooP'  `YP'  `YooP8 `YooP'   8  `YooP8 8  `o.
    :....8 ..:::::.....::.....:::...:::....8 :.....:::..::.....:..::...
    :::::8 :::::::::::::::::::::::::::::ooP'.::::::::::::::::::::::::::
    :::::..:::::::::::::::::::::::::::::...::::::::::::::::::::::::::::

Simple Weather forecast webapp built with **Spring Boot**, **Gradle** and **Mongo**

Before you do anything, you will need to have a local mongo instance running.
You can then populate the **groovy-wevver** db with some cities

    ./monger.sh

Then you can start up the app with the usual

    gradle bootRun

Navigate to _http://localhost:8080/_ and you you should see an empty Cities page managed by AngularJS.

The AngularJS controller will continually poll groovystack to check for updated forecast data.

To populate some random weather forecast data use **Stiffler**

    ./stiffler.sh

Groovystack is a simple FrontController with a simple REST-ish API

Here's some routes

To get an individual forecast for a city and state

    /api/wevver?city=OAKLAND&state=CA

To get all known forecasts

    /api/wevvers

To get an individual city record from mongo

    /api/city?name=OAKLAND&state=CA

To get all the cities called OAKLAND

    /api/city?name=OAKLAND

If you've had enough, remove a forecast for a city and state

    /api/clear?name=OAKLAND&state=CA

Enjoy!