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
package cc.kune.trash.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class EmptyTrashBinBtn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EmptyTrashBinBtn extends ButtonDescriptor {

  /**
   * The Class EmptyTrashBinAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class EmptyTrashBinAction extends RolAction {

    /** The content service. */
    private final ContentServiceHelper contentService;

    /** The session. */
    private final Session session;

    /**
     * Instantiates a new empty trash bin action.
     * 
     * @param contentService
     *          the content service
     * @param res
     *          the res
     * @param session
     *          the session
     */
    @Inject
    public EmptyTrashBinAction(final ContentServiceHelper contentService, final CoreResources res,
        final Session session) {
      super(AccessRolDTO.Administrator, true);
      this.contentService = contentService;
      this.session = session;
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
      contentService.purgeAll(session.getCurrentStateToken());
    }

  }

  /** The reg. */
  private final HandlerRegistration reg;

  /**
   * Instantiates a new empty trash bin btn.
   * 
   * @param action
   *          the action
   * @param stateManager
   *          the state manager
   */
  @Inject
  public EmptyTrashBinBtn(final EmptyTrashBinAction action, final StateManager stateManager) {
    super(action);
    this.withText(I18n.t("Empty trash bin")); // .withIcon(res.cancel());
    reg = stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        if (state instanceof StateContainerDTO) {
          final ContainerDTO ctn = ((StateContainerDTO) state).getContainer();
          if (ctn.getTypeId().equals(TrashToolConstants.TYPE_ROOT)) {
            setVisibility(ctn);
          }
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip#onDetach
   * ()
   */
  @Override
  public void onDetach() {
    super.onDetach();
    unregister();
  }

  /**
   * Sets the visibility.
   * 
   * @param ctn
   *          the new visibility
   */
  private void setVisibility(final ContainerDTO ctn) {
    final boolean visible = ctn.getChilds().size() > 0 || ctn.getContents().size() > 0;
    setVisible(visible);
  }

  /**
   * Unregister.
   */
  public void unregister() {
    reg.removeHandler();
  }

}
