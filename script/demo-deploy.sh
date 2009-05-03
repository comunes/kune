#!/bin/bash

# from time to time use --delete
rsync --delete --progress -C -r -p target/kune-0.0.5/org.ourproject.kune.app.Kune/ ourproject@kraft.faita.net:/home/ourproject/kunedemo/src/main/webapp/gwt/org.ourproject.kune.app.Kune/
