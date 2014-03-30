#!/bin/sh
# Wrapper for Mongo Cities loading

groovy -cp src/test/groovy scripts/MongoLoad.groovy
exit 0
