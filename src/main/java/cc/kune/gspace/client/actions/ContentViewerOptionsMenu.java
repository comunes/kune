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

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentViewerOptionsMenu.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ContentViewerOptionsMenu extends MenuDescriptor {

  /** The Constant ID. */
  private static final String ID = "k-cnt-viewer-opt-menu";

  /**
   * Instantiates a new content viewer options menu.
   * 
   * @param res
   *          the res
   * @param session
   *          the session
   */
  @Inject
  public ContentViewerOptionsMenu(final CoreResources res, final Session session) {
    super();
    this.withIcon(res.arrowdownsitebar()).withId(ID);
    if (session.isNewbie()) {
      this.withText(I18n.t("More"));
      this.withStyles(ActionStyles.MENU_BTN_STYLE_RIGHT);
    } else {
      this.withStyles(ActionStyles.OPTIONS_MENU_BTN_STYLE_NO_BORDER_RIGHT);
    }
  }

}
