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
package cc.kune.chat.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.FieldValidationUtil;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class NewRoomBtn.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewRoomBtn extends ButtonDescriptor {
  
  /**
   * The Class NewRoomAction.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class NewRoomAction extends RolAction {

    /** The Constant CANCEL_ID. */
    private static final String CANCEL_ID = "k-nrbt-cancel";
    
    /** The Constant CREATE_ID. */
    private static final String CREATE_ID = "k-nrbt-create";
    
    /** The Constant ID. */
    private static final String ID = "k-nrbt-dialog";
    
    /** The Constant TEXTBOX_ID. */
    private static final String TEXTBOX_ID = "k-nrbt-textbox";
    
    /** The content service. */
    private final Provider<ContentServiceAsync> contentService;
    
    /** The diag. */
    private PromptTopDialog diag;
    
    /** The i18n. */
    private final I18nTranslationService i18n;
    
    /** The session. */
    private final Session session;
    
    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new new room action.
     *
     * @param session the session
     * @param stateManager the state manager
     * @param i18n the i18n
     * @param contentService the content service
     */
    @Inject
    public NewRoomAction(final Session session, final StateManager stateManager,
        final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService) {
      super(AccessRolDTO.Administrator, true);
      this.session = session;
      this.stateManager = stateManager;
      this.i18n = i18n;
      this.contentService = contentService;
    }

    /* (non-Javadoc)
     * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      final Builder builder = new PromptTopDialog.Builder(ID, i18n.t("Name of the new chatroom?"),
          false, true, i18n.getDirection(), new OnEnter() {
            @Override
            public void onEnter() {
              doAction();
            }
          });
      builder.width("200px").height("50px").firstButtonTitle(i18n.t("Create")).sndButtonTitle(
          i18n.t("Cancel")).firstButtonId(CREATE_ID).sndButtonId(CANCEL_ID);
      FieldValidationUtil.restrictToUnixName(builder, TEXTBOX_ID);
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
          doAction();
        }
      });
    }

    /**
     * Do action.
     */
    private void doAction() {
      if (diag.isValid()) {
        NotifyUser.showProgress();
        final String groupShortName = session.getCurrentState().getGroup().getShortName();
        final StateToken parentToken = session.getContainerState().getRootContainer().getStateToken();
        contentService.get().addRoom(session.getUserHash(), parentToken,
            groupShortName + "-" + diag.getTextFieldValue(),
            new AsyncCallbackSimple<StateContainerDTO>() {
              @Override
              public void onSuccess(final StateContainerDTO state) {
                stateManager.removeCache(parentToken);
                stateManager.setRetrievedStateAndGo(state);
                NotifyUser.hideProgress();
                NotifyUser.info(i18n.t("Chatroom created"));
              }
            });
        diag.hide();
      }
    }
  }

  /**
   * Instantiates a new new room btn.
   *
   * @param i18n the i18n
   * @param action the action
   * @param res the res
   */
  @Inject
  public NewRoomBtn(final I18nTranslationService i18n, final NewRoomAction action,
      final IconicResources res) {
    super(action);
    this.withText(i18n.t("New room")).withToolTip(i18n.t("Create a new chat room")).withStyles(
        "k-def-docbtn, k-fl").withIcon(res.chatsAdd());
  }

}
