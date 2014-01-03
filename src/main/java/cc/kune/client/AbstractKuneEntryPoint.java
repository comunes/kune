/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
import cc.kune.wave.client.WebClient.ErrorHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;

/**
 * The Class AbstractKuneEntryPoint is used to start kune in different
 * environments (kune full web app, or others like embed app).
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractKuneEntryPoint implements EntryPoint {

  /**
   * On module load continue.
   */
  protected abstract void onContinueModuleLoad();

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
   */
  @Override
  public void onModuleLoad() {
    onStartModuleLoad();

    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(final Throwable e) {
        Log.error("Error in 'onModuleLoad()' method", e);
        ErrorHandler.getStackTraceAsync(e, new Accessor<SafeHtml>() {
          @Override
          public void use(final SafeHtml stack) {
            final String message = stack.asString().replace("<br>", "\n");
            NotifyUser.logError(message);
            NotifyUser.showProgress("Error");
            new Timer() {
              @Override
              public void run() {
                NotifyUser.hideProgress();
              }
            }.schedule(5000);
          }
        });
      }
    });

    GWT.runAsync(new RunAsyncCallback() {
      @Override
      public void onFailure(final Throwable reason) {
        GWT.log("Error starting kune");
      }

      @Override
      public void onSuccess() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            onContinueModuleLoad();
          }
        });
      }
    });
  }

  /**
   * On start module load.
   */
  protected abstract void onStartModuleLoad();
}
