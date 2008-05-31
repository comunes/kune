#!/bin/bash
find target/org.ourproject.kune-0.0.4/org.ourproject.kune.app.Kune/ -name .DS_Store -exec rm {} \;

#script/css-compact-and-tidy.css
script/css-update.sh

unison -silent -batch -auto -ui text target/org.ourproject.kune-0.0.4/org.ourproject.kune.app.Kune src/main/webapp/gwt/org.ourproject.kune.app.Kune
