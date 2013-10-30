#!/bin/bash

DEST=/usr/share/kune
HOST=kune@shell.kune.cc

rsync --partial bin/convertI18nMsgToMethods* $HOST:$DEST/bin/
rsync --partial bin/i18n-* $HOST:$DEST/bin/
ssh $HOST "cd $DEST; bin/i18n-all.sh"
scp $HOST:$DEST/KuneCon* src/main/java/cc/kune/core/client/i18n/
