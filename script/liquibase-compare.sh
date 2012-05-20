. script/mvn-vars.sh
java -jar $M2_REPO/org/liquibase/liquibase-core/1.8.1/liquibase-core-1.8.1.jar \
      --driver=com.mysql.jdbc.Driver \
      --classpath=$M2_REPO/mysql/mysql-connector-java/5.0.5/mysql-connector-java-5.0.5.jar \
      --url=jdbc:mysql://localhost/kune_dev \
      --username=kune \
      --password=db4kune \
      diffChangeLog \
      --baseUrl=jdbc:mysql://localhost/kune_prod \
      --baseUsername=kune \
      --basePassword=db4kune
# later version referenceUrl, etc
# diff instead of diffChangeLog