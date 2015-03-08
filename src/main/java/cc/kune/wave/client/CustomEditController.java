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

package cc.kune.wave.client;

import org.waveprotocol.wave.client.editor.Editor;
import org.waveprotocol.wave.client.wavepanel.WavePanel;
import org.waveprotocol.wave.client.wavepanel.impl.WavePanelImpl;
import org.waveprotocol.wave.client.wavepanel.impl.edit.Actions;
import org.waveprotocol.wave.client.wavepanel.impl.edit.EditSession;
import org.waveprotocol.wave.client.wavepanel.impl.focus.FocusBlipSelector;
import org.waveprotocol.wave.client.wavepanel.impl.focus.FocusFramePresenter;
import org.waveprotocol.wave.client.wavepanel.view.BlipView;

import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.shortcuts.GlobalShortcutsInstance;

public class CustomEditController implements EditSession.Listener, WavePanel.LifecycleListener,
FocusFramePresenter.Listener, CustomEditToolbarImpl.Listener {

  public static void install(final FocusFramePresenter focus, final Actions actions,
      final EditSession edit, final WavePanelImpl panel, final FocusBlipSelector blipSelector,
      final CustomEditToolbar editToolbar) {
    new CustomEditController(focus, actions, edit, panel, blipSelector, editToolbar);

  }
  private final Actions actions;
  private final FocusBlipSelector blipSelector;

  private final EditSession edit;
  private final CustomEditToolbar editToolbar;

  private final FocusFramePresenter focus;
  private final WavePanelImpl panel;
  BlipView rootBlip;
  private GlobalShortcutRegister globalShortcuts;

  public CustomEditController(final FocusFramePresenter focus, final Actions actions,
      final EditSession edit, final WavePanelImpl panel, final FocusBlipSelector blipSelector,
      final CustomEditToolbar editToolbar) {
    this.focus = focus;
    this.actions = actions;

    this.edit = edit;
    this.panel = panel;
    this.blipSelector = blipSelector;
    this.editToolbar = editToolbar;
    this.edit.addListener(this);
    this.panel.addListener(this);
    editToolbar.setListener(this);
    editToolbar.setEditAndReplyVisible(true);
    editToolbar.setEditDoneVisible(false);
    globalShortcuts = GlobalShortcutsInstance.get();
  }

  private BlipView getFocusedOrRoot() {
    BlipView focusedBlip = focus.getFocusedBlip();
    if (focusedBlip == null) {
      focusedBlip = getRootBlip();
    }
    return focusedBlip;
  }

  private BlipView getRootBlip() {
    if (rootBlip == null) {
      rootBlip = blipSelector.getOrFindRootBlip();
    }
    return rootBlip;
  }

  @Override
  public void onEdit() {
    actions.startEditing(getFocusedOrRoot());
    globalShortcuts.disable();
  }

  @Override
  public void onEditDone() {
    actions.stopEditing();
    globalShortcuts.enable();
  }

  @Override
  public void onFocusMoved(final BlipView oldBlip, final BlipView newBlip) {
  }

  @Override
  public void onInit() {
    // Showing a wave
    editToolbar.setEditAndReplyVisible(true);
    editToolbar.setEditDoneVisible(false);
  }

  @Override
  public void onReply() {
    final BlipView focusedBlip = focus.getFocusedBlip();
    final BlipView rootBlip = getRootBlip();
    final BlipView currentBlip = getFocusedOrRoot();
    if (focusedBlip == null) {
      actions.addContinuation(currentBlip.getParent());
    } else {
      if (focusedBlip.equals(rootBlip)) {
        actions.addContinuation(rootBlip.getParent());
      } else {
        actions.addContinuation(currentBlip.getParent());
      }
    }
  }

  @Override
  public void onReset() {
    // Not showing a wave
    editToolbar.setEditAndReplyVisible(false);
    editToolbar.setEditDoneVisible(false);
  }

  @Override
  public void onSessionEnd(final Editor editor, final BlipView blipView) {
    // On edit session end
    editToolbar.setEnable(true);
    editToolbar.setEditDoneVisible(false);
    globalShortcuts.enable();
  }

  @Override
  public void onSessionStart(final Editor editor, final BlipView blipView) {
    // On edit session start
    editToolbar.setEnable(false);
    editToolbar.setEditDoneVisible(true);
    globalShortcuts.disable();
  }
}
