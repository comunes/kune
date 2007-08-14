package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.server.manager.AccessRightsManager;
import org.ourproject.kune.platf.server.manager.AccessRightsManagerDefault;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManager;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManagerDefault;
import org.ourproject.kune.platf.server.manager.ContentManager;
import org.ourproject.kune.platf.server.manager.ContentManagerDefault;
import org.ourproject.kune.platf.server.manager.FolderManager;
import org.ourproject.kune.platf.server.manager.FolderManagerDefault;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.GroupManagerDefault;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.LicenseManagerDefault;
import org.ourproject.kune.platf.server.manager.MetadataManager;
import org.ourproject.kune.platf.server.manager.MetadataManagerDefault;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManagerDefault;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManagerDefault;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.manager.UserManagerDefault;
import org.ourproject.kune.platf.server.mapper.DozerMapper;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.properties.KunePropertiesDefault;
import org.ourproject.kune.platf.server.services.ContentServerService;
import org.ourproject.kune.platf.server.services.SiteBarServerService;
import org.ourproject.kune.platf.server.tool.ToolRegistry;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.inject.AbstractModule;
import com.wideplay.warp.persist.PersistenceService;

public class KunePlatformModule extends AbstractModule {
    @Override
    protected void configure() {
	install(PersistenceService.usingJpa().buildModule());
	bind(KunePersistenceService.class);

	bindManagers();
	bindRemoteServices();
	bind(KuneProperties.class).to(KunePropertiesDefault.class);
	bind(Mapper.class).to(DozerMapper.class);
	bind(ToolRegistry.class);

    }

    private void bindRemoteServices() {
	bind(KuneService.class).to(KuneServerService.class);
	bind(ContentService.class).to(ContentServerService.class);
	bind(SiteBarService.class).to(SiteBarServerService.class);
    }

    private void bindManagers() {
	bind(UserManager.class).to(UserManagerDefault.class);
	bind(GroupManager.class).to(GroupManagerDefault.class);
	bind(ContentDescriptorManager.class).to(ContentDescriptorManagerDefault.class);
	bind(ToolConfigurationManager.class).to(ToolConfigurationManagerDefault.class);
	bind(ContentManager.class).to(ContentManagerDefault.class);
	bind(AccessRightsManager.class).to(AccessRightsManagerDefault.class);
	bind(MetadataManager.class).to(MetadataManagerDefault.class);
	bind(FolderManager.class).to(FolderManagerDefault.class);
	bind(LicenseManager.class).to(LicenseManagerDefault.class);
	bind(SocialNetworkManager.class).to(SocialNetworkManagerDefault.class);
    }
}
