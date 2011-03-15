package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;

import com.google.inject.Inject;

public abstract class SessionAction extends AbstractExtendedAction {
    protected final Session session;

    @Inject
    public SessionAction(final Session session, final boolean authNeed) {
        this.session = session;
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(UserSignInEvent event) {
                refreshStatus(authNeed, true);
            }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(UserSignOutEvent event) {
                refreshStatus(authNeed, false);
            }
        });
    }

    public void refreshStatus(final boolean authNeed, final boolean isLogged) {
        boolean newVisibility = false;
        boolean newEnabled = false;
        if (authNeed && !isLogged) {
            newVisibility = newEnabled = false;
        } else {
            // Auth ok
            newVisibility = newEnabled = true;
        }
        setEnabled(newEnabled);
        putValue(GuiActionDescrip.VISIBLE, newVisibility);
    }
}
