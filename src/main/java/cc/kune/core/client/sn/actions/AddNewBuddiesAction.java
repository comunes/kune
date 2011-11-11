/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;
import cc.kune.core.client.sitebar.search.OnEntitySelectedInSearch;
import cc.kune.core.client.sn.SimpleContactManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class AddNewBuddiesAction extends AbstractExtendedAction {

  private final EntitySearchPanel searchPanel;

  @Inject
  public AddNewBuddiesAction(final I18nTranslationService i18n, final CoreResources res,
      final AddBuddieSearchPanel searchPanel, final SimpleContactManager contactsManager) {
    this.searchPanel = searchPanel;
    putValue(Action.NAME, i18n.t("Add a new buddy"));
    putValue(Action.SMALL_ICON, res.addGreen());
    searchPanel.init(new OnEntitySelectedInSearch() {
      @Override
      public void onSeleted(final String shortName) {
        NotifyUser.askConfirmation(i18n.t("Are you sure?"),
            i18n.t("Do you want to add '[%s]' to your contacts?", shortName),
            new SimpleResponseCallback() {
              @Override
              public void onCancel() {
              }

              @Override
              public void onSuccess() {
                contactsManager.addNewBuddy(shortName);
                NotifyUser.info("Added as buddy in your contacts. If your buddie adds you also, it will be visible in your network");
              }
            });
      }
    }, true);
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    searchPanel.show();
    searchPanel.focus();
  }

}
