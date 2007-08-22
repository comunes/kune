#!/bin/bash
for i in `find src/main/java/org/ourproject -name '*.java'`; do grep -q "Copyright" $i ; if [[ $? -ne 0 ]]; then echo $i ; fi; done
