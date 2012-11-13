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

package cc.kune.core.client.invitation;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.actions.FieldValidationUtil;
import cc.kune.core.client.actions.RolActionAutoUpdated;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.InvitationServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Provider;

public abstract class AbstractInvitateAction extends RolActionAutoUpdated {

  private final String cancelId;
  private PromptTopDialog diag;
  private final String dialogId;
  private final Provider<InvitationServiceAsync> invitationService;
  private final String inviteId;
  private final String promptText;
  private final Session session;
  private final String textBoxId;
  private final String title;
  private final InvitationType type;

  public AbstractInvitateAction(final StateManager stateManager, final Session session,
      final AccessRightsClientManager rightsManager,
      final Provider<InvitationServiceAsync> invitationService, final InvitationType type,
      final AccessRolDTO rolRequired, final boolean authNeed, final boolean visibleForNonMemb,
      final boolean visibleForMembers, final String title, final String promptText,
      final String dialogId, final String textBoxId, final String inviteId, final String cancelId) {
    super(stateManager, session, rightsManager, rolRequired, authNeed, visibleForNonMemb,
        visibleForMembers);
    this.session = session;
    this.type = type;
    this.invitationService = invitationService;
    this.title = title;
    this.promptText = promptText;
    this.dialogId = dialogId;
    this.textBoxId = textBoxId;
    this.inviteId = inviteId;
    this.cancelId = cancelId;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    final StateToken token;
    if (event.getTarget() instanceof AbstractContentSimpleDTO) {
      token = ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
    } else {
      token = session.getCurrentStateToken();
    }
    final Builder builder = new PromptTopDialog.Builder(dialogId, promptText, false, true,
        I18n.getDirection(), new OnEnter() {
          @Override
          public void onEnter() {
            doAction(token);
          }
        });
    builder.width("300px").height("50px").firstButtonTitle(I18n.t("Invite")).sndButtonTitle(
        I18n.t("Cancel")).firstButtonId(inviteId).sndButtonId(cancelId).width(270).title(title);
    FieldValidationUtil.restrictToEmailList(builder, textBoxId);
    diag = builder.build();
    diag.showCentered();
    diag.focusOnTextBox();
    diag.getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        diag.hide();
      }
    });
    diag.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        doAction(token);
      }
    });
  }

  private void doAction(final StateToken token) {
    if (diag.isValid()) {
      NotifyUser.showProgress();
      diag.hide();

      invitationService.get().invite(session.getUserHash(), type, token,
          toArray(diag.getTextFieldValue()), new AsyncCallbackSimple<Void>() {
            @Override
            public void onSuccess(final Void val) {
              NotifyUser.hideProgress();

              NotifyUser.info(I18n.t("Invitation sent"));
            }
          });

    }
  }

  private String[] toArray(final String textFieldValue) {
    final String[] splitted = textFieldValue.split(",");
    final String[] withoutSpaces = new String[splitted.length];
    for (int i = 0; i < splitted.length; i++) {
      withoutSpaces[i] = splitted[i].trim();
    }
    return withoutSpaces;
  }

}
