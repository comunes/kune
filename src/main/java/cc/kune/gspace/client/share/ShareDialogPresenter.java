/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share;

/**
 * The Class ShareDialogPresenter, allows to set up how a content is shared to others
 */

import java.util.List;

import cc.kune.common.client.log.Log;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.lists.client.rpc.ListsServiceHelper;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * The Class ShareDialogPresenter.
 */
public class ShareDialogPresenter extends
    Presenter<ShareDialogPresenter.ShareDialogView, ShareDialogPresenter.ShareDialogProxy> implements
    ShareDialog {

  public interface OnAddGroupListener {
    void onAdd(String groupName);
  }
  /**
   * The Interface ShareDialogProxy.
   */
  @ProxyCodeSplit
  public interface ShareDialogProxy extends Proxy<ShareDialogPresenter> {
  }

  /**
   * The Interface ShareDialogView.
   */
  public interface ShareDialogView extends View {

    void hide();

    boolean isVisible();

    void setTypeId(String typeId);

    void show();

  }

  private final Provider<ContentServiceHelper> contentService;
  private final ShareDialogHelper helper;
  private final Provider<ListsServiceHelper> listService;
  private final Session session;
  private final Provider<ShareToListView> shareToListView;
  private final Provider<ShareToTheNetView> shareToNetView;
  private final Provider<ShareToOthersView> shareToOthersView;

  /**
   * Instantiates a new share dialog presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   */
  @Inject
  public ShareDialogPresenter(final EventBus eventBus, final ShareDialogView view,
      final ShareDialogProxy proxy, final Provider<ShareToListView> shareToListView,
      final Provider<ShareToTheNetView> shareToNetView,
      final Provider<ShareToOthersView> shareToOthersView, final Session session,
      final ShareDialogHelper helper, final StateManager stateManager,
      final Provider<ContentServiceHelper> contentService, final Provider<ListsServiceHelper> listService) {
    super(eventBus, view, proxy);
    this.shareToListView = shareToListView;
    this.shareToNetView = shareToNetView;
    this.shareToOthersView = shareToOthersView;
    this.session = session;
    this.helper = helper;
    this.contentService = contentService;
    this.listService = listService;
    this.helper.init(org.waveprotocol.box.webclient.client.Session.get().getDomain());
    eventBus.addHandler(SpaceSelectEvent.getType(), new SpaceSelectEvent.SpaceSelectHandler() {
      @Override
      public void onSpaceSelect(final SpaceSelectEvent event) {
        hideIfVisible();
      }
    });
    stateManager.onStateChanged(false, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        hideIfVisible();
      }

    });
  }

  private void hideIfVisible() {
    if (getView().isVisible()) {
      getView().hide();
    }
  }

  @Override
  protected void onBind() {
    super.onBind();
    final OnAddGroupListener addListener = new OnAddGroupListener() {
      @Override
      public void onAdd(final String groupName) {
        final StateContainerDTO cnt = (StateContainerDTO) session.getCurrentState();
        final String typeId = cnt.getTypeId();
        final SimpleCallback onAdd = new SimpleCallback() {
          @Override
          public void onCallback() {
            // FIXME (vjrj) I don't know if this is correct for this
            shareToListView.get().addParticipant(groupName);
          }
        };
        if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
          listService.get().subscribeAnUserToList(cnt.getStateToken(), groupName, true, onAdd);
        } else {
          if (cnt instanceof StateContentDTO) {
            final StateContentDTO content = (StateContentDTO) cnt;
            if (content.isWave()) {
              final List<String> parts = content.getWaveParticipants();
              Log.debug("Share Dialog: trying to add: " + groupName + " to part. list: "
                  + parts.toString());
              contentService.get().addParticipant(cnt.getStateToken(), groupName, onAdd);
            }
          }
        }
      }
    };
    shareToOthersView.get().onAddGroupListener(addListener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void show() {
    final StateContainerDTO cnt = (StateContainerDTO) session.getCurrentState();

    // Configure behavior if is a doc or a list
    final String typeId = cnt.getTypeId();
    getView().setTypeId(typeId);
    shareToListView.get().setTypeId(typeId);
    shareToOthersView.get().setTypeId(typeId);

    final AccessListsDTO acl = cnt.getAccessLists();
    final GroupDTO currentGroup = cnt.getGroup();
    final String currentUser = session.getCurrentUser().getShortName();
    if (cnt instanceof StateContentDTO) {
      final StateContentDTO content = (StateContentDTO) cnt;
      if (content.isWave()) {
        helper.setState(currentGroup, currentUser, acl, typeId, content.getWaveCreator(),
            content.getWaveParticipants());
      } else {
        helper.setState(currentGroup, currentUser, acl, typeId);
      }
    } else {
      helper.setState(currentGroup, currentUser, acl, typeId);
    }
    shareToNetView.get().setLinkToShare(StateTokenUtils.getGroupSpaceUrl(session.getCurrentStateToken()));
    getView().show();
  }
}