#!/bin/bash
STR=`echo $1 | sed 's/ /\\[\\\"\\\+\\\n \\]\*/g'`
echo egrep -r -m 1 "\"$STR\"" src/main/java/cc