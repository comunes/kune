#!/bin/bash

usage() {
    echo "Use: $0 -u kune_db_username -p kune_db_passwd -d kune_db_name -w path-to-src-code [-e|-l fr]"
    echo "     -e : only process English"
    echo "     -l fr: only process French"
    echo "Note: is recomended to first run with -e option to process English language"
    echo "Sample: $0 -u kune -p db4kune -d kune_dev -w /home/user/projects/dev/wave/src"
}

CURRENT_LANG=*

SRC_PREFIX="Messages"

while getopts “hu:p:d:w:l:m:e” OPTION
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
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $PASS || -z $USERNAME || -z $DB || -z $EXTERNAL_SRC ]]
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

# TODO process first *_en and later the rest of languages, so do here a function
BASEDIR=$(dirname $0)

for i in `(cd $EXTERNAL_SRC; find . -name *$SRC_PREFIX*_$CURRENT_LANG.properties)`
do
  LANG=`basename $i .properties | cut -d "_" -f 2`
  cat $EXTERNAL_SRC/$i | \
  # Add newline at the end (if I remember)
  sed -e '$a\' | \
  # Remove empty lines  
  sed '/^[\b]*$/d' | \
  # Add file name key to the start (escaping /) and removing the last _langcode.properties 
  sed "s/^/$LANG|`echo $i | sed 's/_..\.properties//g' | sed 's/\//\\\\\//g'`|/g" | \
  # Substitute first " = " with delimiter
  sed "0,/RE/s/ =[ ]*/$DELIMITER/g" ;   

# This produces something like
# fr|src/org/waveprotocol/box/webclient/widget/error/i18n/ErrorMessages|everythingShiny|"Tout est OK, capitaine. Pas de panique !"
# so
# lang|file-used-in-gtype|trkey|text

#done | grep TO_DEBUG_PUT_SOMETHING_AND_REMOVE_-V_AND_PUT_A_FILE_IN_TEE | tee /dev/null \
#done | grep everything | tee /tmp/debug-i18n.txt \
done \
| awk -F "|" -v gtypeprefix="wave" -v passwd=$PASS -v username=$USERNAME -v db=$DB -f $BASEDIR/i18n-lib.awk -f $BASEDIR/i18n-import.awk