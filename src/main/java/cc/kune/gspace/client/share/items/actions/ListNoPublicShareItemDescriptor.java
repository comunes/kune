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
package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.gspace.client.share.ShareToTheNetView;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;
import cc.kune.lists.client.rpc.ListsServiceHelper;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ListNoPublicShareItemDescriptor extends ShareItemDescriptor {
  public static class MakeListPublicAction extends AbstractMakeListPublicAction {
    @Inject
    MakeListPublicAction(final Provider<ListsServiceHelper> helper, final ContentViewerShareMenu menu,
        final ShareToTheNetView netView) {
      super(true, helper, menu, netView);
    }
  }

  @Singleton
  public static class MakeListPublicMenuItem extends AbstractToggleShareMenuItem {
    @Inject
    public MakeListPublicMenuItem(final MakeListPublicAction action, final IconicResources icons) {
      super(action);
      withIcon(icons.world()).withText(I18n.t("Do this public to anyone"));
    }
  }

  @Inject
  public ListNoPublicShareItemDescriptor(final IconicResources icons,
      final MakeListPublicMenuItem makeListPublic) {
    super(icons.noWorld(), I18n.t("Nobody else"), I18n.t("can't be member"), makeListPublic);
  }
}