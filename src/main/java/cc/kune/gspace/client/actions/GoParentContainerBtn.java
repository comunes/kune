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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GoParentContainerBtn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GoParentContainerBtn extends ButtonDescriptor {

  /**
   * The Class GoParentContainerAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class GoParentContainerAction extends AbstractExtendedAction {

    /** The session. */
    private final Session session;

    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new go parent container action.
     * 
     * @param session
     *          the session
     * @param stateManager
     *          the state manager
     */
    @Inject
    public GoParentContainerAction(final Session session, final StateManager stateManager) {
      this.session = session;
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
      NotifyUser.showProgress();
      StateToken stateToken;
      final StateAbstractDTO state = session.getCurrentState();
      if (state instanceof StateContentDTO) {
        stateToken = ((StateContentDTO) state).getContainer().getStateToken();
      } else {
        final ContainerDTO container = ((StateContainerDTO) state).getContainer();
        stateToken = container.getParentToken();
      }
      stateManager.gotoStateToken(stateToken);
      // NotifyUser.hideProgress();
    }

  }

  /** The Constant GO_PARENT_ID. */
  public static final String GO_PARENT_ID = "k-goparent-btn-id";

  /**
   * Instantiates a new go parent container btn.
   * 
   * @param i18n
   *          the i18n
   * @param action
   *          the action
   * @param res
   *          the res
   * @param session
   *          the session
   * @param typeRoot
   *          the type root
   */
  public GoParentContainerBtn(final I18nTranslationService i18n, final GoParentContainerAction action,
      final IconicResources res, final Session session, final String typeRoot) {
    super(action);
    this.withToolTip(i18n.t("Go up: Open the container folder")).withIcon(res.leftArrow()).withStyles(
        "k-btn-min, k-fl, k-btn-go-up");
    this.withId(GO_PARENT_ID);
    final StateAbstractDTO state = session.getCurrentState();
    if (!session.isCurrentStateAContent()) {
      final StateContainerDTO stateContainer = (StateContainerDTO) state;
      if (stateContainer.getTypeId().equals(typeRoot)) {
        setEnabled(false);
      }
    }
  }

}
