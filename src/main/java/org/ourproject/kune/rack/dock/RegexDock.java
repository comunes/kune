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

package org.ourproject.kune.rack.dock;

import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

import org.ourproject.kune.rack.RackHelper;

public class RegexDock extends AbstractDock {
    // private static final Log log = LogFactory.getLog(RegexRackDock.class);
    private final Pattern pattern;

    public RegexDock(final String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public boolean matches(final ServletRequest request) {
        String relativeURL = RackHelper.getRelativeURL(request);
        // log.debug ("MATCHING: " + pattern.toString() + " AGAINST: " +
        // relativeURL);
        return pattern.matcher(relativeURL).matches();
    }

    @Override
    public String toString() {
        return pattern.toString() + " - " + getFilter().getClass().getSimpleName();
    }

}
