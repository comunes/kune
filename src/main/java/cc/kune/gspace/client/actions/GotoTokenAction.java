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

public class GotoTokenAction extends AbstractExtendedAction {
  private final HandlerRegistration renameHandler;
  private final HandlerRegistration snHandler;
  private final StateManager stateManager;
  private final StateToken token;

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
        boolean weAreInThisState = !token.equals(event.getState().getStateToken());
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

  @Override
  public void actionPerformed(final ActionEvent event) {
    this.stateManager.gotoStateToken(token);
  };

  @Override
  public void onDettach() {
    super.onDettach();
    snHandler.removeHandler();
    renameHandler.removeHandler();
  }
}
