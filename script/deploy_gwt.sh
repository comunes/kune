#!/bin/bash

mkdir -p src/main/webapp/gwt/org.ourproject.kune.app.Kune

find target/kune-0.0.4/org.ourproject.kune.app.Kune/ -name .DS_Store -exec rm {} \;

script/css-compact-and-tidy.css
script/css-update.sh

cp target/kune-0.0.4/org.ourproject.kune.app.Kune/js/ext/ext-all-debug.js target/kune-0.0.4/org.ourproject.kune.app.Kune/js/ext/ext-all.js 
unison -silent -batch -auto -ui text target/kune-0.0.4/org.ourproject.kune.app.Kune src/main/webapp/gwt/org.ourproject.kune.app.Kune

