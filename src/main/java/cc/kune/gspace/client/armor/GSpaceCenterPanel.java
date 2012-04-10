/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

@Singleton
public class GSpaceCenterPanel extends Composite implements GSpaceCenter {

  interface GSpaceCenterPanelUiBinder extends UiBinder<Widget, GSpaceCenterPanel> {
  }
  private static GSpaceCenterPanelUiBinder uiBinder = GWT.create(GSpaceCenterPanelUiBinder.class);
  @UiField
  ScrollPanel centerScroll;
  @UiField
  DeckPanel deck;

  public GSpaceCenterPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void add(final IsWidget w) {
    deck.add(w);
  }

  @Override
  public void add(final Widget w) {
    deck.add(w);
  }

  @Override
  public void clear() {
    deck.clear();
  }

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

  InsertPanel.ForIsWidget getDeck() {
    return deck;
  }

  public int getHeight() {
    return centerScroll.getOffsetHeight();
  }

  @Override
  public Widget getWidget(final int index) {
    return deck.getWidget(index);
  }

  @Override
  public int getWidgetCount() {
    return deck.getWidgetCount();
  }

  @Override
  public int getWidgetIndex(final IsWidget child) {
    return deck.getWidgetIndex(child);
  }

  @Override
  public int getWidgetIndex(final Widget child) {
    return deck.getWidgetIndex(child);
  }

  @Override
  public void insert(final IsWidget w, final int beforeIndex) {
    deck.insert(w, beforeIndex);
  }

  @Override
  public void insert(final Widget w, final int beforeIndex) {
    deck.insert(w, beforeIndex);
  }

  @Override
  public boolean remove(final int index) {
    return deck.remove(index);
  }

  @Override
  public void showWidget(final IsWidget widget) {
    deck.showWidget(deck.getWidgetIndex(widget));
  }

}
