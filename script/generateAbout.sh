# Determine how to set the tempfile
if [ -n "`which tempfile`" ]; then
  tmp=`tempfile`
else
  tmp=/tmp/kune.$$
fi
trap "rm -rf $tmp" 1 2 3 9 15


REV=`svn info --xml| grep -m 1 revi | cut -d\" -f 2`
COMMITSPENDING=`svn status | grep -c ""`
SVNVER=r$REV+c$COMMITSPENDING
VER=`grep "<version>" pom.xml | head -1 | sed 's/..version.//g'`


echo Kune >> $tmp
echo "--------------------------------------------------------------------------------" >> $tmp
echo Version $VER '('$SVNVER')' >> $tmp

txt2html -h 80 --preserve_indent -8 --style_url /ws/css/richtext.css --infile $tmp --infile CREDITS --infile COPYRIGHT --outfile war/ws/about.html
rm $tmp
