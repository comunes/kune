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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.FolderContainerDropController;
import cc.kune.core.client.dnd.FolderContentDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class FolderViewerAsFlowPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FolderViewerAsFlowPanel extends AbstractFolderViewerPanel {

  /**
   * The Interface FolderViewerAsFlowPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface FolderViewerAsFlowPanelUiBinder extends UiBinder<Widget, FolderViewerAsFlowPanel> {
  }

  /** The Constant ICONLABELMAXSIZE. */
  private static final int ICONLABELMAXSIZE = 20;

  /** The Constant ICONSIZE. */
  private static final int ICONSIZE = 100;

  /** The ui binder. */
  private static FolderViewerAsFlowPanelUiBinder uiBinder = GWT.create(FolderViewerAsFlowPanelUiBinder.class);

  /** The flow. */
  @UiField
  FlowPanel flow;

  /**
   * Instantiates a new folder viewer as flow panel.
   * 
   * @param gsArmor
   *          the gs armor
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param capabilitiesRegistry
   *          the capabilities registry
   * @param dragController
   *          the drag controller
   * @param contentDropControllerProv
   *          the content drop controller prov
   * @param containerDropControllerProv
   *          the container drop controller prov
   */
  @Inject
  public FolderViewerAsFlowPanel(final GSpaceArmor gsArmor, final EventBus eventBus,
      final I18nTranslationService i18n, final ContentCapabilitiesRegistry capabilitiesRegistry,
      final KuneDragController dragController,
      final Provider<FolderContentDropController> contentDropControllerProv,
      final Provider<FolderContainerDropController> containerDropControllerProv) {
    super(gsArmor, eventBus, i18n, capabilitiesRegistry, dragController, contentDropControllerProv,
        containerDropControllerProv);
    widget = uiBinder.createAndBindUi(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#addItem(cc.kune.
   * gspace.client.viewers.items.FolderItemDescriptor,
   * com.google.gwt.event.dom.client.ClickHandler,
   * com.google.gwt.event.dom.client.DoubleClickHandler)
   */
  @Override
  public void addItem(final FolderItemDescriptor item, final ClickHandler clickHandler,
      final DoubleClickHandler doubleClickHandler) {
    // In this viewer we don't use the clickHandler from the presenter
    flow.add(createThumb(item.getText(), item.getIcon(), item.getTooltip(), "",
        item.getActionCollection(), doubleClickHandler, item.getStateToken()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerPanel#clear()
   */
  @Override
  public void clear() {
    flow.clear();
    super.clear();
  }

  /**
   * Creates the thumb.
   * 
   * @param text
   *          the text
   * @param icon
   *          the icon
   * @param tooltip
   *          the tooltip
   * @param tooltipTitle
   *          the tooltip title
   * @param menuitems
   *          the menuitems
   * @param doubleClickHandler
   *          the double click handler
   * @param token
   *          the token
   * @return the basic dragable thumb
   */
  public BasicDragableThumb createThumb(final String text, final Object icon, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menuitems,
      final DoubleClickHandler doubleClickHandler, final StateToken token) {
    final BasicDragableThumb thumb = new BasicDragableThumb(icon, ICONSIZE, text, ICONLABELMAXSIZE,
        false, token);
    final MenuDescriptor menu = new MenuDescriptor();
    menu.setStandalone(true);
    for (final GuiActionDescrip item : menuitems) {
      item.setParent(menu);
    }
    final ClickHandler clickHand = new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        menu.show(thumb);
      }
    };
    thumb.addClickHandler(clickHand);
    thumb.addDoubleClickHandler(doubleClickHandler);
    gsArmor.getSubheaderToolbar().add(menu);
    gsArmor.getSubheaderToolbar().addAll(menuitems);
    thumb.setTooltip(tooltipTitle, tooltip);
    thumb.setLabelVisible(true);
    return thumb;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerPanel#setContainer(cc
   * .kune.core.shared.dto.StateContainerDTO)
   */
  @Override
  public void setContainer(final StateContainerDTO state) {
    super.setContainer(state);
    final HTML html = new HTML("<b>Note:</b> This GUI is provisional<br/>");
    gsArmor.getDocContainer().add(html);
    gsArmor.getDocContainer().showWidget(html);
  }

}
