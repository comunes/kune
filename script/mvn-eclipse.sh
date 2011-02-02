mvn eclipse:eclipse -Dgwt.compiler.skip=true

perl -p -i -e 's/<\/classpath>//g' .classpath
perl -p -i -e 's/\/home\/vjrj\/nfsdev\///g' .classpath
echo -en "  <classpathentry kind=\"src\" path=\"target/generated-sources/apt\" including=\"**/*.java\"/>" >> .classpath
echo -en "  <classpathentry kind=\"con\" path=\"com.google.gwt.eclipse.core.GWT_CONTAINER\"/>\n</classpath>" >> .classpath

