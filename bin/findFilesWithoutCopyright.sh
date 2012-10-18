#!/bin/bash
for i in `find src/main/java/cc/ src/test -name '*.java'`; do egrep -q "Copyright|The kune development team" $i ; if [[ $? -ne 0 ]]; then echo $i ; fi; done
