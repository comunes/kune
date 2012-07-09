#!/bin/bash

PARAMS=$#
JAR=$1
GROUP=$2
ARTIFACT=$3
VER=$4
USER=$5

# CORRECT PARAMS ###############################################################

if [ $PARAMS -lt 4 ]
then
  echo "Use: $0 <jar> <group> <artifact> <version> <username>"
  echo "$0 target/emite-0.4.6-emiteuimodule.jar com.calclab.emite emite 0.4.6 [luther]"
  exit
fi

if [ $PARAMS -gt 4 ]
then
  EXTRA=$USER@
fi

mvn deploy:deploy-file -DgroupId=$2 -DartifactId=$3 -Dversion=$4 -Dclassifier=sources -Dpackaging=jar -Dfile=$1 -Durl=scpexe://shell.ourproject.org/home/groups/kune/htdocs/mavenrepo/ -DrepositoryId=kune.ourproject.org
