/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.client.rpcservices.InvitationService;
import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.client.rpcservices.SocialNetService;
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
import cc.kune.core.server.auth.ShouldBeMember;
import cc.kune.core.server.auth.ShouldBeMemberMethodInterceptor;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContainerManagerDefault;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.ContentManagerDefault;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.content.CreationServiceDefault;
import cc.kune.core.server.content.XMLActionReader;
import cc.kune.core.server.i18n.I18nTranslationServiceMultiLang;
import cc.kune.core.server.i18n.impl.I18nTranslationServiceDefault;
import cc.kune.core.server.mail.MailService;
import cc.kune.core.server.mail.MailServiceDefault;
import cc.kune.core.server.manager.ExtMediaDescripManager;
import cc.kune.core.server.manager.FileManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.manager.KuneWaveManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.ParticipantEntityManager;
import cc.kune.core.server.manager.RateManager;
import cc.kune.core.server.manager.SiteManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.manager.TagManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.manager.UserSignInLogManager;
import cc.kune.core.server.manager.WaveEntityManager;
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
import cc.kune.core.server.manager.impl.InvitationManagerDefault;
import cc.kune.core.server.manager.impl.KuneWaveManagerDefault;
import cc.kune.core.server.manager.impl.LicenseManagerDefault;
import cc.kune.core.server.manager.impl.ParticipantEntityManagerDefault;
import cc.kune.core.server.manager.impl.RateManagerDefault;
import cc.kune.core.server.manager.impl.SiteManagerDefault;
import cc.kune.core.server.manager.impl.SocialNetworkManagerDefault;
import cc.kune.core.server.manager.impl.TagManagerDefault;
import cc.kune.core.server.manager.impl.TagUserContentManagerDefault;
import cc.kune.core.server.manager.impl.ToolConfigurationManagerDefault;
import cc.kune.core.server.manager.impl.UserManagerDefault;
import cc.kune.core.server.manager.impl.UserSignInLogManagerDefault;
import cc.kune.core.server.manager.impl.WaveEntityManagerDefault;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.mapper.KuneMapperDefault;
import cc.kune.core.server.notifier.NotificationSender;
import cc.kune.core.server.notifier.NotificationSenderDefault;
import cc.kune.core.server.notifier.PendingNotificationSender;
import cc.kune.core.server.persist.KunePersistenceService;
import cc.kune.core.server.rpc.ContentRPC;
import cc.kune.core.server.rpc.GroupRPC;
import cc.kune.core.server.rpc.I18nRPC;
import cc.kune.core.server.rpc.InvitationRPC;
import cc.kune.core.server.rpc.SiteRPC;
import cc.kune.core.server.rpc.SocialNetworkRPC;
import cc.kune.core.server.rpc.StatsRPC;
import cc.kune.core.server.rpc.UserRPC;
import cc.kune.core.server.scheduler.CronServerTasksManager;
import cc.kune.core.server.state.StateService;
import cc.kune.core.server.state.StateServiceDefault;
import cc.kune.core.server.stats.StatsService;
import cc.kune.core.server.stats.StatsServiceDefault;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.users.UserInfoService;
import cc.kune.core.server.users.UserInfoServiceDefault;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.server.xmpp.XmppManagerDefault;
import cc.kune.hspace.client.ClientStatsService;
import cc.kune.lists.client.rpc.ListsService;
import cc.kune.lists.server.ListServerService;
import cc.kune.lists.server.ListServerServiceDefault;
import cc.kune.lists.server.rpc.ListsRPC;
import cc.kune.wave.server.kspecific.KuneWaveService;
import cc.kune.wave.server.kspecific.KuneWaveServiceDefault;
import cc.kune.wave.server.kspecific.ParticipantUtils;
import cc.kune.wave.server.kspecific.WaveEmailNotifier;

import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;

// TODO: Auto-generated Javadoc
/**
 * The Class PlatformServerModule.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PlatformServerModule extends AbstractExtendedModule {

  /**
   * Bind managers.
   */
  private void bindManagers() {
    bind(SiteManager.class).to(SiteManagerDefault.class);
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
    bind(UserSignInLogManager.class).to(UserSignInLogManagerDefault.class);
    bind(InvitationManager.class).to(InvitationManagerDefault.class);
    bind(WaveEntityManager.class).to(WaveEntityManagerDefault.class);
    bind(ParticipantEntityManager.class).to(ParticipantEntityManagerDefault.class);
  }

  /**
   * Bind rpc.
   */
  private void bindRPC() {
    bind(SiteService.class).to(SiteRPC.class);
    bind(GroupService.class).to(GroupRPC.class);
    bind(ContentService.class).to(ContentRPC.class);
    bind(UserService.class).to(UserRPC.class);
    bind(SocialNetService.class).to(SocialNetworkRPC.class);
    bind(I18nService.class).to(I18nRPC.class);
    bind(ListsService.class).to(ListsRPC.class);
    bind(ClientStatsService.class).to(StatsRPC.class);
    bind(InvitationService.class).to(InvitationRPC.class);
  }

  /**
   * Bind services.
   */
  private void bindServices() {
    bind(UserInfoService.class).to(UserInfoServiceDefault.class);
    bind(CreationService.class).to(CreationServiceDefault.class);
    bind(AccessRightsService.class).to(AccessRightsServiceDefault.class);
    bind(AccessService.class).to(AccessServiceDefault.class);
    bind(FinderService.class).to(FinderServiceDefault.class);
    bind(StateService.class).to(StateServiceDefault.class);
    bind(I18nTranslationService.class).to(I18nTranslationServiceDefault.class);
    bind(I18nTranslationServiceMultiLang.class).to(I18nTranslationServiceDefault.class);
    bind(KuneWaveService.class).to(KuneWaveServiceDefault.class);
    bind(MailService.class).to(MailServiceDefault.class);
    bind(StatsService.class).to(StatsServiceDefault.class);
    bind(ListServerService.class).to(ListServerServiceDefault.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.inject.AbstractModule#configure()
   */
  @Override
  protected void configure() {
    // install(PersistService.usingJpa().across(UnitOfWork.TRANSACTION).buildModule());
    bind(KunePersistenceService.class);

    bindManagers();
    bindRPC();
    bindServices();
    bind(KuneMapper.class).to(KuneMapperDefault.class);
    bind(ServerToolRegistry.class).in(Singleton.class);
    bind(FileUploadManager.class).in(Singleton.class);
    bind(FileDownloadManager.class).in(Singleton.class);
    bind(EntityLogoUploadManager.class).in(Singleton.class);
    bind(EntityLogoDownloadManager.class).in(Singleton.class);
    bind(ParticipantUtils.class).in(Singleton.class);
    // bind(UserSessionManager.class).to(UserSessionManagerImpl.class).in(Singleton.class);
    // bind(UsersOnline.class).to(UserSessionManagerImpl.class).asEagerSingleton();
    requestStaticInjection(AccessRightsUtils.class);
    bind(WaveEmailNotifier.class).in(Singleton.class);
    bind(PendingNotificationSender.class).in(Singleton.class);
    bind(NotificationSender.class).to(NotificationSenderDefault.class).in(Singleton.class);
    bind(CronServerTasksManager.class).in(Singleton.class);
    bind(XMLActionReader.class).in(Singleton.class);

    bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authenticated.class),
        outermostCall(new AuthenticatedMethodInterceptor()));
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authorizated.class),
        outermostCall(new AuthorizatedMethodInterceptor()));
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(ShouldBeMember.class),
        outermostCall(new ShouldBeMemberMethodInterceptor()));
  }

}
