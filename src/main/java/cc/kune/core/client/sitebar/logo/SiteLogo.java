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
package cc.kune.core.client.sitebar.logo;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.inject.Inject;

public class SiteLogo extends Composite {

  private final PushButton btn;
  private final Image logo;
  private final Image logoOnOver;

  @Inject
  public SiteLogo(final Session session, final StateManager stateManager,
      final I18nUITranslationService i18n) {
    logo = new Image();
    logoOnOver = new Image();

    btn = new PushButton(logo, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        stateManager.gotoHistoryToken(SiteTokens.HOME);
      }
    });
    btn.setStyleName("k-sitebar-logo");
    btn.addStyleName("k-fr");
    btn.addStyleName("k-pointer");
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        logo.setUrl(event.getInitData().getSiteLogoUrl());
        logoOnOver.setUrl(event.getInitData().getSiteLogoUrlOnOver());
      }
    });
    btn.getUpHoveringFace().setImage(logoOnOver);
    initWidget(btn);
    Tooltip.to(btn, i18n.t("Home page of [%s]", i18n.getSiteCommonName()));
  }

}
