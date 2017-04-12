#!/bin/sh
myDate=${1:-`date +%Y-%m-%d`}
shift
echo "-m \"master `git log -1 | grep Merge: | cut -d ' ' -f 3`\"" `../getVersion.sh $myDate`
