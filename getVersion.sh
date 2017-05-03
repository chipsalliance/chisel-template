#!/bin/sh
# As of 5/3/2017, we include the date in the sbt version so there's no need to add it here.
sbt version | gawk -e '/^\[info\] [[:digit:]]+\.[[:digit:]]-SNAPSHOT/ { print "v" $2 }'
