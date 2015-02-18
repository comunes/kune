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

package cc.kune.polymer.client;

import static cc.kune.polymer.client.Layout.*;
import br.com.rpa.client._paperelements.PaperFab;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class PolymerUtils {
  private static PaperFab inboxShowHide = PaperFab.wrap(PolymerId.INBOX_SHOW_HIDE.getId());

  public static void addFlexHorLayout(final Widget... widgets) {
    addFlexLayout(HORIZONTAL, widgets);
  }

  private static void addFlexLayout(final Layout horOVert, final Widget... widgets) {
    for (final Widget widget : widgets) {
      addLayout(widget.getElement(), horOVert, LAYOUT, FLEX);
    }
  }

  public static void addFlexVerLayout(final Widget... widgets) {
    addFlexLayout(VERTICAL, widgets);
  }

  public static void addLayout(final Element element, final Layout... layouts) {
    for (final Layout layout : layouts) {
      element.setAttribute(layout.getAttribute(), "");
    }
  }

  public native static String getMainSelected() /*-{
		return $wnd.kt.main_selected;
  }-*/;

  public native static boolean isMainDrawerNarrow() /*-{
		return $wnd.kt.main_narrow;
  }-*/;

  public static boolean isMainSelected() {
    return getMainSelected().equals("main");
  }

  public native static boolean isXSmall() /*-{
		return $wnd.kt.xsmall;
  }-*/;

  public native static void setBackImage(final String url) /*-{
		$wnd.kt.group_back_image_url = url;
  }-*/;

  public native static void setBlinkAnimation(final String id, boolean blink) /*-{
		$wnd.kt.blink(id, blink);
  }-*/;

  /**
   * Shows/select the "inbox" drawer.
   */
  public static void setDrawerSelected() {
    setMainSelected("drawer");
  }

  /**
   * Shows/select the "main" panel.
   */
  public static void setMainSelected() {
    setMainSelected("main");
  }

  private native static void setMainSelected(String selected) /*-{
		$wnd.kt.main_selected = selected;
  }-*/;

  public static void setNarrowSwipeEnabled(final boolean enabled) {
    setNarrowSwipeEnabledImpl(enabled);
    inboxShowHide.setEnabled(enabled);
  }

  private native static void setNarrowSwipeEnabledImpl(final boolean enabled) /*-{
		$wnd.kt.main_disableEdgeSwipe = !enabled;
		$wnd.kt.main_disableSwipe = !enabled;
  }-*/;

  public static void setNarrowVisible(final boolean visible) {
    setNarrowVisibleImpl(visible);
    // inboxShowHide.setVisible(visible);
  }

  private native static void setNarrowVisibleImpl(final boolean visible) /*-{
		$wnd.kt.main_forcenarrow = !visible;
  }-*/;

  public native static void setSitebarUserIconImage(final String url) /*-{
		$wnd.kt.user_icon_back_image_url = url;
  }-*/;

  public native static void toggleSearch() /*-{
		$wnd.kt.toggleSearch();
  }-*/;
}
