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

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.gspace.client.share.ShareToTheNetView;

import com.google.inject.Provider;

public abstract class AbstractMakeContentVisibleAction extends AbstractToggleShareItemAction {

  private final Provider<ContentServiceHelper> helper;
  private final ContentViewerShareMenu menu;
  private final ShareToTheNetView netView;

  AbstractMakeContentVisibleAction(final boolean value, final Provider<ContentServiceHelper> helper,
      final ContentViewerShareMenu menu, final ShareToTheNetView netView) {
    super(value);
    this.helper = helper;
    this.menu = menu;
    this.netView = netView;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    helper.get().setVisible(value, onSuccess);
    menu.setVisibleToEveryone(value);
    netView.setVisible(value);
  }

}
