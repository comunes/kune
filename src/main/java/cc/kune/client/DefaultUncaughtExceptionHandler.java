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

import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.wave.client.WebClient.ErrorHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Timer;

public class DefaultUncaughtExceptionHandler implements GWT.UncaughtExceptionHandler {
  @Override
  public void onUncaughtException(final Throwable excep) {
    final Throwable unwrapped = unwrap(excep);
    showErrorMessage(unwrapped);
  }

  private void showErrorMessage(final Throwable unwrapped) {
    Log.error("Error in 'onModuleLoad()' method", unwrapped);

    ErrorHandler.getStackTraceAsync(unwrapped, new Accessor<SafeHtml>() {
      @Override
      public void use(final SafeHtml stack) {
        final String message = stack.asString().replace("<br>", "\n");
        NotifyUser.logError(message);
        NotifyUser.showProgress();
        NotifyUser.error(
            I18n.t("We're sorry..."),
            I18n.t("For some reason [%s] is currently experiencing errors. "
                + "Try again refreshing your browser. "
                + "If the problem persist, please provide us feedback with more info "
                + "(see it in topbar menu > Errors info) so we can try to fix it. Thanks",
                I18n.getSiteCommonName()), true);
        new Timer() {
          @Override
          public void run() {
            NotifyUser.hideProgress();
          }
        }.schedule(5000);
      }
    });
  }

  public Throwable unwrap(final Throwable e) {
    if (e instanceof UmbrellaException) {
      final UmbrellaException ue = (UmbrellaException) e;
      if (ue.getCauses().size() == 1) {
        return unwrap(ue.getCauses().iterator().next());
      }
    }
    return e;
  }

}