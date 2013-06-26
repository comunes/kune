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

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.gspace.client.actions.share.ShareInGPlusMenuItem;
import cc.kune.gspace.client.actions.share.ShareInIdenticaMenuItem;
import cc.kune.gspace.client.actions.share.ShareInTwitterMenuItem;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class ShareToTheNetPanel extends Composite {

  @Inject
  public ShareToTheNetPanel(final ShareInTwitterMenuItem twitterItem,
      final ShareInGPlusMenuItem gPlusItem, final ShareInIdenticaMenuItem identicaItem,
      final ActionSimplePanel actionsPanel) {
    final FlowPanel flow = new FlowPanel();
    final Label label = new Label(I18n.t("or share via:"));
    label.setStyleName("k-sharelist-title");
    flow.add(label);

    final MenuDescriptor menu = new MenuDescriptor(I18n.t("social nets"));
    menu.withStyles(ActionStyles.BTN_NO_BACK_NO_BORDER);
    menu.setVertical(false);
    twitterItem.withParent(menu, false).withText("");
    gPlusItem.withParent(menu, false).withText("");
    identicaItem.withParent(menu, false).withText("");
    actionsPanel.add(menu);
    actionsPanel.add(twitterItem);
    actionsPanel.add(gPlusItem);
    actionsPanel.add(identicaItem);
    flow.add(actionsPanel);

    label.addStyleName("k-fl");
    actionsPanel.addStyleName("k-fl");

    initWidget(flow);
  }
}
