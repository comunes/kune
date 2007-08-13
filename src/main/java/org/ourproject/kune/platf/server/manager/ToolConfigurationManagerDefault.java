package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.ToolConfiguration;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ToolConfigurationManagerDefault extends DefaultManager<ToolConfiguration, Long> implements
	ToolConfigurationManager {

    @Inject
    public ToolConfigurationManagerDefault(final Provider<EntityManager> provider) {
	super(provider, ToolConfiguration.class);
    }

}
