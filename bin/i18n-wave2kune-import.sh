#!/bin/bash

usage() {
    echo "Use: $0 -u kune_db_username -p kune_db_passwd -d kune_db_name"
}

USERNAME="kune"
PASS="db4kune"
DB="kune_dev"
WAVE_HOME=/home/vjrj/dev/wave/

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
done | awk -F "|" -v passwd=$PASS -v username=$USERNAME -v db=$DB '{
  key = $1"|"$2
  connect = "mysql -p"passwd" -u"username" "db" -e "
  select = connect "\"SELECT * FROM globalize_translations g WHERE gtype=\x27"key"\x27\""
  print select
  # system(select)
  select
  # |& getline result
  while ( ( select | getline result ) > 0 ) {
    print result
  } 
  #close(select)  
  # print result
  # 1819: English
  insert = connect "\"INSERT INTO globalize_translations VALUES (NULL,\x27\x27,NULL,1,\x27\x27,\x27"$3"\x27,\x27"$3"\x27,\x27"key"\x27,1819)\""
  # print insert;
}'

