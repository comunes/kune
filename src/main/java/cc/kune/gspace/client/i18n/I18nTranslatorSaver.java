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
package cc.kune.gspace.client.i18n;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nTranslationDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslatorSaver.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class I18nTranslatorSaver {

  /** The i18n. */
  private final I18nUITranslationService i18n;

  /** The i18n service. */
  private final Provider<I18nServiceAsync> i18nService;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new i18n translator saver.
   * 
   * @param session
   *          the session
   * @param i18nService
   *          the i18n service
   * @param i18n
   *          the i18n
   */
  @Inject
  public I18nTranslatorSaver(final Session session, final Provider<I18nServiceAsync> i18nService,
      final I18nUITranslationService i18n) {
    this.session = session;
    this.i18nService = i18nService;
    this.i18n = i18n;
  }

  /**
   * Save.
   * 
   * @param item
   *          the item
   */
  public void save(final I18nTranslationDTO item) {
    NotifyUser.showProgress(i18n.t("Saving"));
    i18nService.get().setTranslation(session.getUserHash(), item.getId(), item.getText(),
        new AsyncCallback<String>() {
          @Override
          public void onFailure(final Throwable caught) {
            NotifyUser.hideProgress();
            if (caught instanceof AccessViolationException) {
              NotifyUser.error(
                  i18n.t("Only for authorized translators"),
                  i18n.t("To help with the translation of this software please contact before with this site administrators."));
            } else {
              NotifyUser.error(i18n.t("Server error saving the translation"));
            }
          }

          @Override
          public void onSuccess(final String result) {
            NotifyUser.hideProgress();
            i18n.setTranslationAfterSave(item.getTrKey(), result);
          }
        });
  }

}
