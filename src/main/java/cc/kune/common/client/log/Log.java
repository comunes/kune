/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.common.client.log;

import com.google.gwt.core.client.GWT;

public class Log {

    public static void debug(final String message) {
        GWT.log(prefix(message));
    }

    public static void debug(final String message, final Throwable caught) {
        GWT.log(prefix(message), caught);
    }

    public static void error(final String message) {
        debug(message);
    }

    public static void error(final String message, final Throwable caught) {
        debug(message, caught);
    }

    public static void info(final String message) {
        debug(message);
    }

    public static void info(final String message, final Throwable caught) {
        debug(message, caught);
    }

    private static String prefix(final String message) {
        return new StringBuffer().append("[kune] ").append(message).toString();
    }

}
