/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package cc.kune.wave.client;

import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.box.webclient.widget.frame.FramedPanel;
import org.waveprotocol.wave.model.conversation.TitleHelper;
import org.waveprotocol.wave.model.document.WaveContext;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.state.TokenMatcher;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

/**
 * Sets the browser window title to the wave title.
 * 
 * @author yurize@apache.org (Yuri Zelikov) customized for kune by @vjrj
 */
public final class CustomWindowTitleHandler implements WaveStore.Listener {

  public static CustomWindowTitleHandler install(final WaveStore waveStore, final FramedPanel waveFrame) {
    return new CustomWindowTitleHandler(waveStore, waveFrame);
  }
  private final String defaultTitle;
  private final FramedPanel waveFrame;

  private final WaveStore waveStore;

  private CustomWindowTitleHandler(final WaveStore waveStore, final FramedPanel waveFrame) {
    this.waveStore = waveStore;
    this.waveFrame = waveFrame;
    defaultTitle = I18n.t("Inbox") + " - " + Session.get().getAddress();
    init();
  }

  private String formatTitle(final String title) {
    return title + " - " + Session.get().getAddress();
  }

  private void init() {
    waveStore.addListener(this);
  }

  @Override
  public void onClosed(final WaveContext wave) {
    if (TokenMatcher.isWaveToken(History.getToken())) {
      Window.setTitle(defaultTitle);
    }
  }

  @Override
  public void onOpened(final WaveContext wave) {
    if (TokenMatcher.isWaveToken(History.getToken())) {
      final String waveTitle = TitleHelper.getTitle(wave);
      String windowTitle = formatTitle(waveTitle);
      if (waveTitle == null || waveTitle.isEmpty()) {
        windowTitle = defaultTitle;
      }
      Window.setTitle(windowTitle);
      waveFrame.setTitleText(waveTitle);
    }
  }
}
