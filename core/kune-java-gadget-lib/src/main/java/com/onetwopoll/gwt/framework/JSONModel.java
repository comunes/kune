/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under 
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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;

public class JSONModel extends JavaScriptObject {

  protected JSONModel() {
  }

  public final static native JavaScriptObject evalJson(String json) /*-{
                                                                    try{
                                                                    // TODO throw error on invalid json
                                                                    if (/^[\],:{}\s]*$/.test(json.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
                                                                    return eval('(' + json + ')');
                                                                    }
                                                                    }
                                                                    catch(e)
                                                                    {
                                                                    // TODO log this somewhere
                                                                    }
                                                                    return {};
                                                                    }-*/;

  public final static native JsArray<? extends JavaScriptObject> evalJsonAsArray(String json) /*-{
                                                                                              try {
                                                                                              // TODO throw error on invalid json
                                                                                              if (/^[\],:{}\s]*$/.test(json.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
                                                                                              // the parens are required because '{' and '}' are ambiguous in JS and either enclose an object data or a code block
                                                                                              return eval('(' + json + ')');
                                                                                              }
                                                                                              }
                                                                                              catch(e) 
                                                                                              {
                                                                                              // TODO log this somewhere
                                                                                              }
                                                                                              return [];
                                                                                              }-*/;

  public final String toJSONString() {
    JSONObject o = new JSONObject(this);
    return o.toString();
  }

}
