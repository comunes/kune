package cc.kune.core.client.cookies;

import cc.kune.core.client.cookies.CookiesManager.ICookiesView;
import cc.kune.core.client.state.Session;

import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;

public class CookiesView implements ICookiesView {

    @Inject
    public CookiesView() {
    }

    public String getCurrentCookie() {
        return Cookies.getCookie(Session.USERHASH);
    }

}
