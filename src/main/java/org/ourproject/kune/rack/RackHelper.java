/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.rack;

import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RackHelper {
    private RackHelper() {
    }

    public static String extractParameters(final ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        int index = uri.indexOf('?');
        if (index > 0) {
            return uri.substring(index);
        } else {
            return "";
        }
    }

    public static String getRelativeURL(final ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        return uri.substring(contextPath.length());
    }

    public static String getURI(final ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return httpServletRequest.getRequestURI();
    }

    public static String buildForwardString(final ServletRequest request, final String forward) {
        String parameters = RackHelper.extractParameters(request);
        return new StringBuilder(forward).append(parameters).toString();
    }

	public static boolean matches(ServletRequest request, Pattern pattern) {
        String relativeURL = RackHelper.getRelativeURL(request);
        return pattern.matcher(relativeURL).matches();
	}

}
