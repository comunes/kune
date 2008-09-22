#!/bin/bash

#scripts/css-compact-and-tidy.css


rm src/main/webapp/gwt/org.ourproject.kune.app.Kune/ -R
mkdir src/main/webapp/gwt/org.ourproject.kune.app.Kune/
cp target/org.ourproject.kune-0.0.1/org.ourproject.kune.app.Kune/std/ src/main/webapp/gwt/org.ourproject.kune.app.Kune/ -R
