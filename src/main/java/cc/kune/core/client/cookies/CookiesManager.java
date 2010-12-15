package cc.kune.core.client.cookies;

import cc.kune.core.client.state.Session;

import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;

public class CookiesManager {
    public interface ICookiesView {
        String getCurrentCookie();
    }

    @Inject
    public CookiesManager() {
    }

    public String getCurrentCookie() {
        return Cookies.getCookie(Session.USERHASH);
        // view.getCurrentCookie();
    }

    public void onDoNothing() {
    }

}
