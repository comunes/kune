/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.server;

import static org.ourproject.kune.platf.server.OutermostCallInterceptor.outermostCall;

import org.ourproject.kune.app.server.AbstractExtendedModule;
import org.ourproject.kune.chat.server.managers.XmppManager;
import org.ourproject.kune.chat.server.managers.XmppManagerDefault;
import org.ourproject.kune.platf.server.access.AccessRightsService;
import org.ourproject.kune.platf.server.access.AccessRightsServiceDefault;
import org.ourproject.kune.platf.server.access.AccessService;
import org.ourproject.kune.platf.server.access.AccessServiceDefault;
import org.ourproject.kune.platf.server.access.FinderService;
import org.ourproject.kune.platf.server.access.FinderServiceDefault;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.AuthenticatedMethodInterceptor;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.auth.AuthorizatedMethodInterceptor;
import org.ourproject.kune.platf.server.auth.SessionService;
import org.ourproject.kune.platf.server.auth.SessionServiceDefault;
import org.ourproject.kune.platf.server.content.CommentManager;
import org.ourproject.kune.platf.server.content.CommentManagerDefault;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContainerManagerDefault;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentManagerDefault;
import org.ourproject.kune.platf.server.content.CreationService;
import org.ourproject.kune.platf.server.content.CreationServiceDefault;
import org.ourproject.kune.platf.server.i18n.I18nTranslationServiceDefault;
import org.ourproject.kune.platf.server.manager.ExtMediaDescripManager;
import org.ourproject.kune.platf.server.manager.FileManager;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.RateManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.manager.TagManager;
import org.ourproject.kune.platf.server.manager.TagUserContentManager;
import org.ourproject.kune.platf.server.manager.TagUserContentManagerDefault;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.manager.file.EntityLogoDownloadManager;
import org.ourproject.kune.platf.server.manager.file.EntityLogoUploadManager;
import org.ourproject.kune.platf.server.manager.file.FileDownloadManager;
import org.ourproject.kune.platf.server.manager.file.FileManagerDefault;
import org.ourproject.kune.platf.server.manager.file.FileUploadManager;
import org.ourproject.kune.platf.server.manager.impl.ExtMediaDescripManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.GroupManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.I18nCountryManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.I18nLanguageManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.I18nTranslationManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.LicenseManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.RateManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.SocialNetworkManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.TagManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.ToolConfigurationManagerDefault;
import org.ourproject.kune.platf.server.manager.impl.UserManagerDefault;
import org.ourproject.kune.platf.server.mapper.DozerMapper;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.properties.KunePropertiesDefault;
import org.ourproject.kune.platf.server.rpc.ContentRPC;
import org.ourproject.kune.platf.server.rpc.GroupRPC;
import org.ourproject.kune.platf.server.rpc.I18nRPC;
import org.ourproject.kune.platf.server.rpc.SiteRPC;
import org.ourproject.kune.platf.server.rpc.SocialNetworkRPC;
import org.ourproject.kune.platf.server.rpc.UserRPC;
import org.ourproject.kune.platf.server.state.StateService;
import org.ourproject.kune.platf.server.state.StateServiceDefault;
import org.ourproject.kune.platf.server.tool.ServerToolRegistry;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.platf.server.users.UserInfoServiceDefault;

import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.client.rpcservices.SocialNetworkService;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.Comment;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.ExtMediaDescrip;
import cc.kune.domain.Group;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;
import cc.kune.domain.License;
import cc.kune.domain.Rate;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.Tag;
import cc.kune.domain.TagUserContent;
import cc.kune.domain.User;

import com.google.inject.matcher.Matchers;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;

public class PlatformServerModule extends AbstractExtendedModule {
    private void bindFinders() {
        // http://google-guice.googlecode.com/svn/trunk/javadoc/com/google/inject/Injector.html
        // (...) Just-in-time bindings created for child injectors will be
        // created in an ancestor injector whenever possible (...)
        // (This fails with finders, then we make explicit bindings)

        bind(User.class);
        bind(Group.class);
        bind(Content.class);
        bind(Container.class);
        bind(License.class);
        bind(SocialNetwork.class);
        bind(Rate.class);
        bind(I18nCountry.class);
        bind(I18nLanguage.class);
        bind(I18nTranslation.class);
        bind(Tag.class);
        bind(TagUserContent.class);
        bind(Comment.class);
        bind(ExtMediaDescrip.class);
    }

    private void bindManagers() {
        bind(UserManager.class).to(UserManagerDefault.class);
        bind(GroupManager.class).to(GroupManagerDefault.class);
        bind(ContentManager.class).to(ContentManagerDefault.class);
        bind(ToolConfigurationManager.class).to(ToolConfigurationManagerDefault.class);
        bind(ContainerManager.class).to(ContainerManagerDefault.class);
        bind(LicenseManager.class).to(LicenseManagerDefault.class);
        bind(SocialNetworkManager.class).to(SocialNetworkManagerDefault.class);
        bind(XmppManager.class).to(XmppManagerDefault.class);
        bind(RateManager.class).to(RateManagerDefault.class);
        bind(I18nCountryManager.class).to(I18nCountryManagerDefault.class);
        bind(I18nLanguageManager.class).to(I18nLanguageManagerDefault.class);
        bind(I18nTranslationManager.class).to(I18nTranslationManagerDefault.class);
        bind(TagManager.class).to(TagManagerDefault.class);
        bind(TagUserContentManager.class).to(TagUserContentManagerDefault.class);
        bind(CommentManager.class).to(CommentManagerDefault.class);
        bind(FileManager.class).to(FileManagerDefault.class);
        bind(ExtMediaDescripManager.class).to(ExtMediaDescripManagerDefault.class);
    }

    private void bindRPC() {
        bind(SiteService.class).to(SiteRPC.class);
        bind(GroupService.class).to(GroupRPC.class);
        bind(ContentService.class).to(ContentRPC.class);
        bind(UserService.class).to(UserRPC.class);
        bind(SocialNetworkService.class).to(SocialNetworkRPC.class);
        bind(I18nService.class).to(I18nRPC.class);
    }

    private void bindServices() {
        bind(UserInfoService.class).to(UserInfoServiceDefault.class);
        bind(CreationService.class).to(CreationServiceDefault.class);
        bind(AccessRightsService.class).to(AccessRightsServiceDefault.class);
        bind(AccessService.class).to(AccessServiceDefault.class);
        bind(FinderService.class).to(FinderServiceDefault.class);
        bind(StateService.class).to(StateServiceDefault.class);
        bind(I18nTranslationService.class).to(I18nTranslationServiceDefault.class);
        bind(SessionService.class).to(SessionServiceDefault.class);
    }

    @Override
    protected void configure() {
        install(PersistenceService.usingJpa().across(UnitOfWork.TRANSACTION).buildModule());
        bind(KunePersistenceService.class);

        bindFinders();
        bindManagers();
        bindRPC();
        bindServices();
        bind(KuneProperties.class).to(KunePropertiesDefault.class);
        bind(Mapper.class).to(DozerMapper.class);
        bind(ServerToolRegistry.class);
        // bind(FileUploadManager.class).in(ServletScopes.REQUEST);
        bind(FileUploadManager.class);
        bind(FileDownloadManager.class);
        bind(EntityLogoUploadManager.class);
        bind(EntityLogoDownloadManager.class);

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authenticated.class),
                outermostCall(new AuthenticatedMethodInterceptor()));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authorizated.class),
                outermostCall(new AuthorizatedMethodInterceptor()));
    }

}
