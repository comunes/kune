export M2_REPO=/home/vjrj/.m2/repository/
java -classpath \
$M2_REPO/com/javaforge/scriptella/scriptella-core/0.9/scriptella-core-0.9.jar:\
$M2_REPO/com/javaforge/scriptella/scriptella-drivers/0.9/scriptella-drivers-0.9.jar:\
$M2_REPO/com/javaforge/scriptella/scriptella-tools/0.9/scriptella-tools-0.9.jar:\
$M2_REPO/mysql/mysql-connector-java/5.0.5/mysql-connector-java-5.0.5.jar:\
$M2_REPO/au/com/bytecode/opencsv/opencsv/1.8/opencsv-1.8.jar:\
$M2_REPO/commons-logging/commons-logging/1.0.3/commons-logging-1.0.3.jar:\
$M2_REPO/commons-jexl/commons-jexl/1.0/commons-jexl-1.0.jar\
 scriptella.tools.launcher.EtlLauncher "$@"
