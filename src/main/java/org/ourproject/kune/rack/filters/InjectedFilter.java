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
package org.ourproject.kune.rack.filters;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.ourproject.kune.rack.RackServletFilter;

import com.google.inject.Injector;

public abstract class InjectedFilter implements Filter {
    protected ServletContext ctx;

    public void destroy() {
    }

    public <T> T getInstance(final Class<T> type) {
        return getInjector().getInstance(type);
    }

    public void init(final FilterConfig filterConfig) throws ServletException {
        this.ctx = filterConfig.getServletContext();
        getInjector().injectMembers(this);
    }

    private Injector getInjector() {
        return (Injector) ctx.getAttribute(RackServletFilter.INJECTOR_ATTRIBUTE);
    }

}
