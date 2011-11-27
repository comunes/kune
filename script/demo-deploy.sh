#!/bin/bash

# from time to time use --delete
rsync --delete --progress -C -r -p target/kune-0.1.0-SNAPSHOT/ws/ vjrj@kune.beta.iepala.es:/home/vjrj/kune-current/target-last/kune-0.1.0-SNAPSHOT/ws/
