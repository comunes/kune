. script/mvn-vars.sh
java -jar $M2_REPO/org/liquibase/liquibase-core/1.7.0/liquibase-core-1.7.0.jar \
      --driver=com.mysql.jdbc.Driver \
      --classpath=$M2_REPO/mysql/mysql-connector-java/5.0.5/mysql-connector-java-5.0.5.jar \
      --changeLogFile=src/main/resources/db/liquibase_changelog.xml \
      --url="jdbc:mysql://localhost/kune_dev" \
      --username=kune \
      --password=db4kune \
      migrate

#java -jar $M2_REPO/org/liquibase/liquibase-core/1.4.1/liquibase-core-1.4.1.jar \
#  --defaultsFile=src/main/resources/db/liquibase.properties \
#  migrate
