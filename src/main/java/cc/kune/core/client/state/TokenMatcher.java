/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class TokenMatcher.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TokenMatcher {

  /** The encoder. */
  private WaverefEncoder encoder;
  
  /** The reserved words registry. */
  private final ReservedWordsRegistryDTO reservedWordsRegistry;

  /**
   * Instantiates a new token matcher.
   *
   * @param reservedWordsRegistry the reserved words registry
   */
  @Inject
  public TokenMatcher(final ReservedWordsRegistryDTO reservedWordsRegistry) {
    this.reservedWordsRegistry = reservedWordsRegistry;
  }

  /**
   * Gets the redirect.
   *
   * @param htoken the htoken
   * @return the redirect
   */
  public Pair<String, String> getRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    final String[] splited = splitRedirect(token);
    if (hasRedirect(token, splited)) {
      return Pair.create(splited[0], splited[1].replaceAll("\\)$", ""));
    }
    return null;
  }

  /**
   * Checks for redirect.
   *
   * @param htoken the htoken
   * @return true, if successful
   */
  public boolean hasRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    final String[] splited = splitRedirect(token);
    if (hasRedirect(token, splited)) {
      return true;
    }
    return false;
  }

  /**
   * Checks for redirect.
   *
   * @param htoken the htoken
   * @param splited the splited
   * @return true, if successful
   */
  private boolean hasRedirect(final String htoken, final String[] splited) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token.endsWith(")") && splited.length == 2;
  }

  /**
   * Inits the.
   *
   * @param encoder the encoder
   */
  public void init(final WaverefEncoder encoder) {
    assert encoder != null;
    this.encoder = encoder;
  }

  /**
   * Checks if is group token.
   *
   * @param htoken the htoken
   * @return true, if is group token
   */
  public boolean isGroupToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token != null && !isWaveToken(token) && !hasRedirect(token)
        && !reservedWordsRegistry.contains(token) && !new StateToken(token).hasNothing();
  }

  /**
   * Checks if is inbox token.
   *
   * @param htoken the htoken
   * @return true, if is inbox token
   */
  public boolean isInboxToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return SiteTokens.WAVE_INBOX.equals(token);
  }

  /**
   * Checks if is home token.
   *
   * @param htoken the htoken
   * @return true, if is home token
   */
  public boolean isHomeToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return SiteTokens.HOME.equals(token);
  }

  /**
   * Checks if is wave token.
   *
   * @param htoken the htoken
   * @return true, if is wave token
   */
  public boolean isWaveToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    assert encoder != null;
    try {
      return token == null ? false : encoder.decodeWaveRefFromPath(token) != null;
    } catch (final InvalidWaveRefException e) {
      return false;
    }
  }

  /**
   * Split redirect.
   *
   * @param htoken the htoken
   * @return the string[]
   */
  private String[] splitRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token.split("\\(", 2);
  }

}
