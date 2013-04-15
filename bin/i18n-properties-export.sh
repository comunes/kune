#!/bin/bash

BASEDIR=$(dirname $0)/
. $BASEDIR/i18n-properties-lib.sh

# This produces something like
# 1774|NULL|Warning: Session data not available.|waveł./org/waveprotocol/box/webclient/client/i18n/SessionMessagesłsessionDataNotAvailable|1819|1774
# so
# id|text|tr_key|gtype|language_id|parend_id

mysql -B -p$PASS -u$USERNAME $DB --skip-column-names -e "SELECT id,text,tr_key,gtype,language_id,parent_id FROM globalize_translations g where g.gtype like '"$NAMESPACE"ł%'" | sed "s/\t/$DELIMITER/g" \
| grep wave | tee /tmp/debug-i18n.txt \
| awk -F "|" -v dest=$EXTERNAL_SRC -v gtypeprefix="wave" -v passwd=$PASS -v username=$USERNAME -v db=$DB -f $BASEDIR/i18n-lib.awk -f $BASEDIR/i18n-export.awk

#| grep TO_DEBUG_PUT_SOMETHING_AND_REMOVE_-V_AND_PUT_A_FILE_IN_TEE | tee /dev/null \


exit

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

done \

