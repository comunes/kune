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
package cc.kune.wave.client.kspecific;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.widget.common.ImplPanel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class WebClientMock extends Composite implements WaveClientView {

  private static final String MOCK_MSG = "This is only a mock of the wave client, only to make development testing not so heavy";
  private ImplPanel implPanel;

  public WebClientMock() {
    initWidget(new Label(MOCK_MSG));
  }

  @Override
  public void clear() {
  }

  @Override
  public RemoteViewServiceMultiplexer getChannel() {
    return null;
  }

  @Override
  public Element getLoading() {
    return null;
  }

  @Override
  public ProfileManager getProfiles() {
    return null;
  }

  @Override
  public void getStackTraceAsync(final Throwable caught, final Accessor<SafeHtml> accessor) {
  }

  @Override
  public ImplPanel getWaveHolder() {
    if (implPanel == null) {
      implPanel = new ImplPanel("<span style='padding:20px;'>" + MOCK_MSG + "</span>");
    }
    return implPanel;
  }

  @Override
  public WaveWebSocketClient getWebSocket() {
    return null;
  }

  @Override
  public void login() {
  }

  @Override
  public void logout() {
  }

  @Override
  public void setMaximized(final boolean maximized) {
  }

}
