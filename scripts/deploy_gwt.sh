#!/bin/bash
find target/org.ourproject.kune-0.0.1/org.ourproject.kune.app.Kune/ -name .DS_Store -exec rm {} \;

scripts/css-compact-and-tidy.css

unison -batch -auto -ui text target/org.ourproject.kune-0.0.1/org.ourproject.kune.app.Kune/ src/main/webapp/gwt/org.ourproject.kune.app.Kune
