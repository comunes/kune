# Kune Command Line Interface (CLI), under development

## Dev CLI
Import the folder kune/core/kune-cli as an Eclipse project, and solve dependency problems in Eclipse with
`mvn eclipse:eclipse`.

Run the Kune server and afterwards run the KuneCliMain or its tests KuneCliMainTest

## For running it from terminal, with arguments like:

```bash
mvn exec:java -Dexec.args="help"
```

or

```bash
mvn exec:java -Dexec.args="hello world John"
```
also you can package and use the jar:

```bash
mvn package
mvn assembly:single
java -jar target/kune-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar help
```
