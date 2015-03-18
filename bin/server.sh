#!/bin/bash

# This script will start the kune server via a generated jar with the dependencies

usage() {
    echo "$0 [-j <jar file>] [-k <kune-config>] [-w <wave-config>] [-s <jaas config>] [Debug options] [other options]
    Example: $0 -j target/kune-1.0.1-SNAPSHOT-jar-with-dependencies.jar -l IGNORE -d -p -u 20000 -o 8079 -y somekey

Options:
-j <jar file> : runs jar file generated via mvn assembly:assembly
                or adding -Dgwt.compiler.skip=true to skip compilation
-a: run as a daemon (only root user)
-l LOGLEVEL : IGNORE|DEBUG|INFO|WARN
-x: -Xmx memory value
-m: -Xms memory value
-o: stop port
-y: stop secret key

Debug Options:
-d: debug
-u: Suspend the start (useful for debug the boot)
-p: port of debugger
"
}

# Default values


if [ -z $KUNE_HOME ]
then
    KUNE_HOME=/etc/kune
fi

# See src/main/resources/kune.properties in svn
KUNE_CONFIG=$KUNE_HOME/kune.properties 
# See src/main/resources/wave-server.properties in svn
WAVE_CONFIG=$KUNE_HOME/wave-server.properties
# See src/main/resources/jaas.config in svn
JAAS_CONFIG=$KUNE_HOME/jaas.config
# See src/main/resources/log4j.properties in svn
LOGJ4_CONFIG=file://$KUNE_HOME/log4j.properties

SUSPEND="n"
DEBUG=""
DEBUG_PORT=""
LOG_LEVEL="INFO"
LOGFILE=/var/log/kune/kune.log
PIDFILE=/var/run/kune.pid
MX=""
MS=""
STOP_KEY=somekey
STOP_PORT=8079

while getopts “hm:x:j:k:w:s:up:l:dao:y:t” OPTION
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
        x)
            MX="-Xmx"$OPTARG
            ;;
        m)
            MS="-Xms"$OPTARG
            ;;
        p)
            # Debug port
            PORT=$OPTARG
            if ! [[ $PORT =~ ^[0-9]+$ ]] 
            then
                usage
                exit 1
            fi
            DEBUG_PORT=",address=$PORT"
            ;;
        u)
            SUSPEND="y"
            ;;
        a)
            DAEMON="y"      
            ;;
        l)
            LOG_LEVEL=$OPTARG
            ;;
        o)
            STOP_PORT=$OPTARG
            ;;
        y)
            STOP_KEY=$OPTARG
            ;;
        t)
            STOP_OPT="--stop"
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

if [[ $LOG_LEVEL != "IGNORE" && $LOG_LEVEL != "DEBUG" && $LOG_LEVEL != "INFO" && $LOG_LEVEL != "WARN" ]]
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
    echo "Error: Only root user can run kune as a daemon"
    usage
    exit 1
fi

if [[ -z $JAR ]] 
then
    # Just run kune using the code and mave
    mvn exec:java -Dexec.args="$DEBUG_FLAGS"
else
    if [[ -n $DAEMON ]]
    then
    # FIXME Not sure if this is neccesary (if already configured in "/etc/security/limits.conf"
        ulimit -n 65000
    # http://stackoverflow.com/questions/534648/how-to-daemonize-a-java-program
        exec java $DEBUG_FLAGS \
            -Dorg.eclipse.jetty.LEVEL=$LOG_LEVEL \
            -Djava.security.auth.login.config=$JAAS_CONFIG \
            -Dlog4j.configuration=$LOGJ4_CONFIG \
            -Dkune.server.config=$KUNE_CONFIG \
            -Dwave.server.config=$WAVE_CONFIG \
            -Djava.awt.headless=true \
            -DSTOP.PORT=$STOP_PORT \
            -DSTOP.KEY=$STOP_KEY \
            $STOP_OPT \
            $MS \
	    $MX \
	    -jar $JAR >> $LOGFILE 2>> $LOGFILE
    else
	exec java $DEBUG_FLAGS \
      -Dorg.eclipse.jetty.LEVEL=$LOG_LEVEL \
	    -Djava.security.auth.login.config=$JAAS_CONFIG \
	    -Dlog4j.configuration=$LOGJ4_CONFIG \
	    -Dkune.server.config=$KUNE_CONFIG \
	    -Dwave.server.config=$WAVE_CONFIG \
            -Djava.awt.headless=true \
            -DSTOP.PORT=$STOP_PORT \
            -DSTOP.KEY=$STOP_KEY \
            $STOP_OPT \
            $MS \
	    $MX \
	    -jar $JAR >> $LOGFILE 2>> $LOGFILE
    fi
fi
