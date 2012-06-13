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

import cc.kune.common.shared.utils.Pair;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;

import com.google.inject.Inject;

public class TokenMatcher {

  private WaverefEncoder encoder;
  private final ReservedWordsRegistryDTO reservedWordsRegistry;

  @Inject
  public TokenMatcher(final ReservedWordsRegistryDTO reservedWordsRegistry) {
    this.reservedWordsRegistry = reservedWordsRegistry;
  }

  public Pair<String, String> getRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    final String[] splited = splitRedirect(token);
    if (hasRedirect(token, splited)) {
      return Pair.create(splited[0], splited[1].replaceAll("\\)$", ""));
    }
    return null;
  }

  public boolean hasRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    final String[] splited = splitRedirect(token);
    if (hasRedirect(token, splited)) {
      return true;
    }
    return false;
  }

  private boolean hasRedirect(final String htoken, final String[] splited) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token.endsWith(")") && splited.length == 2;
  }

  public void init(final WaverefEncoder encoder) {
    assert encoder != null;
    this.encoder = encoder;
  }

  public boolean isGroupToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token != null && !isWaveToken(token) && !hasRedirect(token)
        && !reservedWordsRegistry.contains(token) && !new StateToken(token).hasNothing();
  }

  public boolean isInboxToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return SiteTokens.WAVE_INBOX.equals(token);
  }

  public boolean isHomeToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return SiteTokens.HOME.equals(token);
  }

  public boolean isWaveToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    assert encoder != null;
    try {
      return token == null ? false : encoder.decodeWaveRefFromPath(token) != null;
    } catch (final InvalidWaveRefException e) {
      return false;
    }
  }

  private String[] splitRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token.split("\\(", 2);
  }

}
