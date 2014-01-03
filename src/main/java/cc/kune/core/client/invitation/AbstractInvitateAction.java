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

package cc.kune.core.client.invitation;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;
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

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractInvitateAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractInvitateAction extends RolActionAutoUpdated {

  /** The cancel id. */
  private final String cancelId;

  /** The diag. */
  private PromptTopDialog diag;

  /** The dialog id. */
  private final String dialogId;

  /** The invitation service. */
  private final Provider<InvitationServiceAsync> invitationService;

  /** The invite id. */
  private final String inviteId;

  /** The prompt text. */
  private final String promptText;

  /** The session. */
  private final Session session;

  /** The text box id. */
  private final String textBoxId;

  /** The title. */
  private final String title;

  /** The type. */
  private final InvitationType type;

  /**
   * Instantiates a new abstract invitate action.
   * 
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param rightsManager
   *          the rights manager
   * @param invitationService
   *          the invitation service
   * @param type
   *          the type
   * @param rolRequired
   *          the rol required
   * @param authNeed
   *          the auth need
   * @param visibleForNonMemb
   *          the visible for non memb
   * @param visibleForMembers
   *          the visible for members
   * @param title
   *          the title
   * @param promptText
   *          the prompt text
   * @param dialogId
   *          the dialog id
   * @param textBoxId
   *          the text box id
   * @param inviteId
   *          the invite id
   * @param cancelId
   *          the cancel id
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    final StateToken token;
    if (event.getTarget() instanceof AbstractContentSimpleDTO) {
      token = ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
    } else {
      token = session.getCurrentStateToken();
    }
    final Builder builder = new PromptTopDialog.Builder(dialogId, title, false, true,
        I18n.getDirection(), new OnEnter() {
          @Override
          public void onEnter() {
          }
        });
    builder.width("320px").height("120px").firstButtonTitle(I18n.t("Invite")).sndButtonTitle(
        I18n.t("Cancel")).firstButtonId(inviteId).sndButtonId(cancelId);
    builder.promptText(promptText).promptLines(3).emptyTextField(
        "simone@example.com, bertrand@example.com, luther@example.com").promptWidth(295);
    FieldValidationUtil.restrictToEmailList(builder, textBoxId);
    diag = builder.build();
    diag.showCentered();
    // diag.focusOnTextBox();
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

  /**
   * Do action.
   * 
   * @param token
   *          the token
   */
  private void doAction(final StateToken token) {
    if (diag.isValid()) {
      NotifyUser.showProgress();
      diag.hide();

      final AsyncCallbackSimple<Void> callback = new AsyncCallbackSimple<Void>() {
        @Override
        public void onSuccess(final Void val) {
          NotifyUser.hideProgress();

          NotifyUser.info(I18n.t("Invitation sent"));
        }
      };
      switch (type) {
      case TO_SITE:
        invitationService.get().inviteToSite(session.getUserHash(), token,
            TextUtils.emailStringToArray(diag.getTextFieldValue()), callback);
        break;
      case TO_GROUP:
        invitationService.get().inviteToGroup(session.getUserHash(), token,
            TextUtils.emailStringToArray(diag.getTextFieldValue()), callback);
        break;
      case TO_LISTS:
        invitationService.get().inviteToList(session.getUserHash(), token,
            TextUtils.emailStringToArray(diag.getTextFieldValue()), callback);
        break;
      default:
        break;
      }

    }
  }

}
