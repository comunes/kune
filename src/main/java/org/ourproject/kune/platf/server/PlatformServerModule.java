/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.server.access.Accessor;
import org.ourproject.kune.platf.server.access.AccessorDefault;
import org.ourproject.kune.platf.server.access.Finder;
import org.ourproject.kune.platf.server.access.FinderDefault;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentManagerDefault;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContainerManagerDefault;
import org.ourproject.kune.platf.server.content.CreationService;
import org.ourproject.kune.platf.server.content.CreationServiceDefault;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.manager.impl.GroupManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.LicenseManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.SocialNetworkManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.ToolConfigurationManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.UserManagerDefault;
import org.ourproject.kune.platf.server.mapper.DozerMapper;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.properties.KunePropertiesDefault;
import org.ourproject.kune.platf.server.services.ContentServerService;
import org.ourproject.kune.platf.server.services.SiteBarServerService;
import org.ourproject.kune.platf.server.state.StateServiceDefault;
import org.ourproject.kune.platf.server.state.StateService;
import org.ourproject.kune.platf.server.tool.ToolRegistry;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.inject.AbstractModule;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;

public class PlatformServerModule extends AbstractModule {
    @Override
    protected void configure() {
	install(PersistenceService.usingJpa().across(UnitOfWork.TRANSACTION).buildModule());
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
	bind(CreationService.class).to(CreationServiceDefault.class);
	bind(Accessor.class).to(AccessorDefault.class);
	bind(UserManager.class).to(UserManagerDefault.class);
	bind(GroupManager.class).to(GroupManagerDefault.class);
	bind(ContentManager.class).to(ContentManagerDefault.class);
	bind(ToolConfigurationManager.class).to(ToolConfigurationManagerDefault.class);
	bind(Finder.class).to(FinderDefault.class);
	bind(StateService.class).to(StateServiceDefault.class);
	bind(ContainerManager.class).to(ContainerManagerDefault.class);
	bind(LicenseManager.class).to(LicenseManagerDefault.class);
	bind(SocialNetworkManager.class).to(SocialNetworkManagerDefault.class);
    }
}
