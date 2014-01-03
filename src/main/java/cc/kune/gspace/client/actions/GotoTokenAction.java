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
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.events.RenameContentEvent;
import cc.kune.core.client.events.SocialNetworkChangedEvent;
import cc.kune.core.client.events.SocialNetworkChangedEvent.SocialNetworkChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;

// TODO: Auto-generated Javadoc
/**
 * The Class GotoTokenAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GotoTokenAction extends AbstractExtendedAction {

  /** The rename handler. */
  private final HandlerRegistration renameHandler;

  /** The sn handler. */
  private final HandlerRegistration snHandler;

  /** The state manager. */
  private final StateManager stateManager;

  /** The token. */
  private final StateToken token;

  /**
   * Instantiates a new goto token action.
   * 
   * @param icon
   *          the icon
   * @param name
   *          the name
   * @param tooltip
   *          the tooltip
   * @param token
   *          the token
   * @param style
   *          the style
   * @param stateManager
   *          the state manager
   * @param eventBus
   *          the event bus
   * @param disableCurrent
   *          the disable current
   */
  public GotoTokenAction(final Object icon, final String name, final String tooltip,
      final StateToken token, final String style, final StateManager stateManager,
      final EventBus eventBus, final boolean disableCurrent) {
    super();
    this.token = token;
    this.stateManager = stateManager;
    putValue(Action.NAME, name);
    putValue(Action.STYLES, style);
    if (icon != null) {
      putValue(Action.SMALL_ICON, icon);
    }
    putValue(Action.TOOLTIP, tooltip);
    snHandler = stateManager.onSocialNetworkChanged(true, new SocialNetworkChangedHandler() {
      @Override
      public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
        final boolean weAreInThisState = !token.equals(event.getState().getStateToken());
        putValue(Action.STYLES, disableCurrent ? (weAreInThisState ? style : style
            + ", k-button-disabled") : style);
      }
    });
    renameHandler = eventBus.addHandler(RenameContentEvent.getType(),
        new RenameContentEvent.RenameContentHandler() {
          @Override
          public void onRenameEvent(final RenameContentEvent event) {
            final StateToken eToken = event.getToken();
            if (eToken.equals(token)) {
              putValue(Action.NAME, event.getNewName());
            }
          }
        });
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
    this.stateManager.gotoStateToken(token);
  };

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.AbstractAction#onDettach()
   */
  @Override
  public void onDettach() {
    super.onDettach();
    snHandler.removeHandler();
    renameHandler.removeHandler();
  }
}
