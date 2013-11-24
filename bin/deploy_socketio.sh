DIR=~/dev/Socket.IO-Java/extension/gwt/target/
FILE1=$DIR/socketio-gwt-0.1-SNAPSHOT.jar
FILE2=$DIR/socketio-gwt-0.1-SNAPSHOT-sources.jar

mvn deploy:deploy-file \
-Dfile=$FILE1 \
-DgroupId=com.glines.socketio.java -DartifactId=socketio-gwt \
-Dversion=24-11-2013 -Dpackaging=jar \
-Durl=http://archiva.comunes.org/repository/comunes-internal/ \
-DrepositoryId=comunes-internal
mvn deploy:deploy-file -DgroupId=com.glines.socketio.java \
-Dfile=$FILE2 \
-DartifactId=socketio-gwt-sources \
-Dversion=24-11-2013 -Dpackaging=jar \
-Durl=http://archiva.comunes.org/repository/comunes-internal/ \
-DrepositoryId=comunes-internal
