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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.StateManager;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GotoGroupAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GotoGroupAction extends AbstractExtendedAction {

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new goto group action.
   * 
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   */
  @Inject
  public GotoGroupAction(final StateManager stateManager, final I18nTranslationService i18n,
      final IconicResources res) {
    this.stateManager = stateManager;
    putValue(NAME, i18n.t("Visit this group's homepage"));
    putValue(Action.SMALL_ICON, res.home());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    stateManager.gotoStateToken(EventTargetUtils.getTargetToken(event));
  }

}
