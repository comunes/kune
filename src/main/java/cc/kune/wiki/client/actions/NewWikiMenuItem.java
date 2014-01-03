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
package cc.kune.wiki.client.actions;

import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.gspace.client.actions.NewContentAction;
import cc.kune.gspace.client.actions.NewContentMenuItem;
import cc.kune.wiki.shared.WikiToolConstants;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class NewWikiMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewWikiMenuItem extends NewContentMenuItem {

  /**
   * Instantiates a new new wiki menu item.
   * 
   * @param i18n
   *          the i18n
   * @param action
   *          the action
   * @param res
   *          the res
   * @param shorcutReg
   *          the shorcut reg
   * @param wikiFolderNewMenu
   *          the wiki folder new menu
   */
  @Inject
  public NewWikiMenuItem(final I18nTranslationService i18n, final NewContentAction action,
      final IconicResources res, final GlobalShortcutRegister shorcutReg,
      final WikiFolderNewMenu wikiFolderNewMenu) {
    super(
        i18n,
        action,
        res.wikisAdd(),
        shorcutReg,
        i18n.t("New wikipage"),
        i18n.t("Create a New Wikipage here. "
            + "If you choose to publish it, this document will appear as a new 'Page' in the public web"),
        i18n.t("New wikipage"), WikiToolConstants.TYPE_WIKIPAGE, wikiFolderNewMenu.get());
  }

}
