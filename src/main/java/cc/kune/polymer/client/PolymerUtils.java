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

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public class PolymerUtils {

  private static Timer hideInboxTimer = new Timer() {
    @Override
    public void run() {
      PolymerUtils.setMainSelected();
      PolymerUtils.setNarrowVisible(false);
    }
  };

  private static Timer hideSNTimer = new Timer() {
    @Override
    public void run() {
      PolymerUtils.hideSN();
      PolymerUtils.setSNWidth("80%");
    }
  };

  /* private static PaperFab inboxShowHide; */

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
      element.addClassName(layout.getAttribute());
      // element.setAttribute(layout.getAttribute(), "");
    }
  }

  public static void addLayout(final Widget widget, final Layout... layouts) {
    addLayout(widget.getElement(), layouts);
  }

  public native static String getMainSelected() /*-{
    return $wnd.kt.main_selected;
  }-*/;

  public native static String getSNSelected() /*-{
    return $wnd.kt.group_header_selected;
  }-*/;

  public static void hideInboxCancel() {
    hideInboxTimer.cancel();
  }

  public static void hideSNWithDelay(int delay) {
    hideSNTimer.schedule(delay);
  }

  public static void hideInboxWithDelay() {
    hideInboxTimer.schedule(PolymerUtils.isMainDrawerNarrow() ? 0 : 4000);
  }

  public native static void showSN() /*-{
    $wnd.kt.group_header_selected = "drawer";
  }-*/;

  public native static void hideSN() /*-{
    $wnd.kt.group_header_selected = "main";
  }-*/;

  public native static void hideSpinner() /*-{
    $wnd.kt.hideSpinner();
  }-*/;

  public static boolean isGroupHeaderMainSelected() {
    return getSNSelected().equals("main");
  }

  public native static boolean isMainDrawerNarrow() /*-{
    return $wnd.kt.main_narrow;
  }-*/;

  public static boolean isMainSelected() {
    return getMainSelected().equals("main");
  }

  public native static boolean isXSmall() /*-{
    return $wnd.kt.xsmall;
  }-*/;

  public static void removeLayout(final Element element, final Layout... layouts) {
    for (final Layout layout : layouts) {
      element.removeClassName(layout.getAttribute());
      // element.removeAttribute(layout.getAttribute());
    }
  }

  public native static void setBackImage(final String url) /*-{
    $wnd.kt.group_back_image_url = url;
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
    /* if (inboxShowHide == null) {
      inboxShowHide = PaperFab.wrap(PolymerId.INBOX_SHOW_HIDE.getId());
    }
    inboxShowHide.setEnabled(enabled); */
  }

  private native static void setNarrowSwipeEnabledImpl(final boolean enabled) /*-{
    $wnd.kt.main_disableEdgeSwipe = !enabled;
    $wnd.kt.main_disableSwipe = !enabled;
  }-*/;

  public static void setNarrowVisible(final boolean visible) {
    setNarrowVisibleImpl(visible);
  }

  private native static void setNarrowVisibleImpl(final boolean visible) /*-{
    $wnd.kt.main_forcenarrow = !visible;
  }-*/;

  public native static void setSitebarUserIconImage(final String url) /*-{
    $wnd.kt.user_icon_back_image_url = url;
  }-*/;

  public native static void setSNWidth(final String width) /*-{
    $wnd.kt.group_header_drawer_width = width;
  }-*/;


  private native static void setTheme(JsArrayString c, JsArrayString bg) /*-{
    $wnd.kt.c1 = c[0];
    $wnd.kt.bg1 = bg[0];
    $wnd.kt.c2 = c[1];
    $wnd.kt.bg2 = bg[1];
    $wnd.kt.c3 = c[2];
    $wnd.kt.bg3 = bg[2];
    $wnd.kt.c4 = c[3];
    $wnd.kt.bg4 = bg[3];
    $wnd.kt.c5 = c[4];
    $wnd.kt.bg5 = bg[4];
    $wnd.kt.c6 = c[5];
    $wnd.kt.bg6 = bg[5];
    $wnd.kt.c7 = c[6];
    $wnd.kt.bg7 = bg[6];
    $wnd.kt.c8 = c[7];
    $wnd.kt.bg8 = bg[7];
  }-*/;

  public static void setTheme(final String c[], final String bg[]) {
    setTheme(toJsArray(c), toJsArray(bg));
  }

  public native static void showSpinner() /*-{
    $wnd.kt.showSpinner();
  }-*/;

  public native static void toggleSearch() /*-{
    $wnd.kt.toggleSearch();
  }-*/;

  /**
   * https://stackoverflow.com/questions/22167486/gwt-how-to-pass-java-array-
   * into-javascript-native-method
   */
  private static JsArrayString toJsArray(final String[] input) {
    final JsArrayString jsArrayString = JsArrayString.createArray().cast();
    for (final String s : input) {
      jsArrayString.push(s);
    }
    return jsArrayString;
  }

}
