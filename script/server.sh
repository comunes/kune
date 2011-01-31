#!/bin/bash

mvn jetty:run -Dorg.mortbay.util.FileResource.checkAliases=False -Djava.security.auth.login.config=src/main/resources/jaas.config
 # -Dorg.eclipse.jetty.util.log.INFO=true
 # -Dorg.eclipse.jetty.util.log.DEBUG=true
