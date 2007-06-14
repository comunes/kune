#!/bin/bash
#cp -a target/org.ourproject.kune-0.0.1/org.ourproject.kune.Main/* src/main/webapp/gwt/org.ourproject.kune
unison -batch -auto -ui text target/org.ourproject.kune-0.0.1/org.ourproject.kune.Main src/main/webapp/gwt/org.ourproject.kune
