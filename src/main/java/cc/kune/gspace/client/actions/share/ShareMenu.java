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
package cc.kune.gspace.client.actions.share;

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.actions.MenuLoggedDescriptor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ShareMenu extends MenuLoggedDescriptor {

  public static final String ID = "k-cnt-viewer-share-menu";
  private final IconicResources icons;

  @Inject
  public ShareMenu(final IconicResources res, final I18nTranslationService i18n,
      final AccessRightsClientManager rightsManager, final StateManager stateManager) {
    super(rightsManager);
    this.icons = res;
    this.withText(i18n.t("Share")).withToolTip(i18n.t("Share this with group members, etc")).withIcon(
        res.world()).withStyles(ActionStyles.MENU_BTN_STYLE_LEFT).withId(ID);
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        if (state instanceof StateContentDTO) {
          final StateContentDTO cnt = (StateContentDTO) state;
          setShareIconAndText(cnt.getAccessLists());
        } else if (state instanceof StateContainerDTO) {
          final StateContainerDTO cnt = (StateContainerDTO) state;
          setShareIconAndText(cnt.getAccessLists());
        }
      }

      private void setShareIconAndText(final AccessListsDTO acl) {
        final String mode = acl.getViewers().getMode();
        final boolean anyone = GroupListMode.valueOf(mode).equals(GroupListMode.EVERYONE);
        setVisibleToEveryone(anyone);
      }
    });

  }

  public void setVisibleToEveryone(final Boolean visible) {
    this.withIcon(visible ? icons.world() : icons.noWorld());
    withToolTip(visible ? I18n.t("This is visible to everyone. More share options")
        : I18n.t("This is not visible to everyone. Share this!"));
  }

}
