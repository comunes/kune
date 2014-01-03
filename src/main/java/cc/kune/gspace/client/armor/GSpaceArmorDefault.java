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
package cc.kune.gspace.client.armor;

import org.cobogw.gwt.user.client.CSS;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class GSpaceArmorDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GSpaceArmorDefault extends Composite implements GSpaceArmor {

  /**
   * The Interface GSpaceArmorDefaultUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface GSpaceArmorDefaultUiBinder extends UiBinder<Widget, GSpaceArmorDefault> {
  }

  /** The Constant CENTER_NORTH_HEIGHT. */
  private static final int CENTER_NORTH_HEIGHT = 153;

  /** The Constant CENTER_SOUTH_HEIGHT. */
  private static final int CENTER_SOUTH_HEIGHT = 36;

  /** The Constant TOOLS_WIDTH. */
  private static final int TOOLS_WIDTH = 220;

  /** The ui binder. */
  private static GSpaceArmorDefaultUiBinder uiBinder = GWT.create(GSpaceArmorDefaultUiBinder.class);

  /** The center north. */
  @UiField
  FlowPanel centerNorth;

  /** The center panel. */
  @UiField
  GSpaceCenterPanel centerPanel;

  /** The doc container parent. */
  @UiField
  DockLayoutPanel docContainerParent;

  /** The doc footer. */
  @UiField
  FlowPanel docFooter;

  /** The doc footer toolbar. */
  private final ActionFlowPanel docFooterToolbar;

  /** The doc header. */
  @UiField
  FlowPanel docHeader;

  /** The doc subheader. */
  @UiField
  FlowPanel docSubheader;

  /** The entity footer. */
  @UiField
  FlowPanel entityFooter;

  /** The entity footer toolbar. */
  private final ActionFlowPanel entityFooterToolbar;

  /** The entity header. */
  @UiField
  FlowPanel entityHeader;

  /** The entity tools center. */
  @UiField
  FlowPanel entityToolsCenter;

  /** The entity tools container. */
  @UiField
  FlowPanel entityToolsContainer;

  /** The entity tools main panel. */
  @UiField
  DockLayoutPanel entityToolsMainPanel;

  /** The entity tools north. */
  @UiField
  FlowPanel entityToolsNorth;

  /** The entity tools south. */
  @UiField
  FlowPanel entityToolsSouth;

  /** The group space. */
  @UiField
  SplitLayoutPanel groupSpace;

  /** The group space wrapper. */
  @UiField
  SimplePanel groupSpaceWrapper;

  /** The header toolbar. */
  private final ActionFlowPanel headerToolbar;

  /** The home space. */
  @UiField
  SimplePanel homeSpace;

  /** The mainpanel. */
  @UiField
  DockLayoutPanel mainpanel;

  /** The public space. */
  @UiField
  SimplePanel publicSpace;

  /** The sitebar. */
  @UiField
  FlowPanel sitebar;

  /** The split center. */
  @UiField
  DockLayoutPanel splitCenter;

  /** The subheader toolbar. */
  private final ActionFlowPanel subheaderToolbar;

  /** The tabs. */
  @UiField
  TabLayoutPanel tabs;

  /** The tools south toolbar. */
  private final ActionFlowPanel toolsSouthToolbar;

  /** The user space. */
  @UiField
  FlowPanel userSpace;

  /**
   * Instantiates a new g space armor default.
   * 
   * @param toolbarProv
   *          the toolbar prov
   */
  @Inject
  public GSpaceArmorDefault(final Provider<ActionFlowPanel> toolbarProv) {
    initWidget(uiBinder.createAndBindUi(this));
    docFooterToolbar = toolbarProv.get();
    headerToolbar = toolbarProv.get();
    subheaderToolbar = toolbarProv.get();
    toolsSouthToolbar = toolbarProv.get();
    entityFooterToolbar = toolbarProv.get();
    getDocHeader().add(headerToolbar);
    getDocSubheader().add(subheaderToolbar);
    getDocFooter().add(docFooterToolbar);
    getEntityToolsSouth().add(toolsSouthToolbar);
    getEntityFooter().add(entityFooterToolbar);
    entityToolsNorth.getElement().getStyle().setPosition(Position.RELATIVE);
    // entityToolsSouth.setVisible(false);
    mainpanel.getWidgetContainerElement(tabs).addClassName("k-spaces");
    enableCenterScroll(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#clearBackImage()
   */
  @Override
  public void clearBackImage() {
    // final String bodyProp = "#FFFFFF";
    final String bodyProp = "#FFFFFF url('" + GWT.getModuleBaseURL()
        + "images/clear.gif') fixed top left";
    DOM.setStyleAttribute(groupSpaceWrapper.getElement(), CSS.A.BACKGROUND, bodyProp);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#enableCenterScroll(boolean)
   */
  @Override
  public void enableCenterScroll(final boolean enable) {
    centerPanel.enableCenterScroll(enable);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getDocContainer()
   */
  @Override
  public GSpaceCenter getDocContainer() {
    return centerPanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getDocContainerHeight()
   */
  @Override
  public int getDocContainerHeight() {
    return centerPanel.getHeight();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getDocFooter()
   */
  @Override
  public ForIsWidget getDocFooter() {
    return docFooter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getDocFooterToolbar()
   */
  @Override
  public IsActionExtensible getDocFooterToolbar() {
    return docFooterToolbar;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getDocHeader()
   */
  @Override
  public ForIsWidget getDocHeader() {
    return docHeader;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getDocSubheader()
   */
  @Override
  public ForIsWidget getDocSubheader() {
    return docSubheader;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getEntityFooter()
   */
  @Override
  public ForIsWidget getEntityFooter() {
    return entityFooter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getEntityFooterToolbar()
   */
  @Override
  public IsActionExtensible getEntityFooterToolbar() {
    return entityFooterToolbar;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getEntityHeader()
   */
  @Override
  public ForIsWidget getEntityHeader() {
    return entityHeader;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getEntityToolsCenter()
   */
  @Override
  public ForIsWidget getEntityToolsCenter() {
    return entityToolsCenter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getEntityToolsNorth()
   */
  @Override
  public ForIsWidget getEntityToolsNorth() {
    return entityToolsNorth;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getEntityToolsSouth()
   */
  @Override
  public ForIsWidget getEntityToolsSouth() {
    return entityToolsSouth;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getHeaderToolbar()
   */
  @Override
  public IsActionExtensible getHeaderToolbar() {
    return headerToolbar;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getHomeSpace()
   */
  @Override
  public SimplePanel getHomeSpace() {
    return homeSpace;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getMainpanel()
   */
  @Override
  public IsWidget getMainpanel() {
    return mainpanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getPublicSpace()
   */
  @Override
  public SimplePanel getPublicSpace() {
    return publicSpace;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getSitebar()
   */
  @Override
  public ForIsWidget getSitebar() {
    return sitebar;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getSubheaderToolbar()
   */
  @Override
  public IsActionExtensible getSubheaderToolbar() {
    return subheaderToolbar;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getToolsSouthToolbar()
   */
  @Override
  public IsActionExtensible getToolsSouthToolbar() {
    return toolsSouthToolbar;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#getUserSpace()
   */
  @Override
  public ForIsWidget getUserSpace() {
    return userSpace;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#selectGroupSpace()
   */
  @Override
  public void selectGroupSpace() {
    tabs.selectTab(groupSpaceWrapper);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#selectHomeSpace()
   */
  @Override
  public void selectHomeSpace() {
    tabs.selectTab(homeSpace);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#selectPublicSpace()
   */
  @Override
  public void selectPublicSpace() {
    tabs.selectTab(publicSpace);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#selectUserSpace()
   */
  @Override
  public void selectUserSpace() {
    tabs.selectTab(userSpace);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceArmor#setBackImage(java.lang.String)
   */
  @Override
  public void setBackImage(final String url) {
    final String bodyProp = "#FFFFFF url('" + url + "') repeat fixed top left";
    DOM.setStyleAttribute(groupSpaceWrapper.getElement(), CSS.A.BACKGROUND, bodyProp);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.maxmin.IsMaximizable#setMaximized(boolean)
   */
  @Override
  public void setMaximized(final boolean maximized) {
    groupSpace.setWidgetSize(entityToolsMainPanel, maximized ? 0 : TOOLS_WIDTH);
    splitCenter.setWidgetSize(centerNorth, maximized ? 7 : CENTER_NORTH_HEIGHT);
    splitCenter.setWidgetSize(entityFooter, maximized ? 7 : CENTER_SOUTH_HEIGHT);
  }

  /**
   * Sets the maximized.
   * 
   * @param widget
   *          the widget
   * @param maximized
   *          the maximized
   */
  @SuppressWarnings("unused")
  private void setMaximized(final Widget widget, final boolean maximized) {
    widget.setVisible(!maximized);
    if (maximized) {
      // // $((Widget) gsArmor.getHeaderToolbar()).as(Effects).
      // $(widget).as(Effects).slideDown(new Function() {
      // @Override
      // public void f() {
      // }
      // });
    } else {
      // $(widget).parent().as(Effects).slideUp(new Function() {
      // @Override
      // public void f() {
      // }
      // });
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.armor.GSpaceArmor#setRTL(com.google.gwt.i18n.client
   * .HasDirection.Direction)
   */
  @Override
  public void setRTL(final Direction direction) {
    groupSpace.remove(splitCenter);
    groupSpace.setWidgetMinSize(entityToolsMainPanel, 25);
    if (direction.equals(Direction.RTL)) {
      groupSpace.addEast(entityToolsMainPanel, TOOLS_WIDTH);
    } else {
      groupSpace.addWest(entityToolsMainPanel, TOOLS_WIDTH);
    }
    // Add to the center
    groupSpace.add(splitCenter);

    // Fix tools Arrows visibility:
    groupSpace.getWidgetContainerElement(entityToolsMainPanel).getStyle().setOverflow(Overflow.VISIBLE);
    entityToolsMainPanel.getWidgetContainerElement(entityToolsContainer).getStyle().setOverflow(
        Overflow.VISIBLE);
  }
}
