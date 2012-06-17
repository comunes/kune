# Determine how to set the tempfile
if [ -n "`which tempfile`" ]; then
  tmp=`tempfile`
else
  tmp=/tmp/kune.$$
fi
trap "rm -rf $tmp" 1 2 3 9 15


VER=`grep "<version>" pom.xml | head -1 | sed 's/..version.//g'`
GITVER=`git rev-parse HEAD`


#echo "kune" >> $tmp
#echo "--------------------------------------------------------------------------------" >> $tmp
echo Version $VER '('$GITVER')' >> $tmp

txt2html --prepend_file bin/header.html -h 80 --preserve_indent -8 --style_url frame-def.css --infile $tmp --infile CREDITS --infile COPYRIGHT --outfile src/main/java/cc/kune/core/public/about.html
rm $tmp
