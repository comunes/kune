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
package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowSubscribersOfListBtn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShowSubscribersOfListBtn extends ButtonDescriptor {

  /**
   * Instantiates a new show subscribers of list btn.
   * 
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param res
   *          the res
   */
  @Inject
  public ShowSubscribersOfListBtn(final I18nTranslationService i18n, final Session session,
      final CoreResources res) {
    super(AbstractAction.NO_ACTION);
    final int subscribers = session.getContainerState().getAccessLists().getEditors().getList().size();
    final int posts = session.getContainerState().getContainer().getContents().size();
    withText(i18n.t("[%d] subscribed, [%d] posts", subscribers, posts));
    withStyles("k-def-docbtn, " + ActionStyles.MENU_BTN_STYLE_NO_BORDER_LEFT);
  }
}
