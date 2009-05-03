#!/bin/bash
ORIG=src/main/java/org/ourproject/kune/app/public/
CATTMPFILE=`mktemp -t css.1.XXX` || exit 1
CSSTMPFILE=`mktemp -t css.2.XXX` || exit 1
DEST=src/main/java/org/ourproject/kune/app/public/css/all.css
DEST2=src/main/java/org/ourproject/kune/app/public/css/richtext.css

EXTTMPFILE=$ORIG/js/ext/resources/css/ext-all.css-tmp

sed 's/x.btn button{border:0 none;background:transparent;font:normal 11px tahoma,verdana,helvetica;/x-btn button{border:0 none;background:transparent;/g' $ORIG/js/ext/resources/css/ext-all.css |\
sed 's/normal 11px/normal 100\%/g' |\
sed 's/bold 11px/bold 100\%/g' |\
sed 's/normal 12px/normal 100\%/g' |\
sed 's/bold 12px/bold 100\%/g' |\
sed 's/normal 10px/normal 100\%/g' |\
sed 's/normal 9px/normal 100\%/g' |\
sed 's/font-size:11px/font-size:100\%/g' |\
sed 's/font-size:12px/font-size:100\%/g' |\
sed 's/ arial,tahoma,helvetica,sans-serif/ arial,sans/g' |\
sed 's/ tahoma,arial,helvetica,sans-serif/ arial,sans/g' |\
sed 's/ tahoma,arial,verdana,sans-serif/ arial,sans/g' |\
sed 's/ tahoma,verdana,helvetica/ arial,sans/g' |\
sed 's/ tahoma,arial,helvetica/ arial,sans/g' |\
sed 's/ tahoma,arial,sans-serif/ arial,sans/g' |\
sed 's/ \"sans serif\",tahoma,verdana,helvetica/ arial,sans/g' |\
sed 's/ arial,helvetica,tahoma,sans-serif/ arial,sans/g' > $EXTTMPFILE

CSS='css/reset.css css/fonts-min.css js/ext/resources/css/ext-all.css-tmp js/ext/resources/css/xtheme-gray.css css/kune.css css/workspace.css css/chat.css css/docs.css css/kune-new.css'
CSS2='css/reset.css css/fonts-min.css css/base-min.css css/rte.css'

function concat {
for i in $*
do
  cat $ORIG/$i >> $CATTMPFILE
  echo -e "\n" >> $CATTMPFILE
done
}

function tidy {
which csstidy 2> /dev/null > /dev/null
if [[ $? -eq 0 ]]
then
  csstidy $CATTMPFILE \
    --template=highest \
    --optimise_shorthands=2 \
    --preserve_css=true \
    --silent=true \
    $CSSTMPFILE
else
  echo Warning: Dependency csstidy, in debian: apt-get install csstidy
  cp $CATTMPFILE $CSSTMPFILE
fi
cat $CSSTMPFILE | sed 's/\.\.\/images/\.\.\/js\/ext\/resources\/images/g' > $1
}

concat $CSS
tidy $DEST

echo > $CATTMPFILE

concat $CSS2
tidy $DEST2

rm $EXTTMPFILE
