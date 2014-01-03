/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;

/**
 * The Class TokenMatcher.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TokenMatcher {

  /** The encoder. */
  private static WaverefEncoder encoder;

  /** The reserved words registry. */
  private static ReservedWordsRegistryDTO reservedWordsRegistry = new ReservedWordsRegistryDTO();

  /**
   * Gets the redirect.
   * 
   * @param htoken
   *          the htoken
   * @return the redirect
   */
  public static Pair<String, String> getRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    final String[] splited = splitRedirect(token);
    if (hasRedirect(token, splited)) {
      return Pair.create(splited[0], splited[1].replaceAll("\\)$", ""));
    }
    return null;
  }

  public static ReservedWordsRegistryDTO getReservedWords() {
    return reservedWordsRegistry;
  }

  /**
   * Checks for redirect.
   * 
   * @param htoken
   *          the htoken
   * @return true, if successful
   */
  public static boolean hasRedirect(final String htoken) {
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
   * @param htoken
   *          the htoken
   * @param splited
   *          the splited
   * @return true, if successful
   */
  private static boolean hasRedirect(final String htoken, final String[] splited) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token.endsWith(")") && splited.length == 2;
  }

  /**
   * Inits the.
   * 
   * @param encoder
   *          the encoder
   */
  public static void init(final WaverefEncoder encoder) {
    assert encoder != null;
    TokenMatcher.encoder = encoder;
  }

  /**
   * Checks if is group token.
   * 
   * @param htoken
   *          the htoken
   * @return true, if is group token
   */
  public static boolean isGroupToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token != null && !isWaveToken(token) && !hasRedirect(token)
        && !reservedWordsRegistry.contains(token) && !new StateToken(token).hasNothing();
  }

  /**
   * Checks if is home token.
   * 
   * @param htoken
   *          the htoken
   * @return true, if is home token
   */
  public static boolean isHomeToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return SiteTokens.HOME.equals(token);
  }

  /**
   * Checks if is inbox token.
   * 
   * @param htoken
   *          the htoken
   * @return true, if is inbox token
   */
  public static boolean isInboxToken(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return SiteTokens.WAVE_INBOX.equals(token);
  }

  /**
   * Checks if is wave token.
   * 
   * @param htoken
   *          the htoken
   * @return true, if is wave token
   */
  public static boolean isWaveToken(final String htoken) {
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
   * @param htoken
   *          the htoken
   * @return the string[]
   */
  private static String[] splitRedirect(final String htoken) {
    final String token = HistoryUtils.undoHashbang(htoken);
    return token.split("\\(", 2);
  }

  /**
   * Instantiates a new token matcher.
   * 
   * @param reservedWordsRegistry
   *          the reserved words registry
   */
  public TokenMatcher() {
    SessionInstance.get().onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        reservedWordsRegistry = event.getInitData().getReservedWords();
      }
    });
  }
}
