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
package cc.kune.core.server;

import static cc.kune.core.server.OutermostCallInterceptor.outermostCall;
import cc.kune.chat.server.ChatManager;
import cc.kune.chat.server.ChatManagerDefault;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.client.rpcservices.SocialNetworkService;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.access.AccessRightsService;
import cc.kune.core.server.access.AccessRightsServiceDefault;
import cc.kune.core.server.access.AccessRightsUtils;
import cc.kune.core.server.access.AccessService;
import cc.kune.core.server.access.AccessServiceDefault;
import cc.kune.core.server.access.FinderService;
import cc.kune.core.server.access.FinderServiceDefault;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.AuthenticatedMethodInterceptor;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.auth.AuthorizatedMethodInterceptor;
import cc.kune.core.server.auth.SuperAdmin;
import cc.kune.core.server.auth.SuperAdminMethodInterceptor;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContainerManagerDefault;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.ContentManagerDefault;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.content.CreationServiceDefault;
import cc.kune.core.server.content.XMLActionReader;
import cc.kune.core.server.i18n.I18nTranslationServiceDefault;
import cc.kune.core.server.mail.MailService;
import cc.kune.core.server.mail.MailServiceDefault;
import cc.kune.core.server.manager.ExtMediaDescripManager;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.manager.KuneWaveManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.RateManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.manager.TagManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.server.manager.TagUserContentManagerDefault;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.manager.file.EntityLogoDownloadManager;
import cc.kune.core.server.manager.file.EntityLogoUploadManager;
import cc.kune.core.server.manager.file.FileDownloadManager;
import cc.kune.core.server.manager.file.FileManagerDefault;
import cc.kune.core.server.manager.file.FileUploadManager;
import cc.kune.core.server.manager.impl.ExtMediaDescripManagerDefault;
import cc.kune.core.server.manager.impl.GroupManagerDefault;
import cc.kune.core.server.manager.impl.I18nCountryManagerDefault;
import cc.kune.core.server.manager.impl.I18nLanguageManagerDefault;
import cc.kune.core.server.manager.impl.I18nTranslationManagerDefault;
import cc.kune.core.server.manager.impl.KuneWaveManagerDefault;
import cc.kune.core.server.manager.impl.LicenseManagerDefault;
import cc.kune.core.server.manager.impl.RateManagerDefault;
import cc.kune.core.server.manager.impl.SocialNetworkManagerDefault;
import cc.kune.core.server.manager.impl.TagManagerDefault;
import cc.kune.core.server.manager.impl.ToolConfigurationManagerDefault;
import cc.kune.core.server.manager.impl.UserManagerDefault;
import cc.kune.core.server.mapper.DozerMapper;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.properties.KunePropertiesDefault;
import cc.kune.core.server.rpc.ContentRPC;
import cc.kune.core.server.rpc.GroupRPC;
import cc.kune.core.server.rpc.I18nRPC;
import cc.kune.core.server.rpc.SiteRPC;
import cc.kune.core.server.rpc.SocialNetworkRPC;
import cc.kune.core.server.rpc.UserRPC;
import cc.kune.core.server.state.StateService;
import cc.kune.core.server.state.StateServiceDefault;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.users.UserInfoService;
import cc.kune.core.server.users.UserInfoServiceDefault;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.server.xmpp.XmppManagerDefault;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.SocialNetwork;
import cc.kune.lists.client.rpc.ListsService;
import cc.kune.lists.server.rpc.ListsRPC;
import cc.kune.wave.server.KuneWaveService;
import cc.kune.wave.server.KuneWaveServiceDefault;
import cc.kune.wave.server.ParticipantUtils;

import com.google.inject.matcher.Matchers;

public class PlatformServerModule extends AbstractExtendedModule {
  private void bindFinders() {
    // http://google-guice.googlecode.com/svn/trunk/javadoc/com/google/inject/Injector.html
    // (...) Just-in-time bindings created for child injectors will be
    // created in an ancestor injector whenever possible (...)
    // (This fails with finders, then we make explicit bindings)

    bind(SocialNetwork.class);
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
    bind(ChatManager.class).to(ChatManagerDefault.class);
    bind(RateManager.class).to(RateManagerDefault.class);
    bind(I18nCountryManager.class).to(I18nCountryManagerDefault.class);
    bind(I18nLanguageManager.class).to(I18nLanguageManagerDefault.class);
    bind(I18nTranslationManager.class).to(I18nTranslationManagerDefault.class);
    bind(TagManager.class).to(TagManagerDefault.class);
    bind(TagUserContentManager.class).to(TagUserContentManagerDefault.class);
    bind(FileManager.class).to(FileManagerDefault.class);
    bind(ExtMediaDescripManager.class).to(ExtMediaDescripManagerDefault.class);
    bind(KuneWaveManager.class).to(KuneWaveManagerDefault.class);
  }

  private void bindRPC() {
    bind(SiteService.class).to(SiteRPC.class);
    bind(GroupService.class).to(GroupRPC.class);
    bind(ContentService.class).to(ContentRPC.class);
    bind(UserService.class).to(UserRPC.class);
    bind(SocialNetworkService.class).to(SocialNetworkRPC.class);
    bind(I18nService.class).to(I18nRPC.class);
    bind(ListsService.class).to(ListsRPC.class);
  }

  private void bindServices() {
    bind(UserInfoService.class).to(UserInfoServiceDefault.class);
    bind(CreationService.class).to(CreationServiceDefault.class);
    bind(AccessRightsService.class).to(AccessRightsServiceDefault.class);
    bind(AccessService.class).to(AccessServiceDefault.class);
    bind(FinderService.class).to(FinderServiceDefault.class);
    bind(StateService.class).to(StateServiceDefault.class);
    bind(I18nTranslationService.class).to(I18nTranslationServiceDefault.class);
    bind(KuneWaveService.class).to(KuneWaveServiceDefault.class);
    bind(MailService.class).to(MailServiceDefault.class);
    bind(XMLActionReader.class);
  }

  @Override
  protected void configure() {
    // install(PersistService.usingJpa().across(UnitOfWork.TRANSACTION).buildModule());
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
    bind(ParticipantUtils.class);
    requestStaticInjection(AccessRightsUtils.class);
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authenticated.class),
        outermostCall(new AuthenticatedMethodInterceptor()));
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authorizated.class),
        outermostCall(new AuthorizatedMethodInterceptor()));
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(SuperAdmin.class),
        outermostCall(new SuperAdminMethodInterceptor()));
  }

}
