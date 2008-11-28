#!/bin/bash
PARAM=$#
USER=$1
DEST=$2
if [ $PARAM -gt 0 ]
then
  EXTRA=$USER@
fi

if [ $PARAM -gt 1 ]
then
  EXTRADEST=$DEST/
fi

ORIG=target/kune-0.0.5/org.ourproject.kune.app.Kune/
DEST=/mnt/kunedev/var/lib/kune/www

chmod -R g+rw $ORIG
# from time to time use --delete
rsync --progress -C -r -p $ORIG ${EXTRA}ourproject.org:$DEST/$EXTRADEST

ssh ${EXTRA}ourproject.org "chgrp -R kune $DEST"
