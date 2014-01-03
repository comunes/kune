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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class WriteToAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WriteToAction extends AbstractExtendedAction {

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The only to admins. */
  private boolean onlyToAdmins;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new write to action.
   * 
   * @param contentService
   *          the content service
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param res
   *          the res
   */
  @Inject
  public WriteToAction(final Provider<ContentServiceAsync> contentService,
      final I18nTranslationService i18n, final Session session, final StateManager stateManager,
      final IconicResources res) {
    this.contentService = contentService;
    this.i18n = i18n;
    this.session = session;
    this.stateManager = stateManager;
    onlyToAdmins = false;
    putValue(Action.SMALL_ICON, res.write());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    boolean isPerson = false;
    final StateToken token;
    if (event.getTarget() == null) {
      token = session.getCurrentStateToken();
    } else {
      isPerson = EventTargetUtils.isPerson(event);
      token = EventTargetUtils.getTargetToken(event);
    }
    NotifyUser.askConfirmation(
        i18n.t("Confirm, please:"),
        isPerson ? i18n.t("Do you want to write a message to your buddy?")
            : onlyToAdmins ? i18n.t("Do you want to write a message to the administrators of this group?")
                : i18n.t("Do you want to write a message to the members of this group?"),
        new SimpleResponseCallback() {
          @Override
          public void onCancel() {
            // Do nothing
          }

          @Override
          public void onSuccess() {
            contentService.get().writeTo(session.getUserHash(), token, onlyToAdmins,
                new AsyncCallbackSimple<String>() {
                  @Override
                  public void onSuccess(final String url) {
                    stateManager.gotoHistoryToken(url);
                    NotifyUser.info("Now you can edit this message");
                  }
                });
          }
        });
  }

  /**
   * Sets the only to admin.
   * 
   * @param onlyToAdmins
   *          the new only to admin
   */
  public void setOnlyToAdmin(final boolean onlyToAdmins) {
    this.onlyToAdmins = onlyToAdmins;
  }
}
