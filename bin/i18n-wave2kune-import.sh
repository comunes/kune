#!/bin/bash

usage() {
    echo "Use: $0 -u kune_db_username -p kune_db_passwd -d kune_db_name -w path-to-wiab-code "
}

# This only for development
USERNAME="kune"
PASS="db4kune"
DB="kune_dev"
WAVE_HOME=/home/vjrj/dev/wave/wiab/wiab-ro/

while getopts “hu:p:d:w:” OPTION
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

LIMIT=1

CURRENT_LANG=en

# Removing end slash
# http://stackoverflow.com/questions/1848415/remove-slash-from-the-end-of-a-variable
DIR=${WAVE_HOME%/}/src

for i in `find $DIR -name *Mess*_$CURRENT_LANG.properties | tail -$LIMIT`
do 
  LANG=`basename $i .properties | cut -d "_" -f 2`
  cat $i | \
  # Add newline at the end (if I remember)
  sed -e '$a\' | \
  # Remove empty lines  
  sed '/^[\b]*$/d' | \
  # Add file name key to the start (escaping /) and removing the last _langcode.properties 
  sed "s/^/$LANG|`echo $i | sed 's/_..\.properties//g' | sed 's/\//\\\\\//g'`|/g" | \
  # Substitute first " = " with delimiter
  sed "0,/RE/s/ = /$DELIMITER/g" ;   
done | awk -F "|" -v passwd=$PASS -v username=$USERNAME -v db=$DB '
function sql(operation) {
   cmd = connect "\""operation"\""
   cmd |& getline res
   close(cmd)
   print cmd
   return res
}

function getLangCode(lang) {
  select = "SELECT id FROM globalize_languages g WHERE code=\x27"lang"\x27"
  # print select
  return sql(select)
}

function getKeyInLang(somekey, somelang) {
  select = "SELECT count(*) FROM globalize_translations g WHERE gtype=\x27"somekey"\x27 AND language_id=\x27"somelang"\x27"
  return sql(select)
}

BEGIN {
  connect = "mysql -B -p"passwd" -u"username" "db" --skip-column-names -e "
  english=getLangCode("en")
}
{
  key = $2"|"$3
  print "key: "$3
  if ($1 == "en") {
    currentLang = english;
  } else {
     currentLang = getLangCode($1)
  }
  result = getKeyInLang(key, currentLang)
  if (result > 0) {
    print "Already in db"
  } else {
    # print "Dont exists, so insert"
    if (currentLang == english) {
       # just insert
    } else {
       parent = getKeyInLang(key, english)
       # find english parent
       if (parent > 0) {
          # parent found, insert with reference
          # insert = connect "\"INSERT INTO globalize_translations VALUES (NULL,\x27\x27,NULL,1,\x27\x27,\x27"$3"\x27,\x27"$3"\x27,\x27"key"\x27,1819)\""
       } else {
          # parent dont exit, ignore by now
       }
    }
  }
}
END {
  # 1819: English 
  # print english
}
'

