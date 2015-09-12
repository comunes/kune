# kune-cli: a Kune Command Line Interface (CLI)
(Under development)

## Development tips
Import the folder kune/core/kune-cli as an Eclipse project, and solve dependency problems in Eclipse with
`mvn eclipse:eclipse`.

Run a Kune server (via a debian package or using our docker image or a kune dev instance launched from Eclipse) and afterwards run the `KuneCliMain` or its tests `KuneCliMainTest`.

## Usage

During developmen, you can running kune-cli from a terminal, with arguments like:

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
java -cp /usr/lib/jvm/java-7-oracle/lib/tools.jar:./target/kune-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar cc.kune.kunecli.KuneCliMain help
```
Update the tools.jar location pointing to your JDC_HOME directory.

or directly using the bash script:

```bash
bin/kune-cli help
```

## Environment Parameters

By default the kune-cli tries to connecto to a running instance of kune in http://127.0.0.1:8888, but you can provide via environmental variable, other url, like:

```bash
export KUNE_SERVER_URL="http://127.0.0.1:80"
```
