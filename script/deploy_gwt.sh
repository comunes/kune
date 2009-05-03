#!/bin/bash

mkdir -p src/main/webapp/gwt/org.ourproject.kune.app.Kune

find target/kune-0.0.5/org.ourproject.kune.app.Kune/ -name .DS_Store -exec rm {} \;

script/css-compact-and-tidy.sh
script/css-update.sh

cp target/kune-0.0.5/org.ourproject.kune.app.Kune/js/ext/ext-all-debug.js target/kune-0.0.5/org.ourproject.kune.app.Kune/js/ext/ext-all.js 
rsync --delete -C -r -p target/kune-0.0.5/org.ourproject.kune.app.Kune/ src/main/webapp/gwt/org.ourproject.kune.app.Kune/

