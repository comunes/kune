<div id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#sec-1">1. TROUBLESHOOTING</a>
<ul>
<li><a href="#sec-1-1">1.1. Starting Kune server hangs in "INFO: Starting server"</a></li>
<li><a href="#sec-1-2">1.2. Too many files open</a></li>
<li><a href="#sec-1-3">1.3. Error generating CoreResources</a></li>
<li><a href="#sec-1-4">1.4. If you change some .properties or configuration files in src directory</a></li>
<li><a href="#sec-1-5">1.5. Guice-related errors</a></li>
<li><a href="#sec-1-6">1.6. Mysql</a>
<ul>
<li><a href="#sec-1-6-1">1.6.1. Mysql: Caused by: java.sql.SQLException: Access denied for user 'kune'@'localhost' (using password: YES)</a></li>
<li><a href="#sec-1-6-2">1.6.2. Other mysql errors</a></li>
<li><a href="#sec-1-6-3">1.6.3. Liquibase Migration failed</a></li>
</ul>
</li>
<li><a href="#sec-1-7">1.7. If during initialization (first start) you get a 'Account already exists'</a></li>
<li><a href="#sec-1-8">1.8. Liquibase</a>
<ul>
<li><a href="#sec-1-8-1">1.8.1. Checksum errors</a></li>
</ul>
</li>
<li><a href="#sec-1-9">1.9. Lucene</a>
<ul>
<li><a href="#sec-1-9-1">1.9.1. Kune indexes problems during upgrades</a></li>
<li><a href="#sec-1-9-2">1.9.2. Wave indexes problems and waves corruption problems</a></li>
</ul>
</li>
<li><a href="#sec-1-10">1.10. Guice error while running server</a></li>
<li><a href="#sec-1-11">1.11. JUnit</a></li>
<li><a href="#sec-1-12">1.12. If the client get: Error fetching initial data from Kune server</a></li>
<li><a href="#sec-1-13">1.13. Eclipse &amp; GWT</a></li>
<li><a href="#sec-1-14">1.14. Outbound variable M2<sub>REPO</a></li>
<li><a href="#sec-1-15">1.15. Too many GWT permutations?</a></li>
<li><a href="#sec-1-16">1.16. Eclipse startup is slow</a></li>
<li><a href="#sec-1-17">1.17. Error: ChatException: remote-server-not-found(404) trying to create a room.</a></li>
<li><a href="#sec-1-18">1.18. Error: Incorrect string value: '\xEF\xBF\xBD\xEF\xBF\xBD&#x2026;' for column 'native<sub>name' at row 1</a></li>
<li><a href="#sec-1-19">1.19. Error during build: Validation</a></li>
<li><a href="#sec-1-20">1.20. Error during build: Maven</a></li>
<li><a href="#sec-1-21">1.21. Java compile errors: incompatible types</a></li>
<li><a href="#sec-1-22">1.22. Emite troubleshooting</a></li>
<li><a href="#sec-1-23">1.23. Debugging</a>
<ul>
<li><a href="#sec-1-23-1">1.23.1. Server debug</a></li>
<li><a href="#sec-1-23-2">1.23.2. Client debug</a></li>
<li><a href="#sec-1-23-3">1.23.3. Firebug and client log levels</a></li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>

# TROUBLESHOOTING

## Starting Kune server hangs in "INFO: Starting server"

This probably happens because a directory of 'resource_bases' in wave-server.properties doesn't exist. Please create or configure this variable correctly, or make sure the missing directory exists.

## Too many files open

If running all the Kune test you get errors about "Too many files open" see:
<http://code.google.com/p/gwt-examples/wiki/gwtEclipseFaqs>
about how to fix it in your system.

## Error generating CoreResources
```
[INFO]       Computing all possible rebind results for 'cc.kune.core.client.resources.CoreResources' [INFO]          Rebinding cc.kune.core.client.resources.CoreResources [INFO]             Invoking generator com.google.gwt.resources.rebind.context.StaticClientBundleGenerator [INFO]                [ERROR] Generator 'com.google.gwt.resources.rebind.context.StaticClientBundleGenerator' threw an exception while rebinding 'cc.kune.cor
 [INFO]  at com.google.gwt.dev.util.Util.computeStrongName(Util.java:170) [INFO]  at com.google.gwt.dev.util.Util.computeStrongName(Util.java:145) [INFO]  at com.google.gwt.resources.rebind.context.StaticResourceContext.deploy(StaticResourceContext.java:61)
```

<http://code.google.com/p/google-web-toolkit/issues/detail?id=6103>

The gwt compiler is trying to create a sprite sheet, an image with all the icons all together (to minimize calls from client to server) but cannot open all the files. This happens normally when ulimit is not setted. See: "Too many files open" above.

## If you change some .properties or configuration files in src directory

Be sure to do mvn compile (that copy them to the target directory)

## Guice-related errors

See Mysql => Other mysql errors

## Mysql

### Mysql: Caused by: java.sql.SQLException: Access denied for user 'kune'@'localhost' (using password: YES)

Check that the password you have use in the database creation it's the same like the kune.properties. If it's wrong maybe you have to remove the mysql user (DROP USER) and repeat the GRANT sentences of the INSTALL, for instance:
```
DROP USER kune@localhost;
GRANT ALL PRIVILEGES ON kune_prod.* TO kune@localhost IDENTIFIED BY 'db4kune';
GRANT ALL PRIVILEGES ON kune_openfire.* TO kune@localhost IDENTIFIED BY 'db4kune';
FLUSH PRIVILEGES;
```

Also, be sure you do a ```mvn compile``` if you are running kune from the source and you change the kune.properties file to not use the old one.

If you have this problem with openfire it's not enough to change the password in openfire.xml because probably it's already stored in the ofProperty openfire table and you need to change the password there.

### Other mysql errors

If you get this warning, probably you have connection problems between kune and your database:

WARNING: Multiple Servlet injectors detected. This is a warning indicating that you have more than one GuiceFilter running in your web application. If this is deliberate, you may safely ignore this message. If this is NOT deliberate however, your application may not work as expected.

A check list:

-   These files should be the same:
```
      diff src/main/resources/kune.properties target/kune-0.2.0-SNAPSHOT/WEB-INF/classes/kune.properties
```
    and:
```
      diff src/main/resources/META-INF/persistence.xml target/kune-0.2.0-SNAPSHOT/WEB-INF/classes/META-INF/persistence.xml
```
    otherwise, run ```mvn compile -Dliquibase.should.run=false```

-   Also check the persistence unit that you are using and find that name in persistence.xml (we are using development, but can be other). The db user/password configured in kune.properties for that name should be the same to that one you used in the GRANT sentence of mysql database creation.

To debug mysql logs, you can uncomment:
```
#general_log_file        = /var/log/mysql/mysql.log
#general_log             = 1
```
in ```/etc/mysql/my.cnf```

### Liquibase Migration failed

After executing
```
  bin/liquibase-migrate.sh
  ```
An error such as this one may appear:
```
  Migration Failed: Error executing SQL ALTER TABLE groups ADD COLUMN logoLastModifiedTime BIGINT NOT NULL DEFAULT 1347400051999
```

This is due to the auto-generation of columns by the Kune environment. In this specific case, dropping the column would allow the migration to succeed. That is, it can be solved connecting to the kune_dev (or kune_prod) table and executing this SQL sentence:
```
  ALTER TABLE groups DROP COLUMN logoLastModifiedTime;
```

## If during initialization (first start) you get a 'Account already exists'

If you stopped the first initialization and the server init is half started you will get something like:
```
Caused by: cc.kune.core.client.errors.UserRegistrationException:
Account already exists
```

trying to run kune and creating the DB. So better remove the first account of wave and related /var/lib/kune/_*

## Liquibase

### Checksum errors
```
Migration Failed: Validation Failed:1 change sets failed MD5Sum Check     src/main/resources/db/liquibase_changelog.xml :: 18 :: vjrj :: (MD5Sum: bde0aa519108e1e3d1f29bb2483bc9)
```

Don't try to update the liquibase changelogs you have already use. Better create new ones to delete, update, and so on. If you are developing and want to clear the checksums, you can use the command "clearCheckSums". See bin/liquibase-* and <http://www.liquibase.org/manual/command_line>

## Lucene

### Kune indexes problems during upgrades

If you get errors like:
```
org.ourproject.kune.platf.server.ServerException: Error starting persistence service
(&#x2026;)
Caused by: org.hibernate.search.SearchException: Unable to open IndexWriter for class org.ourproject.kune.platf.server.domain.User
(&#x2026;)
Caused by: org.apache.lucene.index.CorruptIndexException: Unknown format version: -7
```
The indexes in /var/lib/kune/lucene/kune*/indexes* are not compatible. For the moment the only workaround we now it's to delete the indexes.

Sometimes the tests also fails without any error (use the same workaround).

### Wave indexes problems and waves corruption problems

1.  Recreation

    Sometimes you have to recreate all the waves index stoping kune removing /var/lib/kune/_wave_indexes and starting again. This process is long depending on the number of waves, so take a rest util kune starts.

2.  Yes, but kune doesn't start after a while

    If the previous process takes too much time, look for the kune process and see with "lsof <pid>" if it's hanged in some specific wave (so, if a directory/files opened are always the same). Some times can happen if exists some corrupted wave that prevends kune to start. In this case, a dirty workaround is to see the wave directory is always open with lsof, to stop kune, move that directory (aka wave) in another location and try to start again whitout it.

3.  Wave index locked

    If with kune stopped you see some write.lock in ```/var/lib/kune/_wave_indexes``` you should delete it before start kune.

```
    total 8
    -rw-r&#x2013;r&#x2013;  1 root root    0 jul 26 14:15 write.lock
    drwxr-xr-x  2 root root 4096 jul 26 14:15 .
    drwxr-xr-x 13 kune kune 4096 jul 26 14:19 ..
    kunedemo:/usr/share/kune/custom# rm /var/lib/kune/_wave_indexes/write.lock
```

## Guice error while running server

When running the server (e.g. "kune server via mvn.launch"), and Guice throws a sequence of exceptions such as:

```
-   FAILED rack: com.google.inject.CreationException: Guice creation errors:

    1.  Error injecting constructor, javax.persistence.PersistenceException: [PersistenceUnit: development] Unable to build EntityManagerFactory

    Caused by: &#x2026;
    Caused by: &#x2026;
    Caused by: &#x2026;
```

There is a chain of "Caused by" exceptions. Check the last ones of the chain to understand what's going on. For instance, it may give a problem of permissions ("permission denied"), which can be easily fixed correcting the pointed file permissions. Another example is a problem with Hibernate and thus the database, as in: <http://www.kune.cc/?locale=en#!kune.lists.1226.8850> (the solution is described there)

## JUnit

Running all the Kune test from eclipse I get connection pool exceptions like:
```
   Connections could not be acquired from the underlying database
```
We have problems testing all test together from eclipse and using real db (not the h2 memory db)

## If the client get: Error fetching initial data from Kune server

Probably the client code is outdated from the server code (or viceversa). Try to get both codes in sync compiling it.

## Eclipse & GWT

If you have some tests with error like "can not be found in source packages. Check the inheritance chain from your module; it may not be inheriting a required module or a module may not be adding its source path entries properly" and you have installed GWTDesigner, uncheck "client classpath": <http://code.google.com/webtoolkit/tools/gwtdesigner/preferences/gwt/preferences_builder.html>

Error: Invalid version number "2.0" passed to external.gwtOnLoad(), expected "2.1"; your hosted mode bootstrap file may be out of date; if you are using -noserver try recompiling and redeploying your app
Fix: clear your browser cache

Error: method should override method xxx (or similar)
Fix: Project &#x2013;> Properties &#x2013;> Java Compiler &#x2014;> Uncheck enable project specific settings

Error: Running Web application seems that are running an old GWT code
Fix: Open Run > Run configurations and remove a recreate your launch configuration

Error: Problem with Eclipse plugin and UiBinder "Field xxx has no corresponding field in template file yyy.ui.xml"

<https://code.google.com/p/google-web-toolkit/issues/detail?id=4353>

## Outbound variable M2_REPO

Set M2_REPO to eclipse in Preferences > Java > Build Path > Classpath Variable (normally to /home/youruser*.m2/repository/)

## Too many GWT permutations?

Compile in PRETTY mode and edit target/kune-VERSION/ws/ws.nocache.js and look for 'unflattenKeylistIntoAnswers' to see a permutation list. See:
<https://groups.google.com/group/google-web-toolkit/browse_thread/thread/ec7737c291ce4572/142590b8985b1b20?lnk=gst&q=user-agent+permutations#142590b8985b1b20>

If you want to compile less permutations (only for dev or testing), comment some locale and user.agent in src/main/java/cc/kune/Kune.gwt.xml

## Eclipse startup is slow

Check, for instance, the first points of:
<http://www.beyondlinux.com/2011/06/25/speed-up-your-eclipse-as-a-super-fast-ide/>

## Error: ChatException: remote-server-not-found(404) trying to create a room.

error: Caused by: org.ourproject.kune.chat.server.managers.ChatException: remote-server-not-found(404) trying to create a room.

Check that the openfire server name is the same in the kune.properties file, and you can resolv the names :
```
$ host yourhostname
```
and
```
$ host rooms.yourhostname
```

## Error: Incorrect string value: '\xEF\xBF\xBD\xEF\xBF\xBD&#x2026;' for column 'native_name' at row 1

Verify that your system supports UTF8 (in debian systems check /etc/locale.gen and locale-gen)

## Error during build: Validation

While building, problems during validation such as:

```
    Errors occurred during the build.
    Errors running builder 'Faceted Project Validation Builder' on project 'kune'.
    Could not initialize class org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants
    Errors running builder 'Validation' on project 'kune'.
    org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants
```

or:

```
    An internal error occurred during: "Validating kune".
    org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants
```

These can be solved installing "Eclipse Java EE Developer Tools", as mentioned in:
<http://stackoverflow.com/questions/6936309/getting-an-error-message-while-building-phonegapsample-in-blackberry-webworks>

## Error during build: Maven

While building, problems related to Maven such as:

```
    Errors occurred during the build.
    Errors running builder 'Maven Project Builder' on project 'kune'.
    java.lang.NullPointerException
```

or:

```
    Plugin execution not covered by lifecycle configuration:
    org.apache.maven.plugins:maven-resources-plugin:2.4.2:resources (execution: default-resources, phase: process-resources)        pom.xml        /kune        line 773        Maven Project Build Lifecycle Mapping Problem
 ```

These can be solved by installing the m2eclipse, as described in:
<http://code.google.com/p/google-web-toolkit/wiki/WorkingWithMaven#Using_Maven_with_Google_Plugin_for_Eclipse>

## Java compile errors: incompatible types

(Obsolete)

Sometimes you get errors like this, trying to compile:
```
Authorizated.java:[50,47] incompatible types
found   : cc.kune.core.shared.domain.AccessRol
required: cc.kune.core.shared.domain.AccessRol
```
seems that happens with some openjdk6 versions. Workaround to compile from eclipse or using sun-jdk or another jdk version.
More info:
<https://bugs.launchpad.net/ubuntu/+source/openjdk-6/+bug/611284>
update-java-alternatives -l
try with:
update-java-alternatives -s java-6-sun

## Emite troubleshooting

See:
<http://code.google.com/p/emite/wiki/Troubleshooting>

## Debugging

### Server debug

You can debug the server running from eclipse as external tool:
```
   bin/kune server via mvn with debug.launch
```
and after this the debug launch:
```
   bin/kune server debug.launch
```
that connects the debugger to the port 8001 of the running server.

Related: debug with jetty in eclipse:
<http://docs.codehaus.org/display/JETTY/Debugging+with+the+Maven+Jetty+Plugin+inside+Eclipse>

Also for different logging levels you can play with -Dorg.eclipse.jetty.util.log.DEBUG=true (or similars)

See also log4j.properties in src/main/resources/ if you increase the level to DEBUG, you will get tons of logs.

### Client debug

You can debug the client with the eclipse launch configuration 'bin/kune client -no-server.launch'.

### Firebug and client log levels

To increase client logging add ?log_level=DEBUG# to the url and use firebug firefox extension to see the output.
