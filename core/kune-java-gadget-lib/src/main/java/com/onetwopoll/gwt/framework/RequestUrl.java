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
package com.onetwopoll.gwt.framework;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;

public class RequestUrl {

  protected String url;
  protected String queryString;

  public RequestUrl() {
    url = GWT.getHostPageBaseURL() + "rest";
    queryString = null;
  }

  public RequestUrl(String baseUrl) {
    url = baseUrl;
    queryString = null;
  }

  public RequestUrl add(String pathPiece) {

    if (pathPiece == null)
      return this;

    String[] pieces = pathPiece.split("/");
    for (int i = 0; i < pieces.length; i++) {
      if (pieces[i].isEmpty())
        continue;
      url += "/" + URL.encodeComponent(pieces[i]);
    }
    return this;
  }

  public RequestUrl add(int pathPiece) {
    return add(Integer.toString(pathPiece));
  }

  public RequestUrl addParam(String key, String value) {
    if (key == null)
      return this;
    if (queryString == null || queryString.isEmpty())
      queryString = "?";
    else
      queryString += "&";
    queryString += URL.encodeComponent(key) + "=" + URL.encodeComponent(value);
    return this;
  }

  public String build() {
    if (queryString == null || queryString.isEmpty())
      return url;
    return url + queryString;
  }

  @Override
  public String toString() {
    return build();
  }

}
