#!/bin/bash

usage() {
    echo "Use: $0 -u kune_db_username -p kune_db_passwd -d kune_db_name"
}

USERNAME="kune_prod"
PASS="db4kune"
DB="kune_prod"

while getopts “hu:p:d:” OPTION
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
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $PASS || -z $USERNAME || -z $DB ]]
then
    usage
    exit 1
fi


if [[ -z $WAVE_HOME ]]
then 
  echo "Please define were the Wave code is located using 'export WAVE_HOME=/some/path'"
  exit 1
fi

DELIMITER="|"

for i in `find $WAVE_HOME/src -name *Mess*_en.properties | tail -5` ; 
do 
  cat $i | \
  # Add newline at the end (if I rember)
  sed -e '$a\' | \
  # Remove empty lines  
  sed '/^[\b]*$/d' | \
  # Add file name key to the start (escaping /)
  sed "s/^/`echo $i | sed 's/\//\\\\\//g'`|/g" | \
  # Substitute first " = " with delimiter
  sed "0,/RE/s/ = /$DELIMITER/g" ; 
done | awk -F "|" '{
  select="mysql -p"$PASSWD" -u"$USERNAME" "$DB" < SELECT COUNT(*) FROM globalize_translations g WHERE gtype="$2;
  print select;
}'
