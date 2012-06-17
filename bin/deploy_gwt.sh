#!/bin/bash

#mkdir -p src/main/webapp/gwt/ws

#find target/kune-0.0.5/org.ourproject.kune.app.Kune/ -name .DS_Store -exec rm {} \;

#bin/css-compact-and-tidy.sh
bin/css-update.sh
bin/generateAbout.sh

#cp target/kune-0.0.5/org.ourproject.kune.app.Kune/js/ext/ext-all-debug.js target/kune-0.0.5/org.ourproject.kune.app.Kune/js/ext/ext-all.js 
#rsync --delete -C -r -p war/ws/ src/main/webapp/gwt/ws/

