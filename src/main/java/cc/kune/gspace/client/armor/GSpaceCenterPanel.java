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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class GSpaceCenterPanel extends Composite {

  interface GSpaceCenterPanelUiBinder extends UiBinder<Widget, GSpaceCenterPanel> {
  }
  private static GSpaceCenterPanelUiBinder uiBinder = GWT.create(GSpaceCenterPanelUiBinder.class);
  @UiField
  ScrollPanel centerScroll;
  @UiField
  DeckPanel deck;
  @UiField
  FlowPanel infoPanel;
  @UiField
  FlowPanel mainPanel;

  public GSpaceCenterPanel() {
    initWidget(uiBinder.createAndBindUi(this));
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
}
