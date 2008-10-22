/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
/*
 * Copyright 2006 Robert Hanson <iamroberthanson AT gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ourproject.kune.platf.client.ui;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private String hash;
    private String host;
    private String hostName;
    private String href;
    private String path;
    private String port;
    private String protocol;
    private String queryString;
    private HashMap<String, String> paramMap;

    public String getHash() {
        return hash;
    }

    public String getHost() {
        return host;
    }

    public String getHostName() {
        return hostName;
    }

    public String getHref() {
        return href;
    }

    public String getPath() {
        return path;
    }

    public String getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getQueryString() {
        return queryString;
    }

    protected void setHash(final String hash) {
        this.hash = hash;
    }

    protected void setHost(final String host) {
        this.host = host;
    }

    protected void setHostName(final String hostName) {
        this.hostName = hostName;
    }

    protected void setHref(final String href) {
        this.href = href;
    }

    protected void setPath(final String path) {
        this.path = path;
    }

    protected void setPort(final String port) {
        this.port = port;
    }

    protected void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    protected void setQueryString(final String queryString) {
        this.queryString = queryString;
        paramMap = new HashMap<String, String>();

        if (queryString != null && queryString.length() > 1) {
            String qs = queryString.substring(1);
            String[] kvPairs = qs.split("&");
            for (int i = 0; i < kvPairs.length; i++) {
                String[] kv = kvPairs[i].split("=");
                if (kv.length > 1) {
                    paramMap.put(kv[0], unescape(kv[1]));
                } else {
                    paramMap.put(kv[0], "");
                }
            }
        }
    }

    private native String unescape(String val) /*-{
                                   return unescape(val);
                               }-*/;

    public String getParameter(final String name) {
        return paramMap.get(name);
    }

    public Map<String, String> getParameterMap() {
        return paramMap;
    }

}