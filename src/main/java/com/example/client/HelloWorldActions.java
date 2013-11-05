/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under 
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
package com.example.client;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.sitebar.SiteUserOptions;
import cc.kune.core.client.sitebar.SitebarActions;

import com.google.inject.Inject;

public class HelloWorldActions {

  public class HelloWorldAction extends AbstractExtendedAction {

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.info("Hello world!");
    }

  }

  @Inject
  public HelloWorldActions(final CommonResources res, final SiteUserOptions siteUserOptions) {

    // We can share the action if we don't want to create several (and for
    // instance it doesn't store any value)
    final HelloWorldAction sharedAction = new HelloWorldAction();

    // An action in the sitebar
    final ButtonDescriptor siteBarBtn = new ButtonDescriptor(sharedAction);
    siteBarBtn.withText("HWorld!").withIcon(res.info());
    siteBarBtn.setParent(SitebarActions.LEFT_TOOLBAR);
    siteBarBtn.setStyles(ActionStyles.SITEBAR_STYLE);
    // Other action in the sitebar options menu

    // An action in the user options menu
    final MenuItemDescriptor menuItem = new MenuItemDescriptor(sharedAction);
    menuItem.withText("HWorld!").withIcon(res.alert());
    siteUserOptions.addAction(menuItem);

    // Another action in the SocialNet menu

    // Something added directly in the Skeleton (esto en el Panel)

    // IMPORTANT: If you want to add something in a part a don't find how,
    // please ask! Maybe we need a extension point or we need to document
    // better

    // Do something with Chat

  }
}
