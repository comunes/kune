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

package cc.kune.client;

import com.google.gwt.event.shared.UmbrellaException;

/**
 *
 * @see https
 *      ://stackoverflow.com/questions/28551066/will-there-be-any-way-to-effectively
 *      -log-exceptions-with-gwt-superdevmode-firef
 *
 */
public class SuperDevModeUncaughtExceptionHandler extends DefaultUncaughtExceptionHandler {
  private native void groupEnd() /*-{
		var groupEnd = console.groupEnd || function() {
		};
		groupEnd.call(console);
  }-*/;

  private native void groupStart(String msg) /*-{
		var groupStart = console.groupCollapsed || console.group
				|| console.error || console.log;
		groupStart.call(console, msg);
  }-*/;

  private native void log(Throwable t) /*-{
		var logError = console.error || console.log;
		var backingError = t.__gwt$backingJsError;
		logError.call(console, backingError && backingError.stack);
  }-*/;

  private void logExceptionInSuperDev(final Throwable t, final boolean isCause) {
    String msg = t.toString();
    if (isCause) {
      msg = "caused by: " + msg;
    }
    groupStart(msg);
    log(t);
    if (t instanceof UmbrellaException) {
      final UmbrellaException umbrella = (UmbrellaException) t;
      for (final Throwable cause : umbrella.getCauses()) {
        logExceptionInSuperDev(cause, true);
      }
    } else if (t.getCause() != null) {
      logExceptionInSuperDev(t.getCause(), true);
    }
    groupEnd();
  }

  @Override
  public void onUncaughtException(final Throwable t) {
    super.onUncaughtException(t);
    logExceptionInSuperDev(t, false);
  }
}