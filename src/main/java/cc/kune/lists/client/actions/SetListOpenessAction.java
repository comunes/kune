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

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.lists.client.rpc.ListsServiceHelper;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SetListOpenessAction extends RolAction {

  public static final String ISPUBLIC = "stla-ispublic";

  private final Provider<ListsServiceHelper> listService;

  @Inject
  public SetListOpenessAction(final Provider<ListsServiceHelper> listService) {
    super(AccessRolDTO.Administrator, true);
    this.listService = listService;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.showProgress();
    final boolean setPublic = !isPublic();
    listService.get().setPublic(setPublic, new SimpleCallback() {
      @Override
      public void onCallback() {
        // Do nothing more
      }
    });
  }

  private Boolean isPublic() {
    return (Boolean) getValue(ISPUBLIC);
  }

}
