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

package cc.kune.sandbox.client;

import java.util.HashMap;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.FlowActionExtensible;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.armor.GSpaceCenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GSpaceArmorPolymer implements GSpaceArmor {

  private final GSpaceCenter centerPanel;
  private final ActionFlowPanel docFooterToolbar;
  private final HashMap<String, Element> elements;
  private final ActionFlowPanel entityFooterToolbar;
  private final FlowActionExtensible flowActionTrash;
  private final ActionFlowPanel headerToolbar;
  private final HashMap<String, WrappedFlowPanel> panels;
  private final ActionFlowPanel subheaderToolbar;
  private final ActionFlowPanel toolsSouthToolbar;
  private final FlowPanel trash;

  @Inject
  GSpaceArmorPolymer(final GSpaceCenter centerPanel, final Provider<ActionFlowPanel> toolbarProv) {
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
    this.centerPanel = centerPanel;
    panels = new HashMap<String, WrappedFlowPanel>();
    elements = new HashMap<String, Element>();
    // This (temporal) panel is for not already implemented panels, so we can
    // continue without showing this widgets
    // Should be deleted when finished
    getDiv("doc_content").add(centerPanel);

    trash = new FlowPanel();
    flowActionTrash = new FlowActionExtensible();
    trash.add(flowActionTrash);
  }

  @Override
  public void clearBackImage() {
    final String bodyProp = "#FFFFFF url('" + GWT.getModuleBaseURL()
        + "images/clear.gif') fixed top left";
    getGroupHeader().setPropertyString("background", bodyProp);
  }

  private SimplePanel createDummySimplePanel() {
    final SimplePanel panel = new SimplePanel();
    trash.add(panel);
    return panel;
  }

  @Override
  public void enableCenterScroll(final boolean enable) {
    PolymerUtils.getShadowElement("core_scroll_header_panel", "mainContainer").getStyle().setOverflowY(
        enable ? Overflow.AUTO : Overflow.HIDDEN);
  }

  public WrappedFlowPanel getDiv(final String id) {
    WrappedFlowPanel panel = panels.get(id);
    if (panel == null) {
      panel = WrappedFlowPanel.wrap(id);
      panels.put(id, panel);
    }
    return panel;
  }

  @Override
  public GSpaceCenter getDocContainer() {
    return centerPanel;
  }

  @Override
  public int getDocContainerHeight() {
    return getDiv("doc_content").getOffsetHeight();
  }

  @Override
  public ForIsWidget getDocFooter() {
    return trash;
  }

  @Override
  public IsActionExtensible getDocFooterToolbar() {
    return flowActionTrash;
  }

  @Override
  public ForIsWidget getDocHeader() {
    return getDiv("document_name");
  }

  @Override
  public ForIsWidget getDocSubheader() {
    return getDiv("doc_toolbar");
  }

  public Element getElement(final String id) {
    Element element = elements.get(id);
    if (element == null) {
      element = DOM.getElementById(id);
      elements.put(id, element);
    }
    return element;
  }

  @Override
  public ForIsWidget getEntityFooter() {
    return trash;
  }

  @Override
  public IsActionExtensible getEntityFooterToolbar() {
    return flowActionTrash;
  }

  @Override
  public ForIsWidget getEntityHeader() {
    return trash;
  }

  @Override
  public ForIsWidget getEntityToolsCenter() {
    return trash;
  }

  @Override
  public ForIsWidget getEntityToolsNorth() {
    return getDiv("header_social_net");
  }

  @Override
  public ForIsWidget getEntityToolsSouth() {
    return trash;
  }

  private Element getGroupHeader() {
    return getElement("header_core_toolbar");
  }

  @Override
  public IsActionExtensible getHeaderToolbar() {
    return headerToolbar;
  }

  @Override
  public SimplePanel getHomeSpace() {
    return createDummySimplePanel();
  }

  public ForIsWidget getHomeSpaceFlow() {
    return getDiv("k-home-center");
  }

  @Override
  public IsWidget getMainpanel() {
    return getDiv("kunetemplate");
  }

  @Override
  public SimplePanel getPublicSpace() {
    return createDummySimplePanel();
  }

  @Override
  public ForIsWidget getSitebar() {
    return getDiv("sitebar_right_extensionbar");
  }

  private void getSpace(final int index) {
    getElement("space_selector_paper_tabs").setPropertyInt("selected", index);
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
    return getDiv("#user_space");
  }

  @Override
  public void selectGroupSpace() {
    getSpace(1);
  }

  @Override
  public void selectHomeSpace() {
    getSpace(0);
  }

  @Override
  public void selectPublicSpace() {
    getSpace(1);
  }

  @Override
  public void selectUserSpace() {
    getSpace(2);
  }

  @Override
  public void setBackImage(final String url) {
    getGroupHeader().getStyle().setBackgroundImage(url);
  }

  @Override
  public void setMaximized(final boolean maximized) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setRTL(final Direction direction) {
    // TODO use reverse methods in Polymer also
    // http://stackoverflow.com/questions/26110405/polymer-rtl-text-based-on-an-attribute
  }

}
