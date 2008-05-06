#!/bin/bash

#script/css-compact-and-tidy.css
script/css-update.sh

rm src/main/webapp/gwt/org.ourproject.kune.app.Kune/ -R
cp target/org.ourproject.kune-0.0.3/org.ourproject.kune.app.Kune/ src/main/webapp/gwt/ -R
