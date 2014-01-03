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

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.armor.GSpaceCenter;
import cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView;
import cc.kune.wave.client.CustomSavedStateIndicator;
import cc.kune.wave.client.kspecific.AurorisColorPicker;
import cc.kune.wave.client.kspecific.WaveClientProvider;
import cc.kune.wave.client.kspecific.WaveClientUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The Class ContentViewerPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentViewerPanel extends WaveViewerPanel implements ContentViewerView {

  /**
   * The Interface ContentViewerPanelUiBinder.
   * 
   */
  interface ContentViewerPanelUiBinder extends UiBinder<Widget, ContentViewerPanel> {
  }

  /** The ui binder. */
  private static ContentViewerPanelUiBinder uiBinder = GWT.create(ContentViewerPanelUiBinder.class);

  /** The capabilities registry. */
  private final ContentCapabilitiesRegistry capabilitiesRegistry;

  /** The content title. */
  private final ContentTitleWidget contentTitle;
  /** The drop controller. */
  private final ContentDropController dropController;
  /** The gs armor. */
  private final GSpaceArmor gsArmor;

  /** The state manager. */
  private final StateManager stateManager;

  /** The widget. */
  private final Widget widget;

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
  @Inject
  public ContentViewerPanel(final GSpaceArmor wsArmor, final WaveClientProvider waveClient,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final I18nTranslationService i18n,
      final EventBus eventBus, final StateManager stateManager,
      final ContentDropController dropController, final CustomSavedStateIndicator waveUnsavedIndicator,
      final Provider<AurorisColorPicker> colorPicker) {
    super(waveClient, eventBus, waveUnsavedIndicator, colorPicker);
    this.gsArmor = wsArmor;
    this.capabilitiesRegistry = capabilitiesRegistry;
    this.stateManager = stateManager;
    this.dropController = dropController;
    widget = uiBinder.createAndBindUi(this);
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistry());
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
    final GSpaceCenter docContainer = gsArmor.getDocContainer();
    docContainer.add(widget);
    docContainer.showWidget(widget);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * blinkTitle()
   */
  @Override
  public void blinkTitle() {
    contentTitle.highlightTitle();
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
    gsArmor.getSubheaderToolbar().clear();
    gsArmor.getDocFooterToolbar().clear();
    gsArmor.getDocContainer().clear();
    UiUtils.clear(gsArmor.getDocHeader());
    super.clear();
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * getEditTitle()
   */
  @Override
  public HasEditHandler getEditTitle() {
    return contentTitle.getEditableTitle();
  }

  /**
   * Inits the wave client if needed.
   */
  @Override
  protected void initWaveClientIfNeeded() {
    if (channel == null) {
      super.initWaveClientIfNeeded();
      stateManager.addBeforeStateChangeListener(new BeforeActionListener() {
        @Override
        public boolean beforeAction() {
          // This fix lot of problems when you are editing and move to other
          // location (without stop editing)
          Log.info("Before change history, clear wave");
          WaveClientUtils.clear(wave, waveHolder, waveHolderParent);
          return true;
        }
      });
    }
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
    final boolean editable = state.getContentRights().isEditable();
    if (editable) {
      dropController.setTarget(state.getStateToken());
    }
    gsArmor.enableCenterScroll(true);
    setTitle(state, editable);
    super.setContent(state);
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
    gsArmor.enableCenterScroll(false);
    dropController.setTarget(state.getStateToken());
    setTitle(state, true);
    super.setEditableContent(state);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * setEditableTitle(java.lang.String)
   */
  @Override
  public void setEditableTitle(final String title) {
    contentTitle.setText(title);
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
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * setFooterActions
   * (cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection)
   */
  @Override
  public void setFooterActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getDocFooterToolbar());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView#
   * setSubheaderActions
   * (cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection)
   */
  @Override
  public void setSubheaderActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getSubheaderToolbar());
  }

  /**
   * Sets the title.
   * 
   * @param state
   *          the state
   * @param editable
   *          the editable
   */
  private void setTitle(final StateContentDTO state, final boolean editable) {
    contentTitle.setTitle(state.getTitle(), state.getTypeId(), state.getMimeType(), editable
        && capabilitiesRegistry.isRenamable(state.getTypeId()));
    Window.setTitle(state.getGroup().getLongName() + ": " + state.getTitle());
  }

  /**
   * Sets the toolbar actions.
   * 
   * @param actions
   *          the actions
   * @param toolbar
   *          the toolbar
   */
  private void setToolbarActions(final GuiActionDescCollection actions, final IsActionExtensible toolbar) {
    toolbar.clear();
    toolbar.addAll(actions);
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
    super.signIn();
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
    super.signOut();
  }

}
