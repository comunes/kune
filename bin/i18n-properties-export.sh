#!/bin/bash

BASEDIR=$(dirname $0)/
. $BASEDIR/i18n-properties-lib.sh

# This produces something like
# 1774|NULL|Warning: Session data not available.|waveł./org/waveprotocol/box/webclient/client/i18n/SessionMessagesłsessionDataNotAvailable|1819|1774
# so
# id|text|tr_key|gtype|language_id|parend_id

mysql -B -p$PASS -u$USERNAME $DB --skip-column-names -e "SELECT id,text,tr_key,gtype,language_id,parent_id FROM globalize_translations g where g.gtype like '"$NAMESPACE"ł%'" | sed "s/\t/$DELIMITER/g" \
| awk -F "|" -v verbose=$VERBOSE -v noact=$NOACT -v dest=$EXTERNAL_SRC -v gtypeprefix="wave" -v passwd=$PASS -v username=$USERNAME -v db=$DB -f $BASEDIR/i18n-lib.awk -f $BASEDIR/i18n-export.awk

#| grep wave | tee /tmp/debug-export-i18n.txt \
#| grep TO_DEBUG_PUT_SOMETHING_AND_REMOVE_-V_AND_PUT_A_FILE_IN_TEE | tee /dev/null \
