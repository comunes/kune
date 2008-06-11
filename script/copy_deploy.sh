#!/bin/bash

#script/css-compact-and-tidy.css
script/css-update.sh

rm src/main/webapp/gwt/org.ourproject.kune.app.Kune/ -R
cp target/kune-0.0.4/org.ourproject.kune.app.Kune/ src/main/webapp/gwt/ -R
