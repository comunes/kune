. bin/mvn-vars.sh
java -jar $M2_REPO/org/liquibase/liquibase-core/1.8.1/liquibase-core-1.8.1.jar \
      --driver=com.mysql.jdbc.Driver \
      --classpath=$M2_REPO/mysql/mysql-connector-java/5.1.13/mysql-connector-java-5.1.13.jar \
      --changeLogFile=src/main/resources/db/liquibase_changelog.xml \
      --url="jdbc:mysql://localhost/kune_dev" \
      --username=kune \
      --password=db4kune \
			rollbackCount 1
