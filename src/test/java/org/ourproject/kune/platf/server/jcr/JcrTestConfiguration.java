package org.ourproject.kune.platf.server.jcr;

public class JcrTestConfiguration implements JcrConfiguration {
    public String getConfigFile() {
	return "config/kune.memory.repository.xml";
    }

    public String getHomeDir() {
	return "testRepository";
    }

    public String getRepositoryName() {
	return "testRepository";
    }

}
