#!/bin/bash
APPPUB=src/main/java/org/ourproject/kune/app/public
WEBAPP=src/main/webapp/gwt/ws

$PWD/script/css-compact-and-tidy.sh

rsync -aC $APPPUB/ws.html $WEBAPP/ws.html
rsync -aC $APPPUB/js/ $WEBAPP/js
rsync -aC $APPPUB/css/ $WEBAPP/css
rsync -aC $APPPUB/images/ $WEBAPP/images
