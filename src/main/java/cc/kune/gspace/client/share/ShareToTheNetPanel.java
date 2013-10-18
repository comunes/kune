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
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
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
    final Label label = new Label(I18n.t("share this link via:"));
    label.setStyleName("k-sharelist-title");
    flow.add(label);

    // Clone social net menu items
    final ButtonDescriptor twitterBtn = new ButtonDescriptor(twitterItem);
    final ButtonDescriptor gPlusBtn = new ButtonDescriptor(gPlusItem);
    final ButtonDescriptor identicaBtn = new ButtonDescriptor(identicaItem);

    twitterBtn.withParent(GuiActionDescrip.NO_PARENT).withText("").withStyles(
        ActionStyles.BTN_NO_BACK_NO_BORDER);
    gPlusBtn.withParent(GuiActionDescrip.NO_PARENT).withText("").withStyles(
        ActionStyles.BTN_NO_BACK_NO_BORDER);
    identicaBtn.withParent(GuiActionDescrip.NO_PARENT).withText("").withStyles(
        ActionStyles.BTN_NO_BACK_NO_BORDER);

    actionsPanel.add(twitterBtn);
    actionsPanel.add(gPlusBtn);
    actionsPanel.add(identicaBtn);

    flow.add(actionsPanel);

    label.addStyleName("k-fl");
    actionsPanel.addStyleName("k-fl");

    initWidget(flow);
  }
}
