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
package cc.kune.gspace.client.options;

import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.gspace.client.options.UserOptionsPresenter.UserOptionsView;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserOptionsPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserOptionsPanel extends AbstractTabbedDialogPanel implements UserOptionsView {

  /** The Constant USER_OP_PANEL_ID. */
  public static final String USER_OP_PANEL_ID = "k-uop-diagpan";

  /** The Constant USER_OP_PANEL_ID_CLOSE. */
  public static final String USER_OP_PANEL_ID_CLOSE = USER_OP_PANEL_ID + "-close";

  /** The Constant USER_OPTIONS_ERROR_ID. */
  public static final String USER_OPTIONS_ERROR_ID = "k-uop-err-mess";

  /**
   * Instantiates a new user options panel.
   * 
   * @param entityHeader
   *          the entity header
   * @param i18n
   *          the i18n
   * @param images
   *          the images
   * @param userOptionsGroup
   *          the user options group
   */
  @Inject
  public UserOptionsPanel(final EntityHeader entityHeader, final I18nTranslationService i18n,
      final NotifyLevelImages images, final UserOptionsCollection userOptionsGroup) {
    super(USER_OP_PANEL_ID, "", false, images, USER_OPTIONS_ERROR_ID, i18n.t("Close"),
        USER_OP_PANEL_ID_CLOSE, null, null, userOptionsGroup, i18n.getDirection());
    super.setIconCls("k-options-icon");
    super.setTitle(i18n.t(CoreMessages.USER_OPTIONS_DIALOG_TITLE));
  }

}
