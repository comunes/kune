package org.ourproject.kune.platf.server.jcr;

import org.ourproject.kune.platf.server.properties.KuneProperties;

import com.google.inject.Inject;

public class ContentService {
    public static final String REPOSITORY_NAME = "kune.repository.name";
    public static final String REPOSITORY_CONFIG = "kune.repository.config";
    private static final String REPOSITORY_HOME = "kune.repository.home";

    private KuneProperties properties;

    public void start() {
	String name = properties.get(REPOSITORY_NAME);
	String configFilePath = properties.get(REPOSITORY_CONFIG);
	String homeDir = properties.get(REPOSITORY_HOME);
	RepositoryHolder.openRepository(name, configFilePath, homeDir);
    }

    public void stop() {
	RepositoryHolder.closeRepository();
    }

    @Inject
    public void setProperties(final KuneProperties properties) {
	this.properties = properties;
    }

}
