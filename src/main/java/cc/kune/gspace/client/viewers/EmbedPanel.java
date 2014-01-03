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

import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView;
import cc.kune.wave.client.CustomSavedStateIndicator;
import cc.kune.wave.client.kspecific.AurorisColorPicker;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class EmbedPanel the panel of the embed component.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmbedPanel extends WaveViewerPanel implements EmbedView {

  /**
   * The Interface EmbedPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface EmbedPanelUiBinder extends UiBinder<Widget, EmbedPanel> {
  }

  /** The ui binder. */
  private static EmbedPanelUiBinder uiBinder = GWT.create(EmbedPanelUiBinder.class);

  /** The widget. */
  private final Widget widget;

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView#
   * setContent(cc.kune.core.shared.dto.StateContentDTO)
   */

  /**
   * Instantiates a new content viewer panel.
   * 
   * @param waveClient
   *          the wave client
   * @param eventBus
   *          the event bus
   * @param waveUnsavedIndicator
   *          the wave unsaved indicator
   * @param colorPicker
   *          the color picker
   */
  @Inject
  public EmbedPanel(final WaveClientProvider waveClient, final EventBus eventBus,
      final CustomSavedStateIndicator waveUnsavedIndicator,
      final Provider<AurorisColorPicker> colorPicker) {
    super(waveClient, eventBus, waveUnsavedIndicator, colorPicker);
    widget = uiBinder.createAndBindUi(this);
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
   * @see cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView# attach()
   */
  @Override
  public void attach() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView# clear()
   */
  @Override
  public void clear() {
    super.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView# detach()
   */
  @Override
  public void detach() {
    clear();
  }

  /**
   * Inits the wave client if needed.
   */
  @Override
  protected void initWaveClientIfNeeded() {
    if (channel == null) {
      super.initWaveClientIfNeeded();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.WaveViewerPanel#postLoad()
   */
  @Override
  protected void postLoad() {
    restyleWavePanel();
  }

  /**
   * Restyle wave panel (dirty hack to style wave panel).
   * $wnd.jQuery("div[class*='ParticipantsViewBuilder-Css-panel']"
   * ).css("border", "0px");
   */
  public native void restyleWavePanel() /*-{
                                        $wnd.jQuery("button[class*='ParticipantsViewBuilder-Css-addMessage']")
                                        .hide();
                                        $wnd.jQuery("div[class*='ToplevelToolbarWidget-Css-toolbar']").css(
                                        "background", "none repeat scroll 0 0 #FFFFFF");
                                        }-*/;

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.WaveViewerPanel#setContent(cc.kune.core.shared
   * .dto.StateContentDTO)
   */
  @Override
  public void setContent(final StateContentDTO state) {
    super.setContent(state);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView#
   * setEditableContent(cc.kune.core.shared.dto.StateContentDTO)
   */
  @Override
  public void setEditableContent(final StateContentDTO state) {
    super.setEditableContent(state);
  }

  /**
   * Sets the editable wave content.
   * 
   * @param waveRefS
   *          the wave ref s
   * @param isNewWave
   *          the is new wave
   */
  @Override
  protected void setEditableWaveContent(final String waveRefS, final boolean isNewWave) {
    super.setEditableWaveContent(waveRefS, isNewWave);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView# signIn()
   */
  @Override
  public void signIn() {
    // Do nothing (now)
    super.signIn();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.EmbedPresenter.EmbedView# signOut()
   */
  @Override
  public void signOut() {
    super.signOut();
  }
}
