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

import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
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

  private final ShareDialogHelper helper;
  private final Session session;
  private final ShareToListView shareToListView;
  private final ShareToTheNetView shareToNetView;
  private final ShareToOthersView shareToOthersView;

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
      final ShareDialogProxy proxy, final ShareToListView shareToListView,
      final ShareToTheNetView shareToNetView, final ShareToOthersView shareToOthersView,
      final Session session, final ShareDialogHelper helper, final StateManager stateManager) {
    super(eventBus, view, proxy);
    this.shareToListView = shareToListView;
    this.shareToNetView = shareToNetView;
    this.shareToOthersView = shareToOthersView;
    this.session = session;
    this.helper = helper;
    this.helper.init(org.waveprotocol.box.webclient.client.Session.get().getDomain());
    stateManager.onStateChanged(false, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        if (getView().isVisible()) {
          getView().hide();
        }
      }
    });
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
    shareToListView.setTypeId(typeId);
    shareToOthersView.setTypeId(typeId);

    final AccessListsDTO acl = cnt.getAccessLists();
    final GroupDTO currentGroup = cnt.getGroup();
    if (cnt instanceof StateContentDTO) {
      final StateContentDTO content = (StateContentDTO) cnt;
      if (content.isWave()) {
        helper.setState(currentGroup, acl, typeId, content.getParticipants());
      } else {
        helper.setState(currentGroup, acl, typeId);
      }
    } else {
      helper.setState(currentGroup, acl, typeId);
    }
    shareToNetView.setLinkToShare(StateTokenUtils.getGroupSpaceUrl(session.getCurrentStateToken()));
    getView().show();
  }
}