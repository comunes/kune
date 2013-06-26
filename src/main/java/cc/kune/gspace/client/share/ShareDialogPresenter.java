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

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

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
    void show();
  }

  private final ShareToAdminsView adminsView;
  private final ShareToCollabsView collabsView;
  private final Session session;
  private final ShareToViewersView viewersView;

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
      final ShareDialogProxy proxy, final ShareToAdminsView adminsView,
      final ShareToCollabsView collabsView, final ShareToViewersView viewersView, final Session session) {
    super(eventBus, view, proxy);
    this.adminsView = adminsView;
    this.collabsView = collabsView;
    this.viewersView = viewersView;
    this.session = session;
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
    final AccessListsDTO acl = cnt.getAccessLists();
    final GroupDTO currentGroup = cnt.getGroup();
    adminsView.setState(currentGroup, acl.getAdmins());
    collabsView.setState(currentGroup, acl.getEditors());
    viewersView.setState(currentGroup, acl.getViewers());
    getView().show();
  }
}