# kune-cli: a Kune Command Line Interface (CLI)

## Usage

Install the package kune-cli for debian (and derivatives) additionally to the kune package ([more details](http://kune.cc/#!kune.docs.6810.898)):

```bash
sudo apt-get install kune-cli
```
and type:

```bash
$ kune-cli

help
        Shows the commands help on plain text.

htmlhelp
        Shows the commands help on HTML format.

execute file <filename:string>
        Execute the commands on file.

auth <user:string> <pass:string>
        auth to kune

site invite <youruser:string> <yourpass:string> <someemail@example.com:string> ...
        invite some emails to use this kune site

site i18n stats [<kune.properties:string>]
        Gets a table with the status of translations. (if kune.properties is not defined we use /etc/kune/kune.properties for get the db parameters)

site reindex
        Reindex all entities in Lucene (experimental, can be slow)

site reload properties
        Reload the kune.properties without restarting kune

groups count
        Count all registered groups

groups reindex
        Reindex all groups in Lucene (experimental)

users count
        Count all registered users

users daily sign-ins stats [<kune.properties:string>]
        Gets stats of daily users sign-ins (if kune.properties is not defined we use /etc/kune/kune.properties for get the db parameters)

users last stats [<kune.properties:string>]
        Gets stats of last users sign-ins (if kune.properties is not defined we use /etc/kune/kune.properties for get the db parameters)

users lang stats [<kune.properties:string>]
        Gets stats of users languages (if kune.properties is not defined we use /etc/kune/kune.properties for get the db parameters)

users sign-in stats [<kune.properties:string>]
        Gets stats of users sign-ins (if kune.properties is not defined we use /etc/kune/kune.properties for get the db parameters)

users reindex
        Reindex all users in Lucene (experimental)

waveletToDir <waveletName:string>
        Converts a wavelet like 'example.com/w+cbghmi0fsmxjIS/example.com/user+test1@example.com' to his filesystem directory name.

```

for instance:

```bash
$ bin/kune-cli users count
(...)
Users registered: 6480
```

There are more commands under development.

## Environment Parameters

By default the kune-cli tries to connect to a running instance of kune in http://127.0.0.1:8888, but you can provide via environmental variable, other url like:

```bash
export KUNE_SERVER_URL="http://127.0.0.1:80"
```

## Development tips

Import the folder kune/core/kune-cli as an Eclipse project, and solve dependency problems in Eclipse with
`mvn eclipse:eclipse`.

Run a Kune server (via a debian package or using our docker image or a kune development instance launched from Eclipse) and afterwards run the `KuneCliMain` or its tests `KuneCliMainTest`.

During development, you can running kune-cli from a terminal, with arguments like:

```bash
mvn exec:java -Dexec.args="help"
```

or other similar commands:

```bash
mvn exec:java -Dexec.args="hello world John"
```

also you can package and use the jar:

```bash
mvn package
java -cp /usr/lib/jvm/java-7-oracle/lib/tools.jar:./target/kune-cli-1.0.1-SNAPSHOT-jar-with-dependencies.jar cc.kune.kunecli.KuneCliMain help
```
Update the tools.jar location pointing to your JVM_HOME directory.

Also you can directly use the bash script:

```bash
bin/kune-cli help
```
