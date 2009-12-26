package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.AccessRightsClientManager;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.events.Listener2;
import com.google.gwt.resources.client.ImageResource;

public abstract class RolAction extends AbstractExtendedAction {

    public static UIStatus refreshStatus(final AccessRolDTO rolRequired, final boolean authNeed,
            final boolean isLogged, final boolean visibleForMembers, final boolean visibleForNonMemb,
            final AccessRightsDTO newRights) {
        boolean newVisibility = false;
        boolean newEnabled = false;
        if (authNeed && !isLogged) {
            newVisibility = newEnabled = false;
        } else {
            // Auth ok
            newEnabled = RolComparator.isEnabled(rolRequired, newRights);
            if (newEnabled) {
                final boolean isMember = RolComparator.isMember(newRights);
                newEnabled = newVisibility = ((isMember && visibleForMembers) || (!isMember && visibleForNonMemb));
            } else {
                newVisibility = false;
            }
        }
        return new UIStatus(newVisibility, newEnabled);
    }

    protected final Session session;
    protected final StateManager stateManager;
    protected final I18nTranslationService i18n;
    private boolean visibleForNonMemb;
    private boolean visibleForMembers;
    private boolean authNeed;

    public RolAction(final Session session, final StateManager stateManager,
            final AccessRightsClientManager rightsManager, final I18nTranslationService i18n,
            final AccessRolDTO rolRequired, final String text, final String tooltip, final ImageResource icon) {
        super(text, tooltip, icon);
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.visibleForMembers = true;
        this.visibleForNonMemb = true;
        this.authNeed = false;
        rightsManager.onRightsChanged(new Listener2<AccessRightsDTO, AccessRightsDTO>() {
            public void onEvent(final AccessRightsDTO prevRights, final AccessRightsDTO newRights) {
                setStatus(refreshStatus(rolRequired, authNeed, session.isLogged(), visibleForMembers,
                        visibleForNonMemb, newRights));
            }

        });
    }

    public void setMustBeAuthenticated(final boolean authNeed) {
        this.authNeed = authNeed;
    }

    public void setVisible(final boolean forMembers, final boolean forNonMembers) {
        this.visibleForMembers = forMembers;
        this.visibleForNonMemb = forNonMembers;
    }

    private void setStatus(final UIStatus refreshStatus) {
        setEnabled(refreshStatus.isEnabled());
        putValue(GuiActionDescrip.VISIBLE, refreshStatus.isVisible());
    }
}
