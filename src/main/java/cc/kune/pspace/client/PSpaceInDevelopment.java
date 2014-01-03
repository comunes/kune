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
package cc.kune.pspace.client;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent.CurrentEntityChangedHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class PSpaceInDevelopment.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PSpaceInDevelopment extends Composite {

  /**
   * The Interface PSpaceInDevelopmentUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface PSpaceInDevelopmentUiBinder extends UiBinder<Widget, PSpaceInDevelopment> {
  }

  /** The ui binder. */
  private static PSpaceInDevelopmentUiBinder uiBinder = GWT.create(PSpaceInDevelopmentUiBinder.class);

  /** The container. */
  @UiField
  FlowPanel container;

  /** The content panel. */
  @UiField
  FlowPanel contentPanel;

  /** The download provider. */
  private final Provider<ClientFileDownloadUtils> downloadProvider;

  /** The entity logo. */
  @UiField
  Image entityLogo;

  /** The entity name. */
  @UiField
  Label entityName;

  /** The header panel. */
  @UiField
  FlowPanel headerPanel;

  /** The images. */
  private final CoreResources images;

  /** The in devel. */
  @UiField
  Label inDevel;

  /** The in devel support. */
  @UiField
  HTMLPanel inDevelSupport;

  /** The main panel. */
  @UiField
  FlowPanel mainPanel;

  /** The photo panel. */
  @UiField
  FlowPanel photoPanel;

  /**
   * Instantiates a new p space in development.
   * 
   * @param stateManager
   *          the state manager
   * @param images
   *          the images
   * @param session
   *          the session
   * @param downloadProvider
   *          the download provider
   * @param i18n
   *          the i18n
   * @param eventBus
   *          the event bus
   */
  @Inject
  public PSpaceInDevelopment(final StateManager stateManager, final CoreResources images,
      final Session session, final Provider<ClientFileDownloadUtils> downloadProvider,
      final I18nTranslationService i18n, final EventBus eventBus) {
    this.images = images;
    this.downloadProvider = downloadProvider;
    initWidget(uiBinder.createAndBindUi(this));
    entityLogo.setSize(FileConstants.LOGO_DEF_SIZE + "px", FileConstants.LOGO_DEF_SIZE + "px");
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setGroupLogo(session.getCurrentState().getGroup(), false);
      }
    });
    inDevel.setText(i18n.t("Right now, the public web space of this group, it's under construction"));
    inDevelSupport.getElement().setInnerHTML(
        i18n.t("[%s] the development",
            TextUtils.generateHtmlLink("http://kune.ourproject.org/join/", i18n.t("Please support"))));
    eventBus.addHandler(CurrentEntityChangedEvent.getType(), new CurrentEntityChangedHandler() {
      @Override
      public void onCurrentLogoChanged(final CurrentEntityChangedEvent event) {
        final GroupDTO group = session.getCurrentState().getGroup();
        setGroupLogo(group, true);
      }
    });
  }

  /**
   * Sets the group logo.
   * 
   * @param group
   *          the group
   * @param noCache
   *          the no cache
   */
  void setGroupLogo(final GroupDTO group, final boolean noCache) {
    setLogoText(group.getLongName());
    if (group.hasLogo()) {
      setLogoImage(group.getStateToken(), noCache);
      setLogoImageVisible(true);
    } else {
      if (group.isPersonal()) {
        showDefUserLogo();
        setLogoImageVisible(true);
      } else {
        setLogoImageVisible(false);
      }
    }
  }

  /**
   * Sets the logo image.
   * 
   * @param stateToken
   *          the state token
   * @param noCache
   *          the no cache
   */
  private void setLogoImage(final StateToken stateToken, final boolean noCache) {
    entityLogo.setUrl(downloadProvider.get().getLogoImageUrl(stateToken.getGroup())
        + (noCache ? UrlParam.noCacheStringSuffix() : ""));
  }

  /**
   * Sets the logo image visible.
   * 
   * @param visible
   *          the new logo image visible
   */
  private void setLogoImageVisible(final boolean visible) {
    entityLogo.setVisible(visible);
  }

  /**
   * Sets the logo text.
   * 
   * @param longName
   *          the new logo text
   */
  private void setLogoText(final String longName) {
    entityName.setText(longName);
  }

  /**
   * Show def user logo.
   */
  public void showDefUserLogo() {
    (AbstractImagePrototype.create(images.unknown60())).applyTo(entityLogo);
  }
}
