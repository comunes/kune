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

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class NewContentMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewContentMenuItem extends MenuItemDescriptor {

  /**
   * Instantiates a new new content menu item.
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
   * @param parent
   *          the parent
   */
  public NewContentMenuItem(final I18nTranslationService i18n, final NewContentAction action,
      final ImageResource icon, final GlobalShortcutRegister shorcutReg, final String title,
      final String tooltip, final String newName, final String id, @Nonnull final MenuDescriptor parent) {
    super(parent, false, action);
    // The name given to this new content
    action.putValue(NewContentAction.NEW_NAME, newName);
    action.putValue(NewContentAction.ID, id);
    // final KeyStroke shortcut = Shortcut.getShortcut(false, true, false,
    // false, Character.valueOf('N'));
    // shorcutReg.put(shortcut, action);
    this.withText(title).withToolTip(tooltip).withIcon(icon).withStyles("k-def-docbtn, k-fr");
  }

}
