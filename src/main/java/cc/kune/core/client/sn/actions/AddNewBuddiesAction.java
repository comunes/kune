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
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.SitebarSearchPresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AddNewBuddiesAction extends AbstractExtendedAction {

  private final I18nTranslationService i18n;
  private final Provider<SitebarSearchPresenter> searcher;

  @Inject
  public AddNewBuddiesAction(final I18nTranslationService i18n, final CoreResources res,
      final Provider<SitebarSearchPresenter> searcher) {
    this.i18n = i18n;
    this.searcher = searcher;
    putValue(Action.NAME, i18n.t("Add a new buddy"));
    putValue(Action.SMALL_ICON, res.addGreen());
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.info(i18n.t("Search the user you want to add and in his/her homepage click '"
        + CoreMessages.ADD_AS_A_BUDDY + "'"));
    searcher.get().focus();
  }

}
