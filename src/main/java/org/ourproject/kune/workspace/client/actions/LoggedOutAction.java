package org.ourproject.kune.workspace.client.actions;

import java.util.Date;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.Cookies;

@SuppressWarnings("unchecked")
public class LoggedOutAction implements Action {

    private final Session session;
    private final StateManager stateManager;

    public LoggedOutAction(final Session session, final StateManager stateManager) {
        this.session = session;
        this.stateManager = stateManager;
    }

    public void execute(final Object value) {
        // FIXME: Remove cookie doesn't works in all browsers, know issue:
        // http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/ded86778ee56690/515dc513c7d085eb?lnk=st&q=remove+cookie#515dc513c7d085eb
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=1735&q=removeCookie
        Cookies.removeCookie("userHash");
        // Workaround:
        Cookies.setCookie("userHash", null, new Date(0), null, "/", false);
        session.setUserHash(null);
        Site.sitebar.showLoggedUser(null);
        stateManager.reload();
    }
}
