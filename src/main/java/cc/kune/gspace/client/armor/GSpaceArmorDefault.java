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
package cc.kune.gspace.client.armor;

import org.cobogw.gwt.user.client.CSS;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.shared.utils.Pair;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GSpaceArmorDefault extends Composite implements GSpaceArmor {

  interface GSpaceArmorDefaultUiBinder extends UiBinder<Widget, GSpaceArmorDefault> {
  }
  private static final int CENTER_NORTH_HEIGHT = 153;
  private static final int CENTER_SOUTH_HEIGHT = 36;
  private static final int EAST_WIDTH = 220;

  private static GSpaceArmorDefaultUiBinder uiBinder = GWT.create(GSpaceArmorDefaultUiBinder.class);

  @UiField
  VerticalPanel centerNorth;
  @UiField
  ScrollPanel centerScroll;
  @UiField
  FlowPanel docContainer;
  @UiField
  DockLayoutPanel docContainerParent;
  @UiField
  FlowPanel docFooter;
  private final ActionFlowPanel docFooterToolbar;
  @UiField
  FlowPanel docHeader;
  @UiField
  FlowPanel docSubheader;
  @UiField
  FlowPanel entityFooter;
  private final ActionFlowPanel entityFooterToolbar;
  @UiField
  FlowPanel entityHeader;
  @UiField
  FlowPanel entityToolsCenter;
  @UiField
  FlowPanel entityToolsContainer;
  @UiField
  FlowPanel entityToolsNorth;
  @UiField
  FlowPanel entityToolsSouth;
  @UiField
  SplitLayoutPanel groupSpace;
  @UiField
  SimplePanel groupSpaceWrapper;
  private final ActionFlowPanel headerToolbar;
  @UiField
  SimplePanel homeSpace;
  @UiField
  DockLayoutPanel mainpanel;
  @UiField
  SimplePanel publicSpace;
  @UiField
  FlowPanel sitebar;
  @UiField
  DockLayoutPanel splitCenter;
  @UiField
  DockLayoutPanel splitEast;
  private final ActionFlowPanel subheaderToolbar;
  @UiField
  TabLayoutPanel tabs;
  private final ActionFlowPanel toolsSouthToolbar;
  @UiField
  FlowPanel userSpace;

  @Inject
  public GSpaceArmorDefault(final Provider<ActionFlowPanel> toolbarProv) {
    initWidget(uiBinder.createAndBindUi(this));
    groupSpace.setWidgetMinSize(splitEast, 150);
    DOM.setStyleAttribute((Element) groupSpace.getWidgetContainerElement(splitEast), "overflow",
        "visible");
    DOM.setStyleAttribute((Element) splitEast.getWidgetContainerElement(entityToolsContainer),
        "overflow", "visible");
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

  @Override
  public void clearBackImage() {
    final String bodyProp = "#FFFFFF";
    DOM.setStyleAttribute(groupSpaceWrapper.getElement(), CSS.A.BACKGROUND, bodyProp);
  }

  @Override
  public void enableCenterScroll(final boolean enable) {
    // TODO use here Element.getStyle()...
    if (enable) {
      centerScroll.setAlwaysShowScrollBars(false);
      DOM.setStyleAttribute(centerScroll.getElement(), "position", "absolute");
      DOM.setStyleAttribute((Element) centerScroll.getElement().getFirstChildElement(), "position",
          "relative");
    } else {
      centerScroll.getElement().getStyle().setOverflow(Overflow.HIDDEN);
      DOM.setStyleAttribute(centerScroll.getElement(), "position", "");
      DOM.setStyleAttribute((Element) centerScroll.getElement().getFirstChildElement(), "position", "");
    }
  }

  @Override
  public ForIsWidget getDocContainer() {
    return docContainer;
  }

  @Override
  public Pair<Integer, Integer> getDocContainerSize() {
    return Pair.create(centerScroll.getOffsetHeight(), centerScroll.getOffsetWidth());
  }

  @Override
  public ForIsWidget getDocFooter() {
    return docFooter;
  }

  @Override
  public IsActionExtensible getDocFooterToolbar() {
    return docFooterToolbar;
  }

  @Override
  public ForIsWidget getDocHeader() {
    return docHeader;
  }

  @Override
  public ForIsWidget getDocSubheader() {
    return docSubheader;
  }

  @Override
  public ForIsWidget getEntityFooter() {
    return entityFooter;
  }

  @Override
  public IsActionExtensible getEntityFooterToolbar() {
    return entityFooterToolbar;
  }

  @Override
  public ForIsWidget getEntityHeader() {
    return entityHeader;
  }

  @Override
  public ForIsWidget getEntityToolsCenter() {
    return entityToolsCenter;
  }

  @Override
  public ForIsWidget getEntityToolsNorth() {
    return entityToolsNorth;
  }

  @Override
  public ForIsWidget getEntityToolsSouth() {
    return entityToolsSouth;
  }

  @Override
  public IsActionExtensible getHeaderToolbar() {
    return headerToolbar;
  }

  @Override
  public SimplePanel getHomeSpace() {
    return homeSpace;
  }

  @Override
  public IsWidget getMainpanel() {
    return mainpanel;
  }

  @Override
  public SimplePanel getPublicSpace() {
    return publicSpace;
  }

  @Override
  public ForIsWidget getSitebar() {
    return sitebar;
  }

  @Override
  public IsActionExtensible getSubheaderToolbar() {
    return subheaderToolbar;
  }

  @Override
  public IsActionExtensible getToolsSouthToolbar() {
    return toolsSouthToolbar;
  }

  @Override
  public ForIsWidget getUserSpace() {
    return userSpace;
  }

  @Override
  public void selectGroupSpace() {
    tabs.selectTab(groupSpaceWrapper);
  }

  @Override
  public void selectHomeSpace() {
    tabs.selectTab(homeSpace);
  }

  @Override
  public void selectPublicSpace() {
    tabs.selectTab(publicSpace);
  }

  @Override
  public void selectUserSpace() {
    tabs.selectTab(userSpace);
  }

  @Override
  public void setBackImage(final String url) {
    final String bodyProp = "#FFFFFF url('" + url + "') fixed top left";
    DOM.setStyleAttribute(groupSpaceWrapper.getElement(), CSS.A.BACKGROUND, bodyProp);
  }

  @Override
  public void setMaximized(final boolean maximized) {
    // setMaximized(docFooter.getParent(), maximized);
    // setMaximized(entityFooter.getParent(), maximized);
    // setMaximized(entityHeader.getParent(), maximized);
    // setMaximized(entityToolsContainer, maximized);
    // splitCenter.setWidgetSize(centerScroll, getAbsoluteLeft())
    // setMaximized(splitCenter.getWidget(0), maximized);
    // setMaximized(splitCenter.getWidget(2), maximized);
    // setMaximized(splitEast, maximized);
    groupSpace.setWidgetSize(splitEast, maximized ? 0 : EAST_WIDTH);
    splitCenter.setWidgetSize(centerNorth, maximized ? 7 : CENTER_NORTH_HEIGHT);
    splitCenter.setWidgetSize(entityFooter, maximized ? 7 : CENTER_SOUTH_HEIGHT);
  }

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
}
