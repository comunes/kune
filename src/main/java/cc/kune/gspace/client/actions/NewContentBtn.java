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

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class NewContentBtn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class NewContentBtn extends ButtonDescriptor {

  /** The Constant BTN_ID. */
  public static final String BTN_ID = "k-newctn-id";

  /**
   * Instantiates a new new content btn.
   * 
   * @param i18n
   *          the i18n
   * @param action
   *          the action
   * @param icon
   *          the icon
   * @param shorcutReg
   *          the shorcut reg
   * @param title
   *          the title
   * @param tooltip
   *          the tooltip
   * @param newName
   *          the new name
   * @param id
   *          the id
   */
  public NewContentBtn(final I18nTranslationService i18n, final NewContentAction action,
      final ImageResource icon, final GlobalShortcutRegister shorcutReg, final String title,
      final String tooltip, final String newName, final String id) {
    super(action);
    action.putValue(NewContentAction.NEW_NAME, newName);
    action.putValue(NewContentAction.ID, id);
    this.withText(title).withToolTip(tooltip).withIcon(icon).withStyles("k-def-docbtn, k-fl").withId(
        BTN_ID);
  }
}
