#!/bin/bash

#scripts/css-compact-and-tidy.css
scripts/css-update.sh

rm src/main/webapp/gwt/org.ourproject.kune.app.Kune/ -R
cp target/org.ourproject.kune-0.0.1/org.ourproject.kune.app.Kune/ src/main/webapp/gwt/ -R
