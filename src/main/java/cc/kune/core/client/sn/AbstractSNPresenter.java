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
package cc.kune.core.client.sn;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sn.actions.registry.AbstractSNMembersActionsRegistry;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

public abstract class AbstractSNPresenter<V extends View, Proxy_ extends Proxy<?>> extends
    Presenter<View, Proxy<?>> {

  private static final int MAX_NUM_AVATAR_IN_A_ROW = 6;

  protected final Provider<ClientFileDownloadUtils> downloadProvider;

  public AbstractSNPresenter(final EventBus eventBus, final View view, final Proxy<?> proxy,
      final Provider<ClientFileDownloadUtils> downloadProvider) {
    super(eventBus, view, proxy);
    this.downloadProvider = downloadProvider;
  }

  protected boolean areMany(final int numAvatars) {
    return numAvatars > MAX_NUM_AVATAR_IN_A_ROW;
  }

  protected GuiActionDescCollection createMenuItems(final Object target,
      final AbstractSNMembersActionsRegistry registry, final String title) {
    final GuiActionDescCollection items = new GuiActionDescCollection();
    items.add(new MenuTitleItemDescriptor(title));
    for (final Provider<MenuItemDescriptor> provider : registry) {
      final MenuItemDescriptor menuItem = provider.get();
      menuItem.setTarget(target);
      items.add(menuItem);
    }
    return items;
  }

  protected String getAvatar(final GroupDTO group) {
    return downloadProvider.get().getGroupLogo(group);
  }

}
