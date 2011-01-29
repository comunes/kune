package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.services.AbstractExtendedModule;
import org.ourproject.kune.workspace.client.socialnet.ParticipateAction;
import org.ourproject.kune.workspace.client.socialnet.UnjoinAction;

import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.ioc.decorator.NoDecoration;
import com.calclab.suco.client.ioc.module.Factory;

public class ActionsModule extends AbstractExtendedModule {
    @Override
    protected void onInstall() {
        register(NoDecoration.class, new Factory<ParticipateAction>(ParticipateAction.class) {
            @Override
            public ParticipateAction create() {
                return new ParticipateAction(i(Session.class), p(SocialNetworkServiceAsync.class),
                        i(StateManager.class), i(AccessRightsClientManager.class), i(I18nTranslationService.class),
                        i(IconResources.class));
            }
        });

        register(NoDecoration.class, new Factory<UnjoinAction>(UnjoinAction.class) {
            @Override
            public UnjoinAction create() {
                return new UnjoinAction(i(Session.class), p(SocialNetworkServiceAsync.class), i(StateManager.class),
                        i(AccessRightsClientManager.class), i(I18nTranslationService.class), i(IconResources.class));
            }
        });

    }
}
