package cc.kune.core.client.cookies;

import cc.kune.core.client.cookies.CookiesManager.CookiesManagerView;
import cc.kune.core.client.state.Session;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class CookiesManagerViewImpl extends ViewImpl implements CookiesManagerView {

    @Inject
    public CookiesManagerViewImpl() {
    }

    @Override
    public String getCurrentCookie() {
        return Cookies.getCookie(Session.USERHASH);
    }

    @Override
    public Widget asWidget() {
        return null;
    }

}
