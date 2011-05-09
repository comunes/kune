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
package cc.kune.gspace.client.viewers;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.ui.EditEvent;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.RenameAction;
import cc.kune.gspace.client.actions.RenameListener;
import cc.kune.gspace.client.tool.ContentViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class ContentViewerPresenter extends
    Presenter<ContentViewerPresenter.ContentViewerView, ContentViewerPresenter.ContentViewerProxy>
    implements ContentViewer {

  @ProxyCodeSplit
  public interface ContentViewerProxy extends Proxy<ContentViewerPresenter> {
  }

  public interface ContentViewerView extends View {

    void attach();

    void clear();

    void detach();

    HasEditHandler getEditTitle();

    void setActions(GuiActionDescCollection actions);

    void setContent(StateContentDTO state);

    void setEditableContent(StateContentDTO state);

    void setEditableTitle(String title);

    void signIn();

    void signOut();
  }

  private final ActionRegistryByType actionsRegistry;
  private HandlerRegistration editHandler;
  private final Provider<RenameAction> renameAction;
  private final Session session;

  @Inject
  public ContentViewerPresenter(final EventBus eventBus, final ContentViewerView view,
      final ContentViewerProxy proxy, final Session session, final ActionRegistryByType actionsRegistry,
      final Provider<RenameAction> renameAction) {
    super(eventBus, view, proxy);
    this.session = session;
    this.actionsRegistry = actionsRegistry;
    this.renameAction = renameAction;
    session.onUserSignOut(true, new UserSignOutHandler() {
      @Override
      public void onUserSignOut(final UserSignOutEvent event) {
        getView().signOut();
      }
    });
    session.onUserSignIn(true, new UserSignInHandler() {

      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        getView().signIn();
      }
    });

  }

  @Override
  public void attach() {
    getView().attach();
    if (editHandler == null) {
      createEditHandler();
    }
  }

  private void createEditHandler() {
    editHandler = getView().getEditTitle().addEditHandler(new EditHandler() {
      @Override
      public void fire(final EditEvent event) {
        renameAction.get().rename(session.getCurrentStateToken(), session.getCurrentState().getTitle(),
            event.getText(), new RenameListener() {
              @Override
              public void onFail(final StateToken token, final String oldTitle) {
                getView().setEditableTitle(oldTitle);
              }

              @Override
              public void onSuccess(final StateToken token, final String title) {
                getView().setEditableTitle(title);
              }
            });
      }
    });
  }

  @Override
  public void detach() {
    getView().detach();
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    getView().clear();
    final StateContentDTO stateContent = (StateContentDTO) state;
    final AccessRights rights = stateContent.getContentRights();
    if (session.isLogged() && rights.isEditable()) {
      if (stateContent.isParticipant()) {
        // is already participant, show wave editor
        if (org.waveprotocol.box.webclient.client.Session.get().isLoggedIn()) {
          getView().setEditableContent(stateContent);
        } else {
          getView().setContent(stateContent);
          // When logged setEditable!
        }
      } else {
        // add "participate" action
        getView().setContent(stateContent);
      }
    } else {
      if (rights.isVisible()) {
        // Show contents
        getView().setContent(stateContent);
      } else {
        throw new UIException("Unexpected status in Viewer");
      }
    }
    final GuiActionDescCollection actions = actionsRegistry.getCurrentActions(stateContent.getGroup(),
        stateContent.getTypeId(), session.isLogged(), rights, ActionGroups.VIEW);
    getView().setActions(actions);
  }
}