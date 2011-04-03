package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInOrSignOutEvent;
import cc.kune.core.client.state.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;

import com.google.inject.Inject;

public abstract class SessionAction extends AbstractExtendedAction {
    protected final Session session;

    @Inject
    public SessionAction(final Session session, final boolean authNeed) {
        this.session = session;
        session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
            @Override
            public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
                refreshStatus(authNeed, event.isLogged());
            }
        });
    }

    public void refreshStatus(final boolean authNeed, final boolean isLogged) {
        boolean visible = false;
        final boolean noLogged = !isLogged;
        if (authNeed && noLogged) {
            visible = false;
        } else {
            // Auth ok
            visible = true;
        }
        setVisible(visible);
    }

    public void setVisible(final boolean visible) {
        setEnabled(visible);
        putValue(GuiActionDescrip.VISIBLE, visible);
    }
}
