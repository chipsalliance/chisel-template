#!/bin/sh
myDate=${1:-`date +%Y-%m-%d`}
shift
sbt version | gawk -v myDate=$myDate -e '/^\[info\] [[:digit:]]+\.[[:digit:]]-SNAPSHOT/ { print "v" $2 "_" myDate}'
