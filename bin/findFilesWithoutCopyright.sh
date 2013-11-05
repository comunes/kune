#!/bin/bash
for i in `find src/main/java/cc/ src/test -name '*.java'`; do egrep -q "Copyright|Licensed to the Comunes Association|The kune development team|Licensed to the Apache Software" $i ; if [[ $? -ne 0 ]]; then echo $i ; fi; done
