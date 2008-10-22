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

public class WindowUtils {
    public static Location getLocation() {
        Location result = new Location();
        result.setHash(getHash());
        result.setHost(getHost());
        result.setHostName(getHostName());
        result.setHref(getHref());
        result.setPath(getPath());
        result.setPort(getPort());
        result.setProtocol(getProtocol());
        result.setQueryString(getQueryString());
        return result;
    }

    private static native String getQueryString() /*-{
                                   return $wnd.location.search;
                               }-*/;

    private static native String getProtocol() /*-{
                                   return $wnd.location.protocol;
                               }-*/;

    private static native String getPort() /*-{
                                   return $wnd.location.port;
                               }-*/;

    private static native String getPath() /*-{
                                   return $wnd.location.pathname;
                               }-*/;

    private static native String getHref() /*-{
                                   return $wnd.location.href;
                               }-*/;

    private static native String getHostName() /*-{
                                   return $wnd.location.hostname;
                               }-*/;

    private static native String getHost() /*-{
                                   return $wnd.location.host;
                               }-*/;

    private static native String getHash() /*-{
                                   return $wnd.location.hash;
                               }-*/;

}