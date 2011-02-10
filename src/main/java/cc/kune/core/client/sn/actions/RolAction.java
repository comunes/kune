package cc.kune.core.client.sn.actions;


import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsChangedEvent;
import cc.kune.core.client.state.AccessRightsChangedEvent.AccessRightsChangedHandler;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class RolAction extends AbstractExtendedAction {
    protected final I18nTranslationService i18n;
    protected final Session session;
    protected final Provider<SocialNetworkServiceAsync> snServiceProvider;
    protected final StateManager stateManager;

    @Inject
    public RolAction(final StateManager stateManager, final Session session, final I18nTranslationService i18n,
            final CoreResources res, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final AccessRightsClientManager rightsManager, final AccessRolDTO rolRequired, final boolean authNeed,
            final boolean visibleForNonMemb, final boolean visibleForMembers) {
        this.stateManager = stateManager;
        this.session = session;
        this.i18n = i18n;
        this.snServiceProvider = snServiceProvider;
        rightsManager.onRightsChanged(new AccessRightsChangedHandler() {

            @Override
            public void onAccessRightsChanged(final AccessRightsChangedEvent event) {
                refreshStatus(rolRequired, authNeed, session.isLogged(), visibleForMembers, visibleForNonMemb,
                        event.getCurrentRights());

            }
        });
    }

    public void refreshStatus(final AccessRolDTO rolRequired, final boolean authNeed, final boolean isLogged,
            final boolean visibleForMembers, final boolean visibleForNonMemb, final AccessRights newRights) {
        boolean newVisibility = false;
        boolean newEnabled = false;
        if (authNeed && !isLogged) {
            newVisibility = newEnabled = false;
        } else {
            // Auth ok
            newEnabled = RolComparator.isEnabled(rolRequired, newRights);
            if (newEnabled) {
                final boolean isMember = RolComparator.isMember(newRights);
                newEnabled = newVisibility = isMember && visibleForMembers || !isMember && visibleForNonMemb;
            } else {
                newVisibility = false;
            }
        }
        setEnabled(newEnabled);
        putValue(GuiActionDescrip.VISIBLE, newVisibility);
    }
}
