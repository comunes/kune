#!/bin/bash

BASEDIR=$(dirname $0)/
. $BASEDIR/i18n-properties-lib.sh

for i in `(cd $EXTERNAL_SRC; find . -name *$SRC_PREFIX*_$CURRENT_LANG.properties)`
do
  LANG=`basename $i .properties | cut -d "_" -f 2`
  cat $EXTERNAL_SRC/$i | \
  # Add newline at the end (if I remember)
  sed -e '$a\' | \
  # Remove empty lines  
  sed '/^[\b]*$/d' | \
  # Remove comments  
  sed '/^[\b]*#.*$/d' | \
  # Add file name key to the start (escaping /) and removing the last _langcode.properties 
  sed "s/^/$LANG|`echo $i | sed 's/_..\.properties//g' | sed 's/\//\\\\\//g'`|/g" | \
  # Substitute first " = " with delimiter
  sed "0,/RE/s/ =[ ]*/$DELIMITER/g" ;   

# This produces something like
# fr|src/org/waveprotocol/box/webclient/widget/error/i18n/ErrorMessages|everythingShiny|"Tout est OK, capitaine. Pas de panique !"
# so
# lang|file-used-in-gtype|trkey|text

#done | grep TO_DEBUG_PUT_SOMETHING_AND_REMOVE_-V_AND_PUT_A_FILE_IN_TEE | tee /dev/null \
#done | grep something | tee /tmp/debug-i18n.txt \
done \
| awk -F "|" -v gtypeprefix=$NAMESPACE -v passwd=$PASS -v username=$USERNAME -v db=$DB -f $BASEDIR/i18n-lib.awk -f $BASEDIR/i18n-import.awk