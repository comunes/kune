package cc.kune.core.client.cookies;

import java.util.Date;

import cc.kune.core.client.state.Session;

import com.google.gwt.user.client.Cookies;

public class CookiesManagerImpl implements CookiesManager {

    public CookiesManagerImpl() {
    }

    @Override
    public String getCurrentCookie() {
        return Cookies.getCookie(Session.USERHASH);
    }

    @Override
    public void removeCookie() {
        // FIXME: Remove cookie doesn't works in all browsers, know
        // issue:
        // http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/ded86778ee56690/515dc513c7d085eb?lnk=st&q=remove+cookie#515dc513c7d085eb
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=1735&q=removeCookie
        Cookies.removeCookie(Session.USERHASH);
        // Workaround:
        Cookies.setCookie(Session.USERHASH, null, new Date(0), null, "/", false);
    }

}
