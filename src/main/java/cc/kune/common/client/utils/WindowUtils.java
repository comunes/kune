/*
 * Copyright (C) 2007-2014 The kune development team (see CREDITS for details)
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
package cc.kune.common.client.utils;

import com.google.gwt.user.client.Window;

public class WindowUtils {

  /**
   * https://developer.mozilla.org/en/DOM/window.location
   * 
   * @param newUrl
   */
  public static native void changeHref(String newUrl) /*-{
		$wnd.location.href = newUrl;
  }-*/;

  /**
   * https://developer.mozilla.org/en/DOM/window.location
   * 
   * "If you need to change pathname but keep the hash as is, use the replace() method instead, which should work consistently across browsers."
   * 
   * @param newUrl
   */
  public static void changeHrefKeepHash(final String newUrl) {
    Window.Location.replace(newUrl);
  }

  public static native boolean dontHasWebSocket() /*-{
		return !window.WebSocket
  }-*/;

  /**
   * sample: #site.docs.3.1
   **/
  public static String getHash() {
    return Window.Location.getHash();
  };

  /**
   * sample: localhost:8080
   * 
   * @return
   */
  public static String getHost() {
    return Window.Location.getHost();
  }

  /**
   * sample: localhost
   * 
   * @return
   */
  public static String getHostName() {
    return Window.Location.getHostName();
  }

  /**
   * sample: http://localhost:8080/ws/#site.docs.3.1?locale=en&log_level=INFO
   **/
  public static String getHref() {
    return Window.Location.getHref();
  };

  public static String getParameter(final String param) {
    return Window.Location.getParameter(param);
  }

  /**
   * sample: /ws/
   * 
   * @return
   */
  public static String getPath() {
    return Window.Location.getPath();
  }

  public static String getPort() {
    return Window.Location.getPort();
  }

  public static String getProtocol() {
    return Window.Location.getProtocol();
  }

  /**
   * sample: ?locale=en&log_level=INFO
   * 
   * @return
   */
  public static String getQueryString() {
    return Window.Location.getQueryString();
  }

}
