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

package cc.kune.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.inject.Singleton;

@Singleton
public class KuneLinkInterceptor implements NativePreviewHandler {

  public KuneLinkInterceptor() {
    Event.addNativePreviewHandler(this);
  }

  private native String getTagName(final Element element) /*-{
                  return element.tagName;
          }-*/;

  boolean isLocal(final String href, final String base) {
    final String baseHashbang = base + "#!";
    final String baseHash = base + "#";
    final boolean startsWithURLandHashbang = href.startsWith(baseHashbang);
    final boolean startsWithURLandHash = href.startsWith(baseHash);
    return href.startsWith("#") || startsWithURLandHashbang || startsWithURLandHash;
  }

  @Override
  public void onPreviewNativeEvent(final NativePreviewEvent nativeEventPreview) {
    final Event event = Event.as(nativeEventPreview.getNativeEvent());
    final String base = GWT.getHostPageBaseURL();
    if (nativeEventPreview.getTypeInt() == Event.ONCLICK) {
      final Element target = DOM.eventGetTarget(event);
      if ("a".equalsIgnoreCase(getTagName(target))) {
        GWT.log("HREF base: " + base);
        final String href = DOM.getElementAttribute(target, "href");
        GWT.log("HREF href: " + href);
        if (isLocal(href, base)) {
          GWT.log("HREF true");
          // if (startsWithURLandHashbang) {
          // History.newItem(href.replace(baseHashbang, ""));
          // nativeEventPreview.cancel();
          // }
          // if (startsWithURLandHash) {
          // History.newItem(href.replace(baseHash, ""));
          // nativeEventPreview.cancel();
          // }
        } else {
          GWT.log("HREF false");
          // FIXME this should be removed
          nativeEventPreview.cancel();
        }
      }
    }
  }
}
