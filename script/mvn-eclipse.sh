mvn eclipse:eclipse
perl -p -i -e 's/<\/classpath>//g' .classpath
echo -en "  <classpathentry kind=\"con\" path=\"com.google.gwt.eclipse.core.GWT_CONTAINER\"/>\n</classpath>" >> .classpath
