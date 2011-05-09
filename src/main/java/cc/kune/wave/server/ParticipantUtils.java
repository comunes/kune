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
package cc.kune.wave.server;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.properties.DatabaseProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ParticipantUtils {

    private final String domain;
    private final ParticipantId superAdmin;

    @Inject
    public ParticipantUtils(@Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain,
            final DatabaseProperties databaseProperties) throws InvalidParticipantAddress {
        this.domain = domain;
        superAdmin = ofImpl(databaseProperties.getAdminShortName());
    }

    public ParticipantId getSuperAdmin() {
        return superAdmin;
    }

    public ParticipantId of(final String username) {
        return ofImpl(username);
    }

    private ParticipantId ofImpl(final String username) {
        try {
            if (username.contains(ParticipantId.DOMAIN_PREFIX)) {
                return ParticipantId.of(username);
            } else {
                return ParticipantId.of(username + ParticipantId.DOMAIN_PREFIX + domain);
            }
        } catch (final InvalidParticipantAddress e) {
            throw new DefaultException("Error getting Wave participant Id");
        }
    }
}
