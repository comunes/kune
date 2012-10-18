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
package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;

public class SubscribeToListBtn extends ButtonDescriptor {

  public static final String ID = "k-list-subs";

  @Inject
  public SubscribeToListBtn(final I18nTranslationService i18n, final SubscriteToListAction action,
      final Session session, final IconicResources res) {
    super(action);
    final Boolean areYouMember = session.isLogged()
        && session.getContainerState().getAccessLists().getEditors().getList().contains(
            session.getCurrentUserInfo().getUserGroup());
    action.putValue(SubscriteToListAction.ISMEMBER, areYouMember);
    withStyles("k-fl");
    if (!areYouMember) {
      withText(i18n.t("Subscribe"));
      withIcon(res.add());
      withToolTip(i18n.t("Subscribe to this list"));
    } else {
      withText(i18n.t("Unsubscribe"));
      withIcon(res.del());
      withToolTip(i18n.t("Unsubscribe to this list"));
    }
    withId(ID);
  }
}
