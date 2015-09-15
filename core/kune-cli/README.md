# kune-cli: a Kune Command Line Interface (CLI)
(Under development)

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

invite <youruser:string> <yourpass:string> <someemail@example.com:string> ...
        invite some emails to use this kune site

groups count
        Count all registered groups

groups reindex
        Reindex all groups in Lucene

users count
        Count all registered users

users reindex
        Reindex all users in Lucene

reload properties
        Reload the kune.properties without restarting kune

```

for instance:

```bash
$ bin/kune-cli users count
sep 15, 2015 10:08:28 PM cc.kune.kunecli.JMXUtils doOperation
INFORMACIÓN: Doing operation 'count' over mbean: 'cc.kune.mbeans:type=UserManagerDefault' with id: '11695'.
Users registered: 6480
```

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
