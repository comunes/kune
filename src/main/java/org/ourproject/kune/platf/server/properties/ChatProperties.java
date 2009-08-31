/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Syntactic sugar!
 */
@Singleton
public class ChatProperties {
    private final KuneProperties properties;

    @Inject
    public ChatProperties(final KuneProperties properties) {
        this.properties = properties;
    }

    public String getAdminJID() {
        return properties.get(KuneProperties.SITE_ADMIN_SHORTNAME);
    }

    public String getAdminPasswd() {
        return properties.get(KuneProperties.SITE_ADMIN_PASSWD);
    }

    public String getDomain() {
        return properties.get(KuneProperties.CHAT_DOMAIN);
    }

    public String getHttpBase() {
        return properties.get(KuneProperties.CHAT_HTTP_BASE);
    }

    public String getRoomHost() {
        return properties.get(KuneProperties.CHAT_ROOM_HOST);
    }
}
