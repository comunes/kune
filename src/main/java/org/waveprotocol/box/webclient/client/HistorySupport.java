// @formatter:off
/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.waveprotocol.box.webclient.client;

import javax.annotation.Nullable;

import org.waveprotocol.box.webclient.client.events.Log;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.core.client.state.TokenMatcher;

/**
 * Contains the code to interface the history event mechanism with the client's
 * event bus. At the moment, a history token encodes a wave id or wave ref.
 */
public class HistorySupport {
  private static final Log LOG = Log.get(HistorySupport.class);
  private static TokenMatcher tokenMatcher;
  private static cc.kune.core.client.state.Session session;
  public static void kuneInit(TokenMatcher tokenMatcher, cc.kune.core.client.state.Session session) {
    HistorySupport.tokenMatcher = tokenMatcher;
    HistorySupport.session = session;
  }
    public static void init() {
//    History.addValueChangeHandler(new ValueChangeHandler<String>() {
//      @Override
//      public void onValueChange(ValueChangeEvent<String> event) {
//        String encodedToken = event.getValue();
//        if (encodedToken == null || encodedToken.length() == 0) {
//          return;
//        }
//        WaveRef waveRef = waveRefFromHistoryToken(encodedToken);
//        if (waveRef == null) {
//          LOG.info("History token contains invalid path: " + encodedToken);
//          return;
//        }
//        LOG.info("Changing selected wave based on history event to " + waveRef.toString());
//        ClientEvents.get().fireEvent(new WaveSelectionEvent(waveRef));
//      }
//    });
  }

  /**
   * @param encodedToken token to parse into waveref
   * @return null if cannot parse into valid waveRef
   */
  @Nullable
  public static WaveRef waveRefFromHistoryToken(String encodedToken) {
    LOG.info("Encoded token: " + encodedToken);
    String waveToken = encodedToken;
    if (tokenMatcher.isGroupToken(encodedToken) || tokenMatcher.isHomeToken(encodedToken)) {
      waveToken = session.getContentState().getWaveRef();
      LOG.info("Kune URL: " + encodedToken + " = " + waveToken);
    }
    try {
      return GwtWaverefEncoder.decodeWaveRefFromPath(waveToken);
    } catch (InvalidWaveRefException e) {
      return null;
    }
  }

  public static String historyTokenFromWaveref(WaveRef ref) {
    return GwtWaverefEncoder.encodeToUriPathSegment(ref);
  }

  private HistorySupport() {
  }
}
