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
package cc.kune.wave.client.kspecific;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.box.webclient.search.SimpleSearch;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.widget.common.ImplPanel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class WebClientMock.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WebClientMock extends Composite implements WaveClientView {

  /** The Constant MOCK_MSG. */
  private static final String MOCK_MSG = "This is only a mock of the wave client, only to make development testing not so heavy";

  /** The impl panel. */
  private ImplPanel implPanel;

  /**
   * Instantiates a new web client mock.
   */
  public WebClientMock() {
    initWidget(new Label(MOCK_MSG));
  }

  @Override
  public void addToBottonBar(final IsWidget widget) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#clear()
   */
  @Override
  public void clear() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#getChannel()
   */
  @Override
  public RemoteViewServiceMultiplexer getChannel() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#getLoading()
   */
  @Override
  public Element getLoading() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#getProfiles()
   */
  @Override
  public ProfileManager getProfiles() {
    return null;
  }

  @Override
  public SimpleSearch getSearch() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.wave.client.kspecific.WaveClientView#getStackTraceAsync(java.lang
   * .Throwable, org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor)
   */
  @Override
  public void getStackTraceAsync(final Throwable caught, final Accessor<SafeHtml> accessor) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#getWaveHolder()
   */
  @Override
  public ImplPanel getWaveHolder() {
    if (implPanel == null) {
      implPanel = new ImplPanel("<span style='padding:20px;'>" + MOCK_MSG + "</span>");
    }
    return implPanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#getWebSocket()
   */
  @Override
  public WaveWebSocketClient getWebSocket() {
    return null;
  }

  @Override
  public void hideBottomToolbar() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#login()
   */
  @Override
  public void login() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.WaveClientView#logout()
   */
  @Override
  public void logout() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.maxmin.IsMaximizable#setMaximized(boolean)
   */
  @Override
  public void setMaximized(final boolean maximized) {
  }

  @Override
  public void showBottomToolbar() {
    // TODO Auto-generated method stub

  }

}
