#!/bin/bash
APPPUB=src/main/java/org/ourproject/kune/app/public
WEBAPP=src/main/webapp/gwt/org.ourproject.kune.app.Kune
TARGET=target/org.ourproject.kune-0.0.4/org.ourproject.kune.app.Kune

$PWD/script/css-compact-and-tidy.css

rsync -aC $APPPUB/Kune.html $WEBAPP/Kune.html
rsync -aC $APPPUB/js/ $WEBAPP/js
rsync -aC $APPPUB/css/ $WEBAPP/css
rsync -aC $APPPUB/images/ $WEBAPP/images

rsync -aC $APPPUB/Kune.html $TARGET/Kune.html
rsync -aC $APPPUB/js/ $TARGET/js
rsync -aC $APPPUB/css/ $TARGET/css
rsync -aC $APPPUB/images/ $TARGET/images
