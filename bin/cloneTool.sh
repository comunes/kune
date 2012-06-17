#!/bin/bash

usage() {
    echo "Use: $0 -o oldtoolname -n newtoolname"
    echo "$0 -o docs -O Documents -n blogs -N Blogs "
}

while getopts “ho:n:O:N:” OPTION
do
    case $OPTION in
	h)
            usage
            exit 1
            ;;
	o)
	    OLDS=$OPTARG
	    ;;
	n)
	    NEWS=$OPTARG
	    ;;
	O)
	    OLDCS=$OPTARG
	    ;;
	N)
	    NEWCS=$OPTARG
	    ;;
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $OLDS ]]  || [[ -z $NEWS ]] || [[ -z $OLDCS ]] || [[ -z $NEWCS ]] 
then
    usage
    exit 1
fi

OLD=$(echo ${OLDS%\s})
NEW=$(echo ${NEWS%\s})
OLDC=$(echo ${OLDCS%\s})
NEWC=$(echo ${NEWCS%\s})
OLD1S=`echo "${OLDS:0:1}" | tr a-z A-Z`${OLDS:1}
NEW1S=`echo "${NEWS:0:1}" | tr a-z A-Z`${NEWS:1}
OLD1=$(echo ${OLD1S%\s})
NEW1=$(echo ${NEW1S%\s})
OLDNCS=`echo "${OLDCS:0:1}" | tr A-Z a-z`${OLDCS:1}
NEWNCS=`echo "${NEWCS:0:1}" | tr A-Z a-z`${NEWCS:1}
OLDCC=`echo $OLDC | tr a-z A-Z`
NEWCC=`echo $NEWC | tr a-z A-Z`

echo We\'ll clone and rename:
echo "           " $OLDCS to $NEWCS
echo "          &" $OLDNCS to $NEWNCS
echo "          &" $OLDC to $NEWC
echo "          &" $OLD1S to $NEW1S
echo "          &" $OLDS to $NEWS
echo "          &" $OLD1 to $NEW1
echo "          &" $OLD to $NEW
echo "          &" $OLDCC to $NEWCC

read -p "Are you sure? (Ctrl-C to cancel) "

cd src/main/java/cc/kune/
rsync -r -C $OLDS/ $NEWS
cd $NEWS/

find . -name '*.java' | xargs rename 's/$OLDCS/$NEWCS/g'
find . -name '*.java' | xargs rename 's/$OLDC/$NEWC/g'
find . -name '*.java' | xargs rename 's/$OLD1S/$NEW1S/g'
find . -name '*.java' | xargs rename 's/$OLDS/$NEWS/g'
find . -name '*.java' | xargs rename 's/$OLD1/$NEW1/g'
find . -name '*.java' | xargs rename 's/$OLD/$NEW/g'
find . -name '*.xml'  | xargs rename 's/$OLD1S/$NEW1S/g'
find . -name '*.xml'  | xargs rename 's/$OLDS/$NEWS/g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLDCS/$NEWCS/g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLDC/$NEWC/g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLD1S/$NEW1S/g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLDS/$NEWS/g'
find . -name '*.java' | xargs perl -p -i -e 's/\.$OLDS\./\.$NEWS\./g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLD1/$NEW1/g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLDNCS/$NEWNCS/g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLD/$NEW/g'
find . -name '*.java' | xargs perl -p -i -e 's/$OLDCC/$NEWCC/g'

