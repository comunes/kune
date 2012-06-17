# mkdir temp temp/etc tmp/bin 
target/../ws/ bin/server.sh
wget 'http://ourproject.org/plugins/scmsvn/viewcvs.php/*checkout*/trunk/src/main/resources/jaas.config?root=kune' -O jaas.config
wget 'http://ourproject.org/plugins/scmsvn/viewcvs.php/*checkout*/trunk/src/main/resources/kune.properties?root=kune' -O kune.properties
wget 'http://ourproject.org/plugins/scmsvn/viewcvs.php/*checkout*/trunk/src/main/resources/wave-server.properties?root=kune' -O wave-server.properties
tar 
