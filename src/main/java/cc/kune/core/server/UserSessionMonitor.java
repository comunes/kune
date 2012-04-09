/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.core.server;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Singleton;

@Singleton
public class UserSessionMonitor implements HttpSessionListener {
  public static final Log LOG = LogFactory.getLog(UserSessionMonitor.class);

  @Override
  public void sessionCreated(final HttpSessionEvent event) {
    LOG.debug(String.format("Session created (with max inactive: %d)",
        event.getSession().getMaxInactiveInterval()), new Throwable());
    // event.getSession().getServletContext().getContextPath(),
  }

  @Override
  public void sessionDestroyed(final HttpSessionEvent event) {
    LOG.debug(String.format("Session destroyed (with max inactive: %d)",
        event.getSession().getMaxInactiveInterval()), new Throwable());
    // event.getSession().getServletContext().getContextPath(),
  }

}
