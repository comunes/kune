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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;

import com.google.inject.Inject;

public class MoveContentSimpleAction extends AbstractExtendedAction {

  @Inject
  public MoveContentSimpleAction() {
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.info(I18n.t("If you want to move something, just drag and drop it to its destination"));
  }

}