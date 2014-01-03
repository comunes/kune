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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.sn.actions.conditions.IsCurrentStateEditableCondition;
import cc.kune.core.client.sn.actions.conditions.IsLoggedCondition;
import cc.kune.core.client.sn.actions.conditions.IsNotParticipantOfCurrentStateCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ParticipateInContentBtn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ParticipateInContentBtn extends ButtonDescriptor {

  /**
   * The Class ParticipateInContentAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class ParticipateInContentAction extends RolAction {

    /** The content service. */
    private final Provider<ContentServiceAsync> contentService;

    /** The session. */
    private final Session session;

    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new participate in content action.
     * 
     * @param session
     *          the session
     * @param stateManager
     *          the state manager
     * @param i18n
     *          the i18n
     * @param contentService
     *          the content service
     */
    @Inject
    public ParticipateInContentAction(final Session session, final StateManager stateManager,
        final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService) {
      super(AccessRolDTO.Editor, true);
      this.session = session;
      this.stateManager = stateManager;
      this.contentService = contentService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.showProgress();
      contentService.get().addParticipant(session.getUserHash(), session.getCurrentStateToken(),
          session.getCurrentUser().getShortName(), new AsyncCallbackSimple<Boolean>() {
            @Override
            public void onSuccess(final Boolean arg) {
              NotifyUser.hideProgress();
              stateManager.refreshCurrentStateWithoutCache();
              // issue #73
              // stateManager.gotoStateToken(session.getCurrentStateToken());
            }
          });
    }
  }

  /**
   * Instantiates a new participate in content btn.
   * 
   * @param i18n
   *          the i18n
   * @param action
   *          the action
   * @param isLogged
   *          the is logged
   * @param isEditable
   *          the is editable
   * @param isNotParticipant
   *          the is not participant
   * @param res
   *          the res
   */
  @Inject
  public ParticipateInContentBtn(final I18nTranslationService i18n,
      final ParticipateInContentAction action, final IsLoggedCondition isLogged,
      final IsCurrentStateEditableCondition isEditable,
      final IsNotParticipantOfCurrentStateCondition isNotParticipant, final IconicResources res) {
    super(action);
    this.withText(i18n.t("Participate")).withToolTip(i18n.t("Participate in the edition of this page")).withIcon(
        res.editGrey()).withStyles("k-def-docbtn, k-fl");
    super.add(isLogged);
    super.add(isEditable);
    super.add(isNotParticipant);
  }
}
