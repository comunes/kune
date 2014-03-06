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
package cc.kune.core.client.embed;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The Class EmbedConfJso is used to configure the embed system via a JSNI call
 * from outside.
 */
public class EmbedConfJso extends JavaScriptObject {

  protected EmbedConfJso() {
  }

  /**
   * Gets if this wave should be read only.
   * 
   * @return the read only
   */
  public final native boolean getReadOnly() /*-{
		return this.readOnly || false;
  }-*/;

  /**
   * Gets the kune server hostname.
   * 
   * @return the server
   */
  public final native String getServerUrl() /*-{
		return this.serverUrl || $wnd.location.protocol + '//'
				+ $wnd.location.host + '/';
  }-*/;

  public final native boolean getShowSignIn() /*-{
		return this.showSignIn || true;
  }-*/;

  public final native boolean getShowSignOut() /*-{
		return this.showSignOut || true;
  }-*/;

  public final native String getSignInText() /*-{
		return this.signInText || 'Participate';
  }-*/;

  /**
   * Gets the sitebar right margin.
   * 
   * @return the sitebar right margin
   */
  public final native int getSitebarRightMargin() /*-{
		return this.sitebarRightMargin || 30;
  }-*/;

  public final native int getSitebarTopMargin() /*-{
		return this.sitebarTopMargin || 0;
  }-*/;

}
