package cc.kune.core.client.sn.actions;

import cc.kune.core.client.actions.RolActionAutoUpdated;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class SNRolAction extends RolActionAutoUpdated {
    protected final I18nTranslationService i18n;
    protected final Provider<SocialNetworkServiceAsync> snServiceProvider;

    @Inject
    public SNRolAction(final StateManager stateManager, final Session session, final I18nTranslationService i18n,
            final CoreResources res, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final AccessRightsClientManager rightsManager, final AccessRolDTO rolRequired, final boolean authNeed,
            final boolean visibleForNonMemb, final boolean visibleForMembers) {
        super(stateManager, session, rightsManager, rolRequired, authNeed, visibleForNonMemb, visibleForMembers);
        this.i18n = i18n;
        this.snServiceProvider = snServiceProvider;
    }
}
