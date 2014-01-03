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

package cc.kune.core.client.state;

import cc.kune.common.client.log.Log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LinkInterceptor implements NativePreviewHandler {

  private static final String A = "a";
  private static final String HREF = "href";
  private final HistoryWrapper history;

  @Inject
  public LinkInterceptor(final HistoryWrapper history) {
    this.history = history;
    if (SessionInstance.get().isGuiInDevelopment()) {
      Event.addNativePreviewHandler(this);
    }
  }

  @Override
  public void onPreviewNativeEvent(final NativePreviewEvent nativeEventPreview) {
    final Event event = Event.as(nativeEventPreview.getNativeEvent());
    final String base = GWT.getHostPageBaseURL();
    if (nativeEventPreview.getTypeInt() == Event.ONCLICK) {
      try {
        final Element target = DOM.eventGetTarget(event);
        if (A.equalsIgnoreCase(target.getTagName())) {
          final String href = DOM.getElementAttribute(target, HREF);
          if (LinkInterceptorHelper.isLocal(href, base)) {
            // Is a local link so we try to use the history without load a new
            // page
            final String hash = LinkInterceptorHelper.getHash(href);
            if (hash.equals(href)) {
              // Is not a different hash, so continue
            } else {
              // Is a local link so we use the history
              history.newItem(hash);
              nativeEventPreview.cancel();
            }
          } else {
            // External URL so just follow (normally with target="_blank")
          }
        }
      } catch (final Exception e) {
        Log.info("Error trying to intercept link clink event");
      }
    }
  }
}
