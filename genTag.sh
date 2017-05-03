#!/bin/sh
shift
echo "-m \"master `git log --merges -n 1 | grep Merge: | cut -d ' ' -f 3`\"" `../getVersion.sh`
