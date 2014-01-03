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
package cc.kune.blogs.client.actions;

import cc.kune.blogs.shared.BlogsToolConstants;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.gspace.client.actions.NewContentAction;
import cc.kune.gspace.client.actions.NewContentBtn;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class NewPostIconBtn.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewPostIconBtn extends NewContentBtn {

  /**
   * Instantiates a new new post icon btn.
   *
   * @param i18n the i18n
   * @param action the action
   * @param res the res
   * @param shorcutReg the shorcut reg
   */
  @Inject
  public NewPostIconBtn(final I18nTranslationService i18n, final NewContentAction action,
      final IconicResources res, final GlobalShortcutRegister shorcutReg) {
    super(i18n, action, res.blogPostAdd(), shorcutReg, i18n.t("New post"),
        i18n.t("Create a new blog post"), i18n.t("New post"), BlogsToolConstants.TYPE_POST);
    withStyles("k-btn-min, k-fl");
  }

}
