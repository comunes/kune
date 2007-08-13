package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.manager.ContentDescriptorManager;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManagerDefault;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.GroupManagerDefault;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManagerDefault;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.manager.UserManagerDefault;
import org.ourproject.kune.platf.server.mapper.DozerMapper;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.properties.KunePropertiesDefault;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.wideplay.warp.persist.PersistenceService;

public class KunePlatformModule extends AbstractModule {
    @Override
    protected void configure() {
	bindManagers();
	install(PersistenceService.usingJpa().buildModule());
	bind(KuneProperties.class).to(KunePropertiesDefault.class).in(Scopes.SINGLETON);
	bind(Mapper.class).to(DozerMapper.class).in(Scopes.SINGLETON);
	bind(ToolRegistry.class).in(Scopes.SINGLETON);
    }

    private void bindManagers() {
	bind(UserManager.class).to(UserManagerDefault.class).in(Scopes.SINGLETON);
	bind(GroupManager.class).to(GroupManagerDefault.class).in(Scopes.SINGLETON);
	bind(ContentDescriptorManager.class).to(ContentDescriptorManagerDefault.class).in(Scopes.SINGLETON);
	bind(ToolConfigurationManager.class).to(ToolConfigurationManagerDefault.class).in(Scopes.SINGLETON);
    }
}
