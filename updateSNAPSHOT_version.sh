#!/bin/sh
releaseDate=$1
shift
sed -E -i.bak -e "s/\"([[:digit:]]+\.[[:digit:]]+-SNAPSHOT)\"/\"\1_$releaseDate\"/" $@