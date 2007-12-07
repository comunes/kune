#!/bin/bash
for i in `find src/main/java/org/ourproject -name '*.java'`; do grep -q "Copyright" $i ; if [[ $? -ne 0 ]]; then echo $i ; fi; done
#for i in `cat /tmp/files` ;do cat COPYRIGHT-GPLv3.java > /tmp/copy ; cat $i >> /tmp/copy; mv /tmp/copy $i ;done
#for i in `./findFilesWithoutCopyright.sh` ;do cp COPYRIGHT-GPLv3.java /tmp/copy; cat $i >> /tmp/copy ; mv /tmp/copy $i ; done
