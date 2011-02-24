/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.client.cookies;

import java.util.Date;

import cc.kune.common.client.log.Log;
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

    @Override
    public void setCookie(final String userHash) {
        // http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ
        final long duration = Session.SESSION_DURATION;
        final Date expires = new Date(System.currentTimeMillis() + duration);
        Cookies.setCookie(Session.USERHASH, userHash, expires, null, "/", false);
        Log.info("Received hash: " + userHash, null);
    }
}
