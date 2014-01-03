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
package cc.kune.core.client.ws.entheader;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityHeaderPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityHeaderPanel extends ViewImpl implements EntityHeaderView {

  /** The download provider. */
  private final Provider<ClientFileDownloadUtils> downloadProvider;

  /** The entity text logo. */
  private final EntityTextLogo entityTextLogo;

  /** The images. */
  private final CoreResources images;

  /** The main panel. */
  private final HorizontalPanel mainPanel;

  /** The toolbar. */
  private final ActionFlowPanel toolbar;

  /** The vpanel. */
  private final VerticalPanel vpanel;

  /**
   * Instantiates a new entity header panel.
   * 
   * @param downloadProvider
   *          the download provider
   * @param images
   *          the images
   * @param bindings
   *          the bindings
   * @param armor
   *          the armor
   * @param entityTextLogo
   *          the entity text logo
   * @param i18n
   *          the i18n
   */
  @Inject
  public EntityHeaderPanel(final Provider<ClientFileDownloadUtils> downloadProvider,
      final CoreResources images, final GuiProvider bindings, final GSpaceArmor armor,
      final EntityTextLogo entityTextLogo, final I18nTranslationService i18n) {
    this.entityTextLogo = entityTextLogo;
    mainPanel = new HorizontalPanel();
    mainPanel.setWidth("100%");
    this.downloadProvider = downloadProvider;
    this.images = images;
    vpanel = new VerticalPanel();
    vpanel.setWidth("100%");
    vpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    mainPanel.add(entityTextLogo);
    toolbar = new ActionFlowPanel(bindings, i18n);
    vpanel.add(toolbar);
    mainPanel.add(vpanel);
    armor.getEntityHeader().add(mainPanel);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #addAction(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public void addAction(final GuiActionDescrip descriptor) {
    toolbar.add(descriptor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #addWidget(com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public void addWidget(final IsWidget view) {
    final Widget widget = (Widget) view;
    vpanel.add(widget);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return mainPanel;
  }

  /**
   * Sets the full logo.
   * 
   * @param stateToken
   *          the state token
   * @param clipped
   *          the clipped
   */
  @Deprecated
  public void setFullLogo(final StateToken stateToken, final boolean clipped) {
    mainPanel.clear();
    final String imageUrl = downloadProvider.get().getImageUrl(stateToken);
    Image logo;
    if (clipped) {
      logo = new Image(imageUrl, 0, 0, FileConstants.LOGO_DEF_WIDTH, FileConstants.LOGO_DEF_SIZE);
    } else {
      logo = new Image(imageUrl);
      logo.setWidth(String.valueOf(FileConstants.LOGO_DEF_WIDTH));
      logo.setHeight(String.valueOf(FileConstants.LOGO_DEF_SIZE));
    }
    mainPanel.add(logo);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setLargeFont()
   */
  @Override
  public void setLargeFont() {
    entityTextLogo.setLargeFont();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setLogoImage(cc.kune.core.shared.dto.GroupDTO, boolean)
   */
  @Override
  public void setLogoImage(final GroupDTO group, final boolean noCache) {
    final String url = downloadProvider.get().getLogoImageUrl(group.getShortName(), noCache);
    entityTextLogo.setLogoImage(url);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setLogoImageVisible(boolean)
   */
  @Override
  public void setLogoImageVisible(final boolean visible) {
    entityTextLogo.setLogoVisible(visible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setLogoText(java.lang.String)
   */
  @Override
  public void setLogoText(final String groupName) {
    entityTextLogo.setLogoText(groupName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setMediumFont()
   */
  @Override
  public void setMediumFont() {
    entityTextLogo.setMediumFont();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setOnlineStatusGroup(java.lang.String)
   */
  @Override
  public void setOnlineStatusGroup(final String group) {
    entityTextLogo.setOnlineStatusGroup(group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setOnlineStatusVisible(boolean)
   */
  @Override
  public void setOnlineStatusVisible(final boolean visible) {
    entityTextLogo.setOnlineStatusVisible(visible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #setSmallFont()
   */
  @Override
  public void setSmallFont() {
    entityTextLogo.setSmallFont();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView
   * #showDefUserLogo()
   */
  @Override
  public void showDefUserLogo() {
    entityTextLogo.setLogoImage(AbstractImagePrototype.create(images.unknown60()));
  }

}
