/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.core.client.cookies;

import java.util.Date;

import org.gwtbootstrap3.client.ui.base.button.CustomButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SiteServiceAsync;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.shared.dto.MotdDTO;

@Singleton
public class MotdManager {

  @Inject
  public MotdManager(final SiteServiceAsync siteService) {
    SessionInstance.get().onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        final Timer timer = new Timer() {

          @Override
          public void run() {
            siteService.getMotd(SessionInstance.get().getUserHash(), new AsyncCallbackSimple<MotdDTO>() {

              @Override
              public void onSuccess(MotdDTO motd) {
                if (motd == null) {
                  Log.debug("No motd message");
                  return;
                }

                final String cookieName = motd.getCookieName();

                final String motdCookie = Cookies.getCookie(cookieName);

                if (motdCookie == null) {

                  final Builder builder = new BasicTopDialog.Builder("k-motd", true, false,
                      I18n.getDirection());

                  // motdDialog.addStyleName("k-motd-dialog");

                  if (motd.getTitle() != null) {
                    builder.title(motd.getTitle());
                  }

                  final BasicTopDialog dialog = builder.build();

                  dialog.setFirstBtnText(motd.getOkBtnText());

                  dialog.setCloseBtnVisible(true);

                  Log.debug("motd message: " + motd.getMessage());

                  final HTML message = new HTML(motd.getMessage() + motd.getMessageBottom());
                  dialog.getInnerPanel().add(message);

                  dialog.setSecondBtnText(motd.getCloseBtnText());
                  dialog.getSecondBtn().addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(final ClickEvent event) {
                      setCookie(cookieName, CookieUtils.inDays(90));
                      dialog.hide();
                    }
                  });
                  if (motd.getShouldRemember() > 0) {
                    final CustomButton laterBtn = new CustomButton((I18n.t("Maybe later")));
                    laterBtn.addClickHandler(new ClickHandler() {
                      @Override
                      public void onClick(final ClickEvent event) {
                        setCookie(cookieName, CookieUtils.inDays(motd.getShouldRemember()));
                        dialog.hide();
                      }
                    });
                    dialog.getBtnPanel().add(laterBtn);
                  }

                  dialog.getFirstBtn().addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(final ClickEvent event) {
                      setCookie(cookieName, CookieUtils.inDays(7));
                      KuneWindowUtils.open(motd.getOkBtnUrl());
                      dialog.hide();
                    }
                  });

                  dialog.show();
                }
              }
            });
          }
        };
        timer.schedule(10000);
      }
    });
  }

  private void setCookie(final String motdCookieName, final Date expires) {
    Cookies.removeCookie(motdCookieName);
    Cookies.setCookie(motdCookieName, null, expires, null, "/", WindowUtils.isHttps());
  }

}
