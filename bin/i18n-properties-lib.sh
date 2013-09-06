#!/bin/bash

CURRENT_LANG=*

SRC_PREFIX="Messages"

usage() {
    echo "Use: $0 -u kune_db_username -p kune_db_passwd -d kune_db_name -w path-to-src-code -n namespace [-e|-l fr] [-v]"
    echo "     -e : only process English"
    echo "     -l fr: only process French"
    echo "     -n wave: project short name that we add as a prefix to the ids"
    echo "     -v : verbose"
    echo "     -a : no act, dry run"
    echo "Sample: $0 -u kune -p db4kune -d kune_dev -w /home/user/projects/dev/wave/src -n wave"
}

while getopts “hu:p:d:w:l:m:n:eva” OPTION
do
    case $OPTION in
	h)
            usage
            exit 1
            ;;
	u)
	    USERNAME=$OPTARG
	    ;;
	p)
	    PASS=$OPTARG
	    ;;
	d)
	    DB=$OPTARG
	    ;;   
        w)
	    EXTERNAL_SRC=$OPTARG
	    ;;
	l)
	    CURRENT_LANG=$OPTARG
	    ;;
	m)
	    SRC_PREFIX=$OPTARG
	    ;;
	e)
	    CURRENT_LANG=en
	    ;;
	n)
	    NAMESPACE=$OPTARG
	    ;;
	v)
	    VERBOSE=1
	    ;;
	a)
	    NOACT=1
	    ;;
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $PASS || -z $USERNAME || -z $DB || -z $EXTERNAL_SRC || -z $NAMESPACE ]]
then
    usage
    exit 1
fi

if [[ -z $EXTERNAL_SRC ]]
then 
  echo "Please define were the external source code is located using 'export EXTERNAL_SRC=/some/path' or -w path option"
  exit 1
fi

DELIMITER="|"

# Removing end slash
# http://stackoverflow.com/questions/1848415/remove-slash-from-the-end-of-a-variable
# DIR=${EXTERNAL_SRC%/}/src

if [[ $EXTERNAL_SRC != *src && $EXTERNAL_SRC != *src/ ]]
then
  EXTERNAL_SRC_NEW=${EXTERNAL_SRC%/}"/src/"
  if [[ -d EXTERNAL_SRC_NEW ]]
  then
      EXTERNAL_SRC=${EXTERNAL_SRC%/}"/src/"
  else
      echo "Warning: We are usually looking for the src directory of the project"
  fi
fi