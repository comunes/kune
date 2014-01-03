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

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class NewContainerBtn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewContainerBtn extends ButtonDescriptor {

  /**
   * The Class NewContainerAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class NewContainerAction extends RolAction {

    /** The content service. */
    private final ContentServiceHelper contentService;

    /**
     * Instantiates a new new container action.
     * 
     * @param contentService
     *          the content service
     */
    @Inject
    public NewContainerAction(final ContentServiceHelper contentService) {
      super(AccessRolDTO.Editor, true);
      this.contentService = contentService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      contentService.addContainer((String) getValue(ID), (String) getValue(NEW_NAME));
    }

  }

  /** The Constant BTN_ID. */
  public static final String BTN_ID = "k-newctner-id";

  /** The Constant ID. */
  private static final String ID = "ctnernewid";

  /** The Constant NEW_NAME. */
  private static final String NEW_NAME = "ctnernewname";

  /**
   * Instantiates a new new container btn.
   * 
   * @param i18n
   *          the i18n
   * @param action
   *          the action
   * @param icon
   *          the icon
   * @param title
   *          the title
   * @param tooltip
   *          the tooltip
   * @param newName
   *          the new name
   * @param id
   *          the id
   */
  public NewContainerBtn(final I18nTranslationService i18n, final NewContainerAction action,
      final ImageResource icon, final String title, final String tooltip, final String newName,
      final String id) {
    super(action);
    // The name given to this new content
    action.putValue(NEW_NAME, newName);
    // The type id of the container
    action.putValue(ID, id);
    this.withText(title).withToolTip(tooltip).withIcon(icon).withStyles("k-def-docbtn, k-fl").withId(
        BTN_ID);
  }
}
