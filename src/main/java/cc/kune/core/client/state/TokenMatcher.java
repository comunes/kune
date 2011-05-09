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
package cc.kune.core.client.state;

import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaverefEncoder;

import cc.kune.common.client.utils.Pair;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.Inject;

public class TokenMatcher {

    private WaverefEncoder encoder;
    private final ReservedWordsRegistry reservedWordsRegistry;

    @Inject
    public TokenMatcher(final ReservedWordsRegistry reservedWordsRegistry) {
        this.reservedWordsRegistry = reservedWordsRegistry;
    }

    public Pair<String, String> getRedirect(final String token) {
        final String[] splited = splitRedirect(token);
        if (hasRedirect(token, splited)) {
            return Pair.create(splited[0], splited[1].replaceAll("\\)$", ""));
        }
        return null;
    }

    public boolean hasRedirect(final String token) {
        final String[] splited = splitRedirect(token);
        if (hasRedirect(token, splited)) {
            return true;
        }
        return false;
    }

    private boolean hasRedirect(final String token, final String[] splited) {
        return token.endsWith(")") && splited.length == 2;
    }

    public void init(final WaverefEncoder encoder) {
        assert encoder != null;
        this.encoder = encoder;
    }

    public boolean isGroupToken(final String token) {
        return token != null && !isWaveToken(token) && !hasRedirect(token) && !reservedWordsRegistry.contains(token)
                && !new StateToken(token).hasNothing();
    }

    public boolean isHomeToken(final String currentToken) {
        return SiteTokens.HOME.equals(currentToken);
    }

    public boolean isWaveToken(final String token) {
        assert encoder != null;
        try {
            return token == null ? false : encoder.decodeWaveRefFromPath(token) != null;
        } catch (final InvalidWaveRefException e) {
            return false;
        }
    }

    private String[] splitRedirect(final String token) {
        return token.split("\\(", 2);
    }

}
