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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
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

public class WriteToAction extends AbstractExtendedAction {

  private final Provider<ContentServiceAsync> contentService;
  private final I18nTranslationService i18n;
  private boolean onlyToAdmins;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public WriteToAction(final Provider<ContentServiceAsync> contentService,
      final I18nTranslationService i18n, final Session session, final StateManager stateManager,
      final IconicResources res) {
    this.contentService = contentService;
    this.i18n = i18n;
    this.session = session;
    this.stateManager = stateManager;
    onlyToAdmins = false;
    putValue(AbstractAction.SMALL_ICON, res.write());
  }

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

  public void setOnlyToAdmin(final boolean onlyToAdmins) {
    this.onlyToAdmins = onlyToAdmins;
  }
}
