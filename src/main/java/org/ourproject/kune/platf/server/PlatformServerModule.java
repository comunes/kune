
package org.ourproject.kune.platf.server;

import static org.ourproject.kune.platf.server.OutermostCallInterceptor.outermostCall;

import org.ourproject.kune.app.server.AbstractExtendedModule;
import org.ourproject.kune.chat.server.managers.XmppManager;
import org.ourproject.kune.chat.server.managers.XmppManagerDefault;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
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
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContainerManagerDefault;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentManagerDefault;
import org.ourproject.kune.platf.server.content.CreationService;
import org.ourproject.kune.platf.server.content.CreationServiceDefault;
import org.ourproject.kune.platf.server.i18n.I18nTranslationServiceDefault;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.RateManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.manager.TagManager;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.manager.UserManager;
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
import org.ourproject.kune.platf.server.tool.ToolRegistry;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.platf.server.users.UserInfoServiceDefault;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;

import com.google.inject.matcher.Matchers;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;

public class PlatformServerModule extends AbstractExtendedModule {
    @Override
    protected void configure() {
        install(PersistenceService.usingJpa().across(UnitOfWork.TRANSACTION).buildModule());
        bind(KunePersistenceService.class);

        bindManagers();
        bindRPC();
        bindServices();
        bind(KuneProperties.class).to(KunePropertiesDefault.class);
        bind(Mapper.class).to(DozerMapper.class);
        bind(ToolRegistry.class);

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authenticated.class),
                outermostCall(new AuthenticatedMethodInterceptor()));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Authorizated.class),
                outermostCall(new AuthorizatedMethodInterceptor()));
    }

    private void bindServices() {
        bind(UserInfoService.class).to(UserInfoServiceDefault.class);
        bind(CreationService.class).to(CreationServiceDefault.class);
        bind(AccessService.class).to(AccessServiceDefault.class);
        bind(FinderService.class).to(FinderServiceDefault.class);
        bind(StateService.class).to(StateServiceDefault.class);
        bind(I18nTranslationService.class).to(I18nTranslationServiceDefault.class);
        bind(SessionService.class).to(SessionServiceDefault.class);
    }

    private void bindRPC() {
        bind(SiteService.class).to(SiteRPC.class);
        bind(GroupService.class).to(GroupRPC.class);
        bind(ContentService.class).to(ContentRPC.class);
        bind(UserService.class).to(UserRPC.class);
        bind(SocialNetworkService.class).to(SocialNetworkRPC.class);
        bind(I18nService.class).to(I18nRPC.class);
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
    }
}
