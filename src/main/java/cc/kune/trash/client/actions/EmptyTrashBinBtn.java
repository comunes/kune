/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

public class EmptyTrashBinBtn extends ButtonDescriptor {

  public static class EmptyTrashBinAction extends RolAction {

    private final ContentServiceHelper contentService;
    private final Session session;

    @Inject
    public EmptyTrashBinAction(final ContentServiceHelper contentService, final CoreResources res,
        final Session session) {
      super(AccessRolDTO.Administrator, true);
      this.contentService = contentService;
      this.session = session;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      contentService.purgeAll(session.getCurrentStateToken());
    }

  }
  private final HandlerRegistration reg;

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

  @Override
  public void onDetach() {
    super.onDetach();
    unregister();
  }

  private void setVisibility(final ContainerDTO ctn) {
    final boolean visible = ctn.getChilds().size() > 0 || ctn.getContents().size() > 0;
    setVisible(visible);
  }

  public void unregister() {
    reg.removeHandler();
  }

}
