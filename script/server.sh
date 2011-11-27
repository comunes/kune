#!/bin/bash

# This script will start the kune server via a generated jar with the dependencies

usage() {
    echo "$0 [-j <jar file>] [-k <kune-config>] [-w <wave-config>] [-s <jaas config>] [Debug options] [other options]
    Example: $0 -j target/kune-0.1.0-SNAPSHOT-jar-with-dependencies.jar -l IGNORE -d -p -u 20000

Options:
-j <jar file> : runs jar file generated via mvn package -Dmaven.test.skip=true
-a: run as a daemon (only root user)
-l LOGLEVEL : IGNORE|DEBUG|INFO|WARN

Debug Options:
-d: debug
-u: Suspend the start (useful for debug the boot)
-p: port of debugger
"
}

# Default values

# See src/main/resources/kune.properties in svn
KUNE_CONFIG=/etc/kune.properties 
# See src/main/resources/wave-server.properties in svn
WAVE_CONFIG=/etc/kune/wave-server.properties
# See src/main/resources/jaas.config in svn
JAAS_CONFIG=src/main/resources/jaas.config

SUSPEND="n"
DEBUG=""
DEBUG_PORT=""
LOG_LEVEL="INFO"
LOGFILE=/var/log/kune.log
PIDFILE=/var/run/kune.pid

while getopts “hj:k:w:s:up:l:da” OPTION
do
    case $OPTION in
	h)
            usage
            exit 1
            ;;
	j)
	    JAR=$OPTARG
	    ;;
	k)
	    KUNE_CONFIG=$OPTARG
	    ;;
	w)
	    WAVE_CONFIG=$OPTARG
	    ;;
	s)
            JAAS_CONFIG=$OPTARG
            ;;
	p)
	    # Debug port
	    DEBUG_PORT=,address=$OPTARG
	    ;;
	u)
	    SUSPEND="y"
	    ;;
        a)
	    DAEMON="y"	    
	    ;;
	l)
            # TODO: use/configure src/main/resources/log4j.properties
	    LOG_LEVEL=$OPTARG
	    ;;
	d)
	    DEBUG="y"
	    ;;
	?)
            usage
            exit
            ;;
    esac
done	

if [[ $LOG_LEVEL -ne "IGNORE" || $LOG_LEVEL -ne "DEBUG" || $LOG_LEVEL -ne "INFO" || $LOG_LEVEL -ne "WARN" ]]
then
    usage
    exit 1
fi

if [[ -n $DEBUG ]]
then
    DEBUG_FLAGS=-Xrunjdwp:transport=dt_socket,server=y,suspend=$SUSPEND$DEBUG_PORT
fi

USER=`id -u -n`
if [[ -n $DAEMON && USER -ne "root" ]]
then 
    echo "Error: Only root user can run kune as a deamon"
    usage
    exit 1
fi

if [[ -z $JAR ]] 
then
    # Just run kune using the code and mave
    mvn exec:java
else
    if [[ -n $DAEMON ]]
    then
    # FIXME Not sure if this is neccesary (if already configured in "/etc/security/limits.conf"
	ulimit -n 65000
    # http://stackoverflow.com/questions/534648/how-to-daemonize-a-java-program
	nohup java $DEBUG_FLAGS \
	    -Dorg.eclipse.jetty.util.log.$LOG_LEVEL=true \
	    -Djava.security.auth.login.config=$JAAS_CONFIG \
	    -Dkune.server.config=$KUNE_CONFIG \
	    -Dwave.server.config=$WAVE_CONFIG \
	    -jar $JAR </dev/null > $LOGFILE 2>&1 &
	echo $! > $PIDFILE
    else
	exec java $DEBUG_FLAGS \
	    -Dorg.eclipse.jetty.util.log.$LOG_LEVEL=true \
	    -Djava.security.auth.login.config=$JAAS_CONFIG \
	    -Dkune.server.config=$KUNE_CONFIG \
	    -Dwave.server.config=$WAVE_CONFIG \
	    -jar $JAR
    fi
fi