echo "Under development"
exit
KUNE_DB=$1
KUNE_DB_USER=$2
KUNE_DB_PASS=$3
OPENFIRE_DB=$4
OPENFIRE_DB_USER=$5
OPENFIRE_DB_PASS=$6
DOMAIN=$7
cat << EOF | awk -F "|" '{print "UPDATE ofProperty SET propValue=\""$2"\" WHERE name=\""$1"\";" }'
com.mysql.jdbc.Driver|jdbc:mysql://localhost/$OPENFIRE_DB?user=$OPENFIRE_DB_USER&password=$OPENFIRE_DB_PASS&useUnicode=true&characterEncoding=utf-8
jdbcProvider.driver|com.mysql.jdbc.Driver
jdbcProvider.connectionString|jdbc:mysql://localhost/$KUNE_DB?user=$KUNE_DB_USER&password=$KUNE_DB_PASS&useUnicode=true&characterEncoding=utf-8

provider.auth.className|org.jivesoftware.openfire.auth.JDBCAuthProvider
provider.user.className|org.jivesoftware.openfire.user.JDBCUserProvider
jdbcAuthProvider.passwordSQL|SELECT password FROM $KUNE_DB.kusers WHERE shortName=?
jdbcAuthProvider.passwordType|plain
jdbcUserProvider.allUsersSQL|SELECT shortName FROM $KUNE_DB.kusers
jdbcUserProvider.emailField|email
jdbcUserProvider.loadUserSQL|SELECT shortName,email FROM $KUNE_DB.kusers WHERE shortName=?
jdbcUserProvider.nameField|name
jdbcUserProvider.searchSQL|SELECT shortName FROM $KUNE_DB.kusers WHERE
jdbcUserProvider.userCountSQL|SELECT COUNT(*) FROM $KUNE_DB.kusers
jdbcUserProvider.usernameField|shortName
admin.authorizedJIDs|admin@$DOMAIN
EOF
exit

