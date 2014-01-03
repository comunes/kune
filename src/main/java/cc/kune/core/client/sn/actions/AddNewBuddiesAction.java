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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.contacts.SimpleContactManager;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;
import cc.kune.core.client.sitebar.search.OnEntitySelectedInSearch;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class AddNewBuddiesAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AddNewBuddiesAction extends AbstractExtendedAction {

  /** The Constant ADD_NEW_BUDDIES_TEXTBOX. */
  public static final String ADD_NEW_BUDDIES_TEXTBOX = "kune-add-newbuddies-tbox";

  /** The search panel. */
  private final EntitySearchPanel searchPanel;

  /**
   * Instantiates a new adds the new buddies action.
   * 
   * @param res
   *          the res
   * @param searchPanel
   *          the search panel
   * @param contactsManager
   *          the contacts manager
   */
  @Inject
  public AddNewBuddiesAction(final IconicResources res, final AddBuddieSearchPanel searchPanel,
      final SimpleContactManager contactsManager) {
    this.searchPanel = searchPanel;
    putValue(Action.NAME, I18n.t("Add a new buddy"));
    putValue(Action.SMALL_ICON, res.add());
    searchPanel.init(true, ADD_NEW_BUDDIES_TEXTBOX, new OnEntitySelectedInSearch() {
      @Override
      public void onSeleted(final String shortName) {
        NotifyUser.askConfirmation(I18n.t("Are you sure?"),
            I18n.t("Do you want to add '[%s]' to your contacts?", shortName),
            new SimpleResponseCallback() {
              @Override
              public void onCancel() {
              }

              @Override
              public void onSuccess() {
                contactsManager.addNewBuddy(shortName);
                NotifyUser.info(I18n.t("Added as buddy in your contacts. If your buddie adds you also, it will be visible in your network"));
              }
            });
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    searchPanel.show();
    searchPanel.focus();
  }

}
