#!/bin/bash
for i in `find src -name *Mess*_en.properties | tail -5` ; 
do 
  FILE=`echo $i |sed 's/\//\./g'`
  cat $i | sed -e '$a\' | sed '/^[\b]*$/d' | sed "s/^/$FILE|/g" | sed '0,/RE/s/ = /|/g' ; 
done
