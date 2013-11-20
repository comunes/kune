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

package cc.kune.core.client.embed;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.shared.utils.Url;
import cc.kune.core.client.sitebar.SitebarSignInLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink;
import cc.kune.core.client.state.Session;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

public class EmbedSitebar {

  private final PopupPanel popup;
  private final ActionFlowPanel toolbar;

  @Inject
  public EmbedSitebar(final Session session, final ActionFlowPanel toolbar,
      final SitebarSignInLink signInLink, final SitebarSignOutLink signOutLink) {
    this.toolbar = toolbar;
    signInLink.detachFromParent();
    signOutLink.detachFromParent();
    @SuppressWarnings("deprecation")
    final String sitelogo = session.getInitData().getSiteLogoUrl();
    toolbar.add(signInLink);
    toolbar.add(signOutLink);
    signInLink.withIcon(new Url(sitelogo));
    signOutLink.withIcon(new Url(sitelogo));
    popup = new PopupPanel(false, false);
    popup.setWidget(toolbar);
    popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      @Override
      public void setPosition(final int offsetWidth, final int offsetHeight) {
        setPopupPosition();
      }
    });
    popup.setAnimationEnabled(false);
    popup.setStyleName("oc-user-msg-popup");
    popup.addStyleName("k-embed-sitebar");
    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(final ResizeEvent event) {
        setPopupPosition();
      }
    });
  }

  private void setPopupPosition() {
    popup.setPopupPosition(Window.getClientWidth() - toolbar.getOffsetWidth() - 20, 0);
  }
}
