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

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.share.items.ShareItemFactory;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class ShareToListPanel is used as a list of users/groups a document is
 * shared to
 */
public class ShareToListPanel extends Composite implements ShareToListView, ShareToListOnItemRemoved {

  private static final String SCROLL_HEIGHT = "100px";
  private static final String SCROLLWIDTH = "380px";

  private final VerticalPanel itemsPanel;

  private final ScrollPanel scroll;

  private final Label title;

  private String typeId;

  /**
   * Instantiates a new abstract share to list panel.
   * 
   * @param titleText
   *          the title text
   */
  public ShareToListPanel() {
    title = new Label();
    title.setStyleName("k-sharelist-title");
    final VerticalPanel vp = new VerticalPanel();
    itemsPanel = new VerticalPanel();
    scroll = new ScrollPanel();
    scroll.setWidth(SCROLLWIDTH);
    DOM.setStyleAttribute(scroll.getElement(), "maxHeight", SCROLL_HEIGHT);
    scroll.setStyleName("k-sharelist-scroll");
    scroll.add(itemsPanel);
    vp.add(title);
    vp.add(scroll);
    initWidget(vp);
  }

  @Override
  public void addAdmin(final GroupDTO group) {
    addItemForSomeRol(group, AccessRolDTO.Administrator);
  }

  @Override
  public void addAdmin(final GroupDTO group, final boolean isMe) {
    addItemForSomeRol(group, AccessRolDTO.Administrator, isMe);
  }

  @Override
  public void addEditableByAnyone() {
    itemsPanel.add(ShareItemFactory.getContentEditableByAnyone().with(true));
  }

  @Override
  public void addEditor(final GroupDTO group) {
    addItemForSomeRol(group, AccessRolDTO.Editor);
  }

  private void addItemForSomeRol(final GroupDTO group, final AccessRolDTO rol) {
    addItemForSomeRol(group, rol, false);
  }

  private void addItemForSomeRol(final GroupDTO group, final AccessRolDTO rol, final boolean isMe) {
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      itemsPanel.add(ShareItemFactory.createListItem().with(rol, group, isMe));
    } else {
      itemsPanel.add(ShareItemFactory.createContentItem().with(rol, group, isMe));
    }
  }

  @Override
  public void addNotEditableByOthers() {
    itemsPanel.add(ShareItemFactory.getContentEditableByAnyone().with(false));
  }

  @Override
  public void addNotVisibleByOthers() {
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      itemsPanel.add(ShareItemFactory.getListPublicByAnyone().with(false));
    } else {
      itemsPanel.add(ShareItemFactory.getContentVisibleByAnyone().with(false));
    }
  }

  @Override
  public void addOwner(final GroupDTO owner) {
    itemsPanel.add(ShareItemFactory.getOwner().of(owner).with(typeId));
  }

  @Override
  public void addParticipant(final String waveParticipant) {
    addParticipant(waveParticipant, false);
  }

  @Override
  public void addParticipant(final String waveParticipant, final boolean isCreator) {
    itemsPanel.add(ShareItemFactory.getParticipant().of(waveParticipant, typeId, this, isCreator));
  }

  @Override
  public void addViewer(final GroupDTO group) {
    addItemForSomeRol(group, AccessRolDTO.Viewer);
  }

  @Override
  public void addVisibleByAnyone() {
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      itemsPanel.add(ShareItemFactory.getListPublicByAnyone().with(true));
    } else {
      itemsPanel.add(ShareItemFactory.getContentVisibleByAnyone().with(true));
    }
  }

  @Override
  public void clear() {
    scroll.setVerticalScrollPosition(0);
    itemsPanel.clear();
  }

  @Override
  public IsWidget getView() {
    return this;
  }

  @Override
  public void onRemove(final IsWidget widget) {
    itemsPanel.remove(widget);
  }

  @Override
  public void setTypeId(final String typeId) {
    this.typeId = typeId;
    final String titleText;
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      titleText = I18n.t("members:");
    } else {
      titleText = I18n.t("who can access:");
    }
    title.setText(titleText);
  }

}
