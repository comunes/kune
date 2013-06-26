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

import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupListDTO;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class AbstractShareToListPanel is used as a list of users/groups a
 * document is shared to
 */
public abstract class AbstractShareToListPanel extends Composite implements ShareListView {

  private static final String SCROLL_HEIGHT = "50px";

  private final VerticalPanel itemsPanel;

  /**
   * Instantiates a new abstract share to list panel.
   * 
   * @param titleText
   *          the title text
   */
  AbstractShareToListPanel(final String titleText) {
    final Label title = new Label();
    title.setStyleName("k-sharelist-title");
    final VerticalPanel vp = new VerticalPanel();
    itemsPanel = new VerticalPanel();
    final ScrollPanel scroll = new ScrollPanel();
    // scroll.setHeight(SCROLL_HEIGHT);
    DOM.setStyleAttribute(scroll.getElement(), "maxHeight", SCROLL_HEIGHT);
    scroll.setStyleName("k-sharelist-scroll");
    scroll.add(itemsPanel);
    title.setText(titleText);
    vp.add(title);
    vp.add(scroll);
    initWidget(vp);
  }

  @Override
  public IsWidget getView() {
    return this;
  }

  @Override
  public void setState(final GroupDTO currentGroup, final GroupListDTO groupList) {
    itemsPanel.clear();
    if (groupList.getMode().equals(GroupListDTO.NOBODY)) {
      itemsPanel.add(ShareItemFactory.getShareToNobody());
    } else {
      if (groupList.getMode().equals(GroupListDTO.EVERYONE)) {
        itemsPanel.add(ShareItemFactory.getShareToEveryone());
      } else {
        for (final GroupDTO group : groupList.getList()) {
          final ShareToEntity entity = ShareItemFactory.getEntity();
          entity.init(group, !group.equals(currentGroup));
          itemsPanel.add(entity);
        }
      }
    }
  }

}
