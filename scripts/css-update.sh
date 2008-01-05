#!/bin/bash
APPPUB=src/main/java/org/ourproject/kune/app/public
WEBAPP=src/main/webapp/gwt/org.ourproject.kune.app.Kune
TARGET=target/org.ourproject.kune-0.0.1/org.ourproject.kune.app.Kune

$PWD/scripts/css-compact-and-tidy.css

rsync -aC $TR/$APPPUB/Kune.html $TR/$WEBAPP/Kune.html
rsync -aC $TR/$APPPUB/js/ $TR/$WEBAPP/js
rsync -aC $TR/$APPPUB/css/ $TR/$WEBAPP/css
rsync -aC $TR/$APPPUB/images/ $TR/$WEBAPP/images

rsync -aC $TR/$APPPUB/Kune.html $TR/$TARGET/Kune.html
rsync -aC $TR/$APPPUB/js/ $TR/$TARGET/js
rsync -aC $TR/$APPPUB/css/ $TR/$TARGET/css
rsync -aC $TR/$APPPUB/images/ $TR/$TARGET/images
