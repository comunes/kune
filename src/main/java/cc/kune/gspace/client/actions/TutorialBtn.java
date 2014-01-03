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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.viewers.TutorialViewer.OnTutorialClose;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class TutorialBtn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class TutorialBtn extends ButtonDescriptor {

  /**
   * The Class ShowTutorialAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @Singleton
  public static class ShowTutorialAction extends RolAction {

    /** The bus. */
    private final EventBus bus;

    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new show tutorial action.
     * 
     * @param eventBus
     *          the event bus
     * @param stateManager
     *          the state manager
     */
    @Inject
    public ShowTutorialAction(final EventBus eventBus, final StateManager stateManager) {
      super(AccessRolDTO.Editor, true);
      this.bus = eventBus;
      this.stateManager = stateManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      ShowHelpContainerEvent.fire(bus, new OnTutorialClose() {
        @Override
        public void onClose() {
          stateManager.refreshCurrentState();
        }
      });
    }

  }

  /** The Constant INFO_CONTAINER_ID. */
  public static final String INFO_CONTAINER_ID = "k-container-info-id";

  /**
   * Instantiates a new tutorial btn.
   * 
   * @param i18n
   *          the i18n
   * @param action
   *          the action
   * @param res
   *          the res
   */
  @Inject
  public TutorialBtn(final I18nTranslationService i18n, final ShowTutorialAction action,
      final IconicResources res) {
    super(action);
    this.withToolTip(i18n.t("New to this tool? Here there is some help")).withIcon(res.info()).withStyles(
        "k-btn-min, k-fr");
    this.withId(INFO_CONTAINER_ID);
  }

}
