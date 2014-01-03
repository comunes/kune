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
package cc.kune.gspace.client.viewers;

import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.box.webclient.widget.frame.FramedPanel;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.state.SiteParameters;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.wave.client.CustomSavedStateIndicator;
import cc.kune.wave.client.CustomStagesProvider;
import cc.kune.wave.client.kspecific.AurorisColorPicker;
import cc.kune.wave.client.kspecific.WaveClientClearEvent;
import cc.kune.wave.client.kspecific.WaveClientProvider;
import cc.kune.wave.client.kspecific.WaveClientUtils;
import cc.kune.wave.client.kspecific.WaveClientView;
import cc.kune.wave.client.kspecific.WebClientMock;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * The Class ContentViewerPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveViewerPanel extends ViewImpl implements WaveViewerView {

  /** The Constant NO_CHANNEL. */
  private static final RemoteViewServiceMultiplexer NO_CHANNEL = null;

  /** The channel. */
  protected RemoteViewServiceMultiplexer channel;

  /** The color picker. */
  private final Provider<AurorisColorPicker> colorPicker;

  /** The deck. */
  @UiField
  DeckPanel deck;

  /** The dummy wave frame. */
  private FramedPanel dummyWaveFrame;

  /** The event bus. */
  private final EventBus eventBus;

  /** The id generator. */
  private IdGenerator idGenerator;

  /** The loading. */
  private Element loading;

  /** The only view panel. */
  @UiField
  InlineHTML onlyViewPanel;

  /** The only web client. */
  private final boolean onlyWebClient;

  /** The profiles. */
  private ProfileManager profiles;

  /** The wave panel, if a wave is open. */
  CustomStagesProvider wave;

  /** The wave client prov. */
  private final WaveClientProvider waveClientProv;

  /** The wave holder. */
  protected ImplPanel waveHolder;

  /** The wave holder parent. */
  @UiField
  ImplPanel waveHolderParent;

  /** The wave store. */
  private final WaveStore waveStore = new SimpleWaveStore();

  /** The wave unsaved indicator. */
  private final CustomSavedStateIndicator waveUnsavedIndicator;

  /** The widget. */
  protected Widget widget;

  /**
   * Instantiates a new content viewer panel.
   * 
   * @param wsArmor
   *          the ws armor
   * @param waveClient
   *          the wave client
   * @param capabilitiesRegistry
   *          the capabilities registry
   * @param i18n
   *          the i18n
   * @param eventBus
   *          the event bus
   * @param stateManager
   *          the state manager
   * @param dropController
   *          the drop controller
   * @param waveUnsavedIndicator
   *          the wave unsaved indicator
   * @param colorPicker
   *          the color picker
   */
  public WaveViewerPanel(final WaveClientProvider waveClient, final EventBus eventBus,
      final CustomSavedStateIndicator waveUnsavedIndicator,
      final Provider<AurorisColorPicker> colorPicker) {
    this.waveClientProv = waveClient;
    this.eventBus = eventBus;
    this.waveUnsavedIndicator = waveUnsavedIndicator;
    this.colorPicker = colorPicker;
    // widget = uiBinder.createAndBindUi(this);
    eventBus.addHandler(WaveClientClearEvent.getType(),
        new WaveClientClearEvent.WaveClientClearHandler() {
          @Override
          public void onWaveClientClear(final WaveClientClearEvent event) {
            waveClear();
          }
        });
    onlyWebClient = WindowUtils.getParameter(SiteParameters.ONLY_WEBCLIENT) != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return widget;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * attach()
   */
  @Override
  public void attach() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * clear()
   */
  @Override
  public void clear() {
    onlyViewPanel.setHTML("");
    waveClear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * detach()
   */
  @Override
  public void detach() {
    clear();
  }

  /**
   * Gets the wave ref.
   * 
   * @param waveRefS
   *          the wave ref s
   * @return the wave ref
   */
  protected WaveRef getWaveRef(final String waveRefS) {
    try {
      return GwtWaverefEncoder.decodeWaveRefFromPath(waveRefS);
    } catch (final InvalidWaveRefException e) {
      throw new UIException("Invalid waveref: " + waveRefS);
    }
  }

  /**
   * Inits the wave client if needed.
   */
  protected void initWaveClientIfNeeded() {
    if (channel == null) {
      Log.info("Channel is null so, will create wave in ContentViewerPanel");
      final WaveClientView webClient = waveClientProv.get();
      loading = webClient.getLoading();
      waveHolder = webClient.getWaveHolder();
      dummyWaveFrame = new FramedPanel();
      channel = webClient.getChannel();
      profiles = webClient.getProfiles();
      idGenerator = ClientIdGenerator.create();
    }
  }

  protected void postLoad() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * setContent(cc.kune.core.shared.dto.StateContentDTO)
   */
  @Override
  public void setContent(final StateContentDTO state) {
    onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
    deck.showWidget(1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * setEditableContent(cc.kune.core.shared.dto.StateContentDTO)
   */
  @Override
  public void setEditableContent(final StateContentDTO state) {
    setEditableWaveContent(state.getWaveRef(), false);
    deck.showWidget(0);
  }

  /**
   * Sets the editable wave content.
   * 
   * @param waveRefS
   *          the wave ref s
   * @param isNewWave
   *          the is new wave
   */
  protected void setEditableWaveContent(final String waveRefS, final boolean isNewWave) {
    if (onlyWebClient) {
      return;
    } else {
      final WaveRef waveRef = getWaveRef(waveRefS);

      initWaveClientIfNeeded();
      waveClientProv.get().clear();
      waveClear();

      waveHolderParent.add(waveHolder);

      if (waveClientProv.get() instanceof WebClientMock) {
        // do nothing;
      } else {
        // real wave client
        // Release the display:none.
        // UIObject.setVisible(waveFrame.getElement(), true);
        waveHolder.getElement().appendChild(loading);
        final Element holder = waveHolder.getElement().appendChild(Document.get().createDivElement());
        final CustomStagesProvider wave = new CustomStagesProvider(holder, waveUnsavedIndicator,
            waveHolder, dummyWaveFrame, waveRef, channel, idGenerator, profiles, waveStore, isNewWave,
            org.waveprotocol.box.webclient.client.Session.get().getDomain(), null, eventBus, colorPicker);
        this.wave = wave;
        wave.load(new Command() {
          @Override
          public void execute() {
            loading.removeFromParent();
            postLoad();
          }
        });
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * signIn()
   */
  @Override
  public void signIn() {
    // Do nothing (now)
    // initWaveClientIfNeeded();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * signOut()
   */
  @Override
  public void signOut() {
    channel = NO_CHANNEL;
  }

  /**
   * Wave clear.
   */
  private void waveClear() {
    if (!onlyWebClient) {
      WaveClientUtils.clear(wave, waveHolder, waveHolderParent);
    }
  }

}
