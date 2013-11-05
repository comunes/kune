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
package cc.kune.core.client.sn;

import cc.kune.chat.client.LastConnectedManager;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.avatar.SmallAvatarDecorator;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

public class AbstractSNPanel extends ViewImpl {

  interface AbstractSNPanelUiBinder extends UiBinder<Widget, AbstractSNPanel> {
  }

  private final static int AVATARLABELMAXSIZE = 4;
  private final static int AVATARSIZE = 22;
  private final static String CATEG_HEIGHT = "86px";
  private final static String CATEG_MIN_HEIGHT = "57px";
  private static AbstractSNPanelUiBinder uiBinder = GWT.create(AbstractSNPanelUiBinder.class);
  protected final ActionSimplePanel actions;
  protected final GSpaceArmor armor;
  protected final Provider<SmallAvatarDecorator> avatarDecoratorProv;
  ActionFlowPanel bottomActionsToolbar;
  @UiField
  FlowPanel bottomPanel;
  @UiField
  FlowPanel categoriesFlow;
  @UiField
  DeckPanel deck;
  protected final KuneDragController dragController;
  @UiField
  Label firstCategoryCount;
  @UiField
  FlowPanel firstCategoryFlow;
  @UiField
  Label firstCategoryLabel;
  @UiField
  DockLayoutPanel firstCategoryPanel;
  @UiField
  ScrollPanel firstCategoryScroll;
  @UiField
  Label firstDeckLabel;
  private final LastConnectedManager lastConnectedManager;
  @UiField
  FlowPanel mainPanel;
  @UiField
  Label mainTitle;
  @UiField
  Label sndCategoryCount;
  @UiField
  FlowPanel sndCategoryFlow;
  @UiField
  Label sndCategoryLabel;
  @UiField
  DockLayoutPanel sndCategoryPanel;
  @UiField
  ScrollPanel sndCategoryScroll;
  @UiField
  Label sndDeckLabel;
  @UiField
  Label trdCategoryCount;
  @UiField
  FlowPanel trdCategoryFlow;
  @UiField
  Label trdCategoryLabel;
  @UiField
  DockLayoutPanel trdCategoryPanel;
  @UiField
  ScrollPanel trdCategoryScroll;
  protected final Widget widget;

  public AbstractSNPanel(final I18nTranslationService i18n, final GuiProvider guiProvider,
      final GSpaceArmor armor, final Provider<SmallAvatarDecorator> avatarDecorator,
      final KuneDragController dragController, final LastConnectedManager lastConnectedManager) {
    this.armor = armor;
    this.avatarDecoratorProv = avatarDecorator;
    this.dragController = dragController;
    this.lastConnectedManager = lastConnectedManager;
    widget = uiBinder.createAndBindUi(this);
    actions = new ActionSimplePanel(guiProvider, i18n);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  public void clear() {
    trdCategoryFlow.clear();
    firstCategoryFlow.clear();
    sndCategoryFlow.clear();
    actions.clear();
  }

  protected String countAsString(final int count) {
    return count > 0 ? new StringBuffer("(").append(count).append(")").toString() : "";
  }

  public BasicDragableThumb createThumb(final boolean isPersonal, final String shortName,
      final String text, final String avatarUrl, final String tooltip, final String tooltipTitle,
      final GuiActionDescCollection menuitems, final StateToken token, final boolean dragable) {
    final BasicDragableThumb thumb = new BasicDragableThumb(avatarUrl, AVATARSIZE, text,
        AVATARLABELMAXSIZE, false, token);

    final ClickHandler clickHand = new ClickHandler() {
      MenuDescriptor menu;

      @Override
      public void onClick(final ClickEvent event) {
        if (menu == null) {
          menu = new MenuDescriptor();
          menu.setStandalone(true);
          for (final GuiActionDescrip item : menuitems) {
            item.setParent(menu);
          }
          actions.add(menu);
          // (Not needed after setParent Recursive) actions.add(menuitems);
        }
        thumb.hideTooltip();
        menu.show(thumb);
      }
    };
    thumb.addClickHandler(clickHand);

    thumb.setTooltip(tooltipTitle,
        isPersonal ? tooltip
            + (TextUtils.empty(lastConnectedManager.get(shortName, true)) ? "" : ". "
                + lastConnectedManager.get(shortName, true)) : tooltip);
    thumb.setLabelVisible(false);
    if (dragable) {
      dragController.makeDraggable(thumb);
    }
    return thumb;
  }

  public SmallAvatarDecorator decorateAvatarWithXmppStatus(final String shortname,
      final BasicDragableThumb thumb) {
    final SmallAvatarDecorator decorator = avatarDecoratorProv.get();
    decorator.setWidget(thumb);
    decorator.setItem(shortname);
    return decorator;
  }

  public IsActionExtensible getBottomToolbar() {
    return bottomActionsToolbar;
  }

  public void setFirstCategoryVisible(final boolean visible, final boolean big) {
    firstCategoryPanel.setVisible(visible);
    firstCategoryPanel.setHeight(visible ? (big ? CATEG_HEIGHT : CATEG_MIN_HEIGHT) : "0px");
  }

  public void setSndCategoryVisible(final boolean visible, final boolean big) {
    sndCategoryPanel.setVisible(visible);
    sndCategoryPanel.setHeight(visible ? (big ? CATEG_HEIGHT : CATEG_MIN_HEIGHT) : "0px");
  }

  protected void setTooltip(final Widget widget, final String title) {
    Tooltip.to(widget, title);
  }

  public void setTrdCategoryVisible(final boolean visible, final boolean big) {
    trdCategoryPanel.setVisible(visible);
    trdCategoryPanel.setHeight(visible ? (big ? CATEG_HEIGHT : CATEG_MIN_HEIGHT) : "0px");
  }
}
