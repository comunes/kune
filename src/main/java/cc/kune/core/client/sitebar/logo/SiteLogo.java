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
package cc.kune.core.client.sitebar.logo;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class SiteLogo.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SiteLogo extends Composite {

  /** The btn. */
  private final PushButton btn;

  /** The logo. */
  private final Image logo;

  /** The logo on over. */
  private final Image logoOnOver;

  /**
   * Instantiates a new site logo.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   */
  @Inject
  public SiteLogo(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n) {
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
