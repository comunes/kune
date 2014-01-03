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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class GSpaceCenterPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class GSpaceCenterPanel extends Composite implements GSpaceCenter {

  /**
   * The Interface GSpaceCenterPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface GSpaceCenterPanelUiBinder extends UiBinder<Widget, GSpaceCenterPanel> {
  }

  /** The ui binder. */
  private static GSpaceCenterPanelUiBinder uiBinder = GWT.create(GSpaceCenterPanelUiBinder.class);

  /** The center scroll. */
  @UiField
  ScrollPanel centerScroll;

  /** The deck. */
  @UiField
  DeckPanel deck;

  /**
   * Instantiates a new g space center panel.
   */
  public GSpaceCenterPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.client.ui.InsertPanel.ForIsWidget#add(com.google.gwt
   * .user.client.ui.IsWidget)
   */
  @Override
  public void add(final IsWidget w) {
    deck.add(w);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.client.ui.InsertPanel#add(com.google.gwt.user.client
   * .ui.Widget)
   */
  @Override
  public void add(final Widget w) {
    deck.add(w);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.armor.GSpaceCenter#clear()
   */
  @Override
  public void clear() {
    deck.clear();
  }

  /**
   * Enable center scroll.
   * 
   * @param enable
   *          the enable
   */
  public void enableCenterScroll(final boolean enable) {
    // TODO use here Element.getStyle()...
    if (enable) {
      // Move up???
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

  /**
   * Gets the deck.
   * 
   * @return the deck
   */
  InsertPanel.ForIsWidget getDeck() {
    return deck;
  }

  /**
   * Gets the height.
   * 
   * @return the height
   */
  public int getHeight() {
    return centerScroll.getOffsetHeight();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IndexedPanel#getWidget(int)
   */
  @Override
  public Widget getWidget(final int index) {
    return deck.getWidget(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IndexedPanel#getWidgetCount()
   */
  @Override
  public int getWidgetCount() {
    return deck.getWidgetCount();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.client.ui.IndexedPanel.ForIsWidget#getWidgetIndex(com
   * .google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public int getWidgetIndex(final IsWidget child) {
    return deck.getWidgetIndex(child);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.client.ui.IndexedPanel#getWidgetIndex(com.google.gwt
   * .user.client.ui.Widget)
   */
  @Override
  public int getWidgetIndex(final Widget child) {
    return deck.getWidgetIndex(child);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.client.ui.InsertPanel.ForIsWidget#insert(com.google
   * .gwt.user.client.ui.IsWidget, int)
   */
  @Override
  public void insert(final IsWidget w, final int beforeIndex) {
    deck.insert(w, beforeIndex);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.client.ui.InsertPanel#insert(com.google.gwt.user.client
   * .ui.Widget, int)
   */
  @Override
  public void insert(final Widget w, final int beforeIndex) {
    deck.insert(w, beforeIndex);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IndexedPanel#remove(int)
   */
  @Override
  public boolean remove(final int index) {
    return deck.remove(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.armor.GSpaceCenter#showWidget(com.google.gwt.user
   * .client.ui.IsWidget)
   */
  @Override
  public void showWidget(final IsWidget widget) {
    deck.showWidget(deck.getWidgetIndex(widget));
  }

}
