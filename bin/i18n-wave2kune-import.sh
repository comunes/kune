#!/bin/bash

usage() {
    echo "Use: $0 -u kune_db_username -p kune_db_passwd -d kune_db_name -w path-to-wiab-code [-e]"
    echo "-e : only process English"
}

CURRENT_LANG=*

# This only for development
USERNAME="kune"
PASS="db4kune"
DB="kune_dev"
WAVE_HOME=/home/vjrj/dev/wave/wiab/wiab-rw/

while getopts “hu:p:d:w:e” OPTION
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
	    WAVE_HOME=$OPTARG
	    ;;
	e)
	    CURRENT_LANG=en
	    ;;
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $PASS || -z $USERNAME || -z $DB || -z $WAVE_HOME ]]
then
    usage
    exit 1
fi


if [[ -z $WAVE_HOME ]]
then 
  echo "Please define were the Wave code is located using 'export WAVE_HOME=/some/path' or -w path option"
  exit 1
fi

DELIMITER="|"

# Removing end slash
# http://stackoverflow.com/questions/1848415/remove-slash-from-the-end-of-a-variable
# DIR=${WAVE_HOME%/}/src

# TODO process first *_en and later the rest of languages, so do here a function

for i in `(cd $WAVE_HOME; find src -name *Mess*_$CURRENT_LANG.properties)`
do
  LANG=`basename $i .properties | cut -d "_" -f 2`
  cat $WAVE_HOME$i | \
  # Add newline at the end (if I remember)
  sed -e '$a\' | \
  # Remove empty lines  
  sed '/^[\b]*$/d' | \
  # Add file name key to the start (escaping /) and removing the last _langcode.properties 
  sed "s/^/$LANG|`echo $i | sed 's/_..\.properties//g' | sed 's/\//\\\\\//g'`|/g" | \
  # Substitute first " = " with delimiter
  sed "0,/RE/s/ = /$DELIMITER/g" ;   
done | awk -F "|" -v gtypeprefix="wave" -v passwd=$PASS -v username=$USERNAME -v db=$DB -f i18n-lib.awk -f i18n-wave2kune-import.awk