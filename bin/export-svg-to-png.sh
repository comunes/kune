DEST=/tmp/
SIZE=16
for i in `ls img/icons/custom-iconic/* svg`
do 
  inkscape --export-png=$DEST/`basename $i .svg`.png \
           --export-width=$SIZE --export-height=$SIZE \
           $i
done