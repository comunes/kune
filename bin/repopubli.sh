#!/bin/bash

usage() {
    echo "Use: $0 -j <jar> -g <group> -a <artifact> -v <version> [-s (for source)] [-i (for internal repo instead of snapshot)"
    echo "$0 -j target/emite-0.4.6-emiteuimodule.jar -g com.calclab.emite -a emite -v 0.4.6"
}

PACKAGING='jar'
REPO=snapshots

while getopts “hig:a:v:j:s” OPTION
do
    case $OPTION in
	h)
            usage
            exit 1
            ;;
  i)
			REPO=internal
      ;;
	g)
	    GROUP=$OPTARG
	    ;;
	a)
	    ARTIFACT=$OPTARG
	    ;;
	v)
	    VER=$OPTARG
	    ;;
	j)
	    JAR=$OPTARG
	    ;;
	s)
            PACKAGING='java-source -DgeneratePom=false'
            ;;
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $GROUP ]] || [[ -z $ARTIFACT ]] || [[ -z $VER ]] || [[ -z $JAR ]] 
then
    usage
    exit 1
fi

mvn deploy:deploy-file -DgroupId=$GROUP \
	-DartifactId=$ARTIFACT \
	-Dversion=$VER \
	-Dpackaging=$PACKAGING \
	-Dfile=$JAR \
	-Durl=http://archiva.comunes.org/repository/comunes-$REPO \
	-DrepositoryId=comunes-$REPO
