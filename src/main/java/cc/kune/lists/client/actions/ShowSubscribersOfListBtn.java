/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;

public class ShowSubscribersOfListBtn extends ButtonDescriptor {

  @Inject
  public ShowSubscribersOfListBtn(final I18nTranslationService i18n, final Session session,
      final CoreResources res) {
    super(AbstractAction.NO_ACTION);
    final int subscribers = session.getContainerState().getAccessLists().getEditors().getList().size();
    final int posts = session.getContainerState().getContainer().getContents().size();
    withText(i18n.t("[%d] subscribed, [%d] posts", subscribers, posts));
    // withToolTip(i18n.t("Subscribe to this list"));
    withStyles("k-def-docbtn, k-fl, k-noborder, k-no-backimage, k-nobackcolor");
  }
}
