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

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSNPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractSNPanel extends ViewImpl {

  /**
   * The Interface AbstractSNPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface AbstractSNPanelUiBinder extends UiBinder<Widget, AbstractSNPanel> {
  }

  /** The Constant AVATARLABELMAXSIZE. */
  private final static int AVATARLABELMAXSIZE = 4;

  /** The Constant AVATARSIZE. */
  private final static int AVATARSIZE = 22;

  /** The Constant CATEG_HEIGHT. */
  private final static String CATEG_HEIGHT = "86px";

  /** The Constant CATEG_MIN_HEIGHT. */
  private final static String CATEG_MIN_HEIGHT = "57px";

  /** The ui binder. */
  private static AbstractSNPanelUiBinder uiBinder = GWT.create(AbstractSNPanelUiBinder.class);

  /** The actions. */
  protected final ActionSimplePanel actions;

  /** The armor. */
  protected final GSpaceArmor armor;

  /** The avatar decorator prov. */
  protected final Provider<SmallAvatarDecorator> avatarDecoratorProv;

  /** The bottom actions toolbar. */
  ActionFlowPanel bottomActionsToolbar;

  /** The bottom panel. */
  @UiField
  FlowPanel bottomPanel;

  /** The categories flow. */
  @UiField
  FlowPanel categoriesFlow;

  /** The deck. */
  @UiField
  DeckPanel deck;

  /** The drag controller. */
  protected final KuneDragController dragController;

  /** The first category count. */
  @UiField
  Label firstCategoryCount;

  /** The first category flow. */
  @UiField
  FlowPanel firstCategoryFlow;

  /** The first category label. */
  @UiField
  Label firstCategoryLabel;

  /** The first category panel. */
  @UiField
  DockLayoutPanel firstCategoryPanel;

  /** The first category scroll. */
  @UiField
  ScrollPanel firstCategoryScroll;

  /** The first deck label. */
  @UiField
  Label firstDeckLabel;

  /** The last connected manager. */
  private final LastConnectedManager lastConnectedManager;

  /** The main panel. */
  @UiField
  FlowPanel mainPanel;

  /** The main title. */
  @UiField
  Label mainTitle;

  /** The snd category count. */
  @UiField
  Label sndCategoryCount;

  /** The snd category flow. */
  @UiField
  FlowPanel sndCategoryFlow;

  /** The snd category label. */
  @UiField
  Label sndCategoryLabel;

  /** The snd category panel. */
  @UiField
  DockLayoutPanel sndCategoryPanel;

  /** The snd category scroll. */
  @UiField
  ScrollPanel sndCategoryScroll;

  /** The snd deck label. */
  @UiField
  Label sndDeckLabel;

  /** The trd category count. */
  @UiField
  Label trdCategoryCount;

  /** The trd category flow. */
  @UiField
  FlowPanel trdCategoryFlow;

  /** The trd category label. */
  @UiField
  Label trdCategoryLabel;

  /** The trd category panel. */
  @UiField
  DockLayoutPanel trdCategoryPanel;

  /** The trd category scroll. */
  @UiField
  ScrollPanel trdCategoryScroll;

  /** The widget. */
  protected final Widget widget;

  /**
   * Instantiates a new abstract sn panel.
   * 
   * @param i18n
   *          the i18n
   * @param guiProvider
   *          the gui provider
   * @param armor
   *          the armor
   * @param avatarDecorator
   *          the avatar decorator
   * @param dragController
   *          the drag controller
   * @param lastConnectedManager
   *          the last connected manager
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return widget;
  }

  /**
   * Clear.
   */
  public void clear() {
    trdCategoryFlow.clear();
    firstCategoryFlow.clear();
    sndCategoryFlow.clear();
    actions.clear();
  }

  /**
   * Count as string.
   * 
   * @param count
   *          the count
   * @return the string
   */
  protected String countAsString(final int count) {
    return count > 0 ? new StringBuffer("(").append(count).append(")").toString() : "";
  }

  /**
   * Creates the thumb.
   * 
   * @param isPersonal
   *          the is personal
   * @param shortName
   *          the short name
   * @param text
   *          the text
   * @param avatarUrl
   *          the avatar url
   * @param tooltip
   *          the tooltip
   * @param tooltipTitle
   *          the tooltip title
   * @param menuitems
   *          the menuitems
   * @param token
   *          the token
   * @param dragable
   *          the dragable
   * @return the basic dragable thumb
   */
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

  /**
   * Decorate avatar with xmpp status.
   * 
   * @param shortname
   *          the shortname
   * @param thumb
   *          the thumb
   * @return the small avatar decorator
   */
  public SmallAvatarDecorator decorateAvatarWithXmppStatus(final String shortname,
      final BasicDragableThumb thumb) {
    final SmallAvatarDecorator decorator = avatarDecoratorProv.get();
    decorator.setWidget(thumb);
    decorator.setItem(shortname);
    return decorator;
  }

  /**
   * Gets the bottom toolbar.
   * 
   * @return the bottom toolbar
   */
  public IsActionExtensible getBottomToolbar() {
    return bottomActionsToolbar;
  }

  /**
   * Sets the first category visible.
   * 
   * @param visible
   *          the visible
   * @param big
   *          the big
   */
  public void setFirstCategoryVisible(final boolean visible, final boolean big) {
    firstCategoryPanel.setVisible(visible);
    firstCategoryPanel.setHeight(visible ? (big ? CATEG_HEIGHT : CATEG_MIN_HEIGHT) : "0px");
  }

  /**
   * Sets the snd category visible.
   * 
   * @param visible
   *          the visible
   * @param big
   *          the big
   */
  public void setSndCategoryVisible(final boolean visible, final boolean big) {
    sndCategoryPanel.setVisible(visible);
    sndCategoryPanel.setHeight(visible ? (big ? CATEG_HEIGHT : CATEG_MIN_HEIGHT) : "0px");
  }

  /**
   * Sets the tooltip.
   * 
   * @param widget
   *          the widget
   * @param title
   *          the title
   */
  protected void setTooltip(final Widget widget, final String title) {
    Tooltip.to(widget, title);
  }

  /**
   * Sets the trd category visible.
   * 
   * @param visible
   *          the visible
   * @param big
   *          the big
   */
  public void setTrdCategoryVisible(final boolean visible, final boolean big) {
    trdCategoryPanel.setVisible(visible);
    trdCategoryPanel.setHeight(visible ? (big ? CATEG_HEIGHT : CATEG_MIN_HEIGHT) : "0px");
  }
}
