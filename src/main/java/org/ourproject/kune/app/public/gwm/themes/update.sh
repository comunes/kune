#!/bin/bash
#!/bin/bash

PARAMS=$#
DIR=$1

# PARAMETROS CORRECTOS #########################################################

if [ $PARAMS -ne 1 ]
then
  echo "Use: $0 <gwm-new-themes-dir>"
  echo "$0 /tmp/gwm/0.6.6/themes/"
  exit
fi

for i in `ls -d * | egrep -v "update|custom"`; do svn delete $i ; done
cp -a $DIR/* .
for i in `ls -d * | grep -v "update|custom"`; do svn add $i ; done 
