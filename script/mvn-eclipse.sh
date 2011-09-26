#mvn eclipse:eclipse -Dgwt.compiler.skip=true -o
mvn eclipse:eclipse -Dgwt.compiler.skip=true
RESULT=$?
perl -p -i -e 's/<\/classpath>//g' .classpath
#perl -p -i -e 's/\/home\/vjrj\/nfsdev\///g' .classpath
echo -en "  <classpathentry kind=\"src\" path=\".apt_generated\" including=\"**/*.java\"/>" >> .classpath
echo -en "  <classpathentry kind=\"con\" path=\"com.google.gwt.eclipse.core.GWT_CONTAINER\"/>\n</classpath>" >> .classpath
which kdialog > /dev/null 2>&1
if [[ $? == 0 ]]
then
  if [[ $RESULT == 0 ]]
  then
    kdialog --msgbox "mvn finished successfully" 2>/dev/null &
  else
    kdialog --error "mvn finished with errors"  2>/dev/null &
  fi
fi
