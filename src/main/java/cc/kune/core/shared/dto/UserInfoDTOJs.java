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
package cc.kune.core.shared.dto;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The Class UserInfoDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserInfoDTOJs extends JavaScriptObject {

  protected UserInfoDTOJs() {
  }

  /** The chat name. */
  public final native String getchatName() /*-{
                                           return this.chatName;
                                           }-*/;

  /** The client flags. */
  public final native String getClientFlags() /*-{
                                              return this.clientFlags;
                                              }-*/;

  /** The home page. */
  public final native String gethomePage() /*-{
                                           return this.homePage;
                                           }-*/;

  /** The session json. */
  public final native String getSessionJSON() /*-{
                                              return this.sessionJSON;
                                              }-*/;

  /** The show deleted content. */
  public final native boolean getshowDeletedContent() /*-{
                                                      return this.showDeletedContent;
                                                      }-*/;

  /** The sign in count. */
  public final native Long getsignInCount() /*-{
                                            return this.signInCount;
                                            }-*/;

  /** The user hash. */
  public final native String getUserHash() /*-{
                                           return this.userHash;
                                           }-*/;

  /** The websocket address. */
  public final native String getWebsocketAddress() /*-{
                                                   return this.websocketAddress;
                                                   }-*/;

}
