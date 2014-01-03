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

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.gspace.client.options.GroupOptionsPresenter.GroupOptionsView;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptionsPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptionsPanel extends AbstractTabbedDialogPanel implements GroupOptionsView {

  /** The Constant GROUP_OP_PANEL_ID. */
  public static final String GROUP_OP_PANEL_ID = "k-gop-diagpan";

  /** The Constant GROUP_OP_PANEL_ID_CLOSE. */
  public static final String GROUP_OP_PANEL_ID_CLOSE = GROUP_OP_PANEL_ID + "-close";

  /** The Constant GROUP_OPTIONS_ERROR_ID. */
  public static final String GROUP_OPTIONS_ERROR_ID = "k-gop-err-mess";

  /** The entity header. */
  private final EntityHeader entityHeader;

  /**
   * Instantiates a new group options panel.
   * 
   * @param entityHeader
   *          the entity header
   * @param i18n
   *          the i18n
   * @param images
   *          the images
   * @param entityOptionsGroup
   *          the entity options group
   */
  @Inject
  public GroupOptionsPanel(final EntityHeader entityHeader, final I18nTranslationService i18n,
      final NotifyLevelImages images, final GroupOptionsCollection entityOptionsGroup) {
    super(GROUP_OP_PANEL_ID, "", false, false, images, GROUP_OPTIONS_ERROR_ID, i18n.t("Close"),
        GROUP_OP_PANEL_ID_CLOSE, null, null, entityOptionsGroup, i18n.getDirection());
    this.entityHeader = entityHeader;
    super.setIconCls("k-options-icon");
    super.setTitle(i18n.t(CoreMessages.GROUP_OPTIONS_DIALOG_TITLE));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.GroupOptionsPresenter.GroupOptionsView#addAction
   * (cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public void addAction(final GuiActionDescrip descriptor) {
    entityHeader.addAction(descriptor);
  }

}
