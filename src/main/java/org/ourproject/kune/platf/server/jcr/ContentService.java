package org.ourproject.kune.platf.server.jcr;

import com.google.inject.Inject;

public class ContentService {
    // public static final String REPOSITORY_NAME = "kune.repository.name";
    // public static final String REPOSITORY_CONFIG = "kune.repository.config";
    // private static final String REPOSITORY_HOME = "kune.repository.home";

    @Inject
    public JcrConfiguration configuration;

    public void start() {
	String name = configuration.getRepositoryName();
	String configFilePath = configuration.getConfigFile();
	String homeDir = configuration.getHomeDir();
	RepositoryHolder.openRepository(name, configFilePath, homeDir);
    }

    public void stop() {
	RepositoryHolder.closeRepository();
    }
}
