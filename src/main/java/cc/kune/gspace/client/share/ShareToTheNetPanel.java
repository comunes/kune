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

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.gspace.client.actions.share.ShareInDiggMenuItem;
import cc.kune.gspace.client.actions.share.ShareInFacebookMenuItem;
import cc.kune.gspace.client.actions.share.ShareInGPlusMenuItem;
import cc.kune.gspace.client.actions.share.ShareInIdenticaMenuItem;
import cc.kune.gspace.client.actions.share.ShareInLinkedinMenuItem;
import cc.kune.gspace.client.actions.share.ShareInRedditMenuItem;
import cc.kune.gspace.client.actions.share.ShareInTumblrMenuItem;
import cc.kune.gspace.client.actions.share.ShareInTwitterMenuItem;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ShareToTheNetPanel extends Composite implements ShareToTheNetView {
  private final TextBox linkToShare;

  @Inject
  public ShareToTheNetPanel(final ShareInTwitterMenuItem twitterItem,
      final ShareInGPlusMenuItem gPlusItem, final ShareInIdenticaMenuItem identicaItem,
      final ShareInLinkedinMenuItem linkedItem, final ShareInTumblrMenuItem tumblrItem,
      final ShareInDiggMenuItem diggItem, final ShareInRedditMenuItem redditItem,
      final ShareInFacebookMenuItem facebookItem, final ActionSimplePanel actionsPanel) {
    final FlowPanel flow = new FlowPanel();

    final Label shareThisLinkTitle = new Label(I18n.t("share this link via:"));
    shareThisLinkTitle.setStyleName("k-sharetonet-introtexts");

    final Label linkToShareIntro = new Label(I18n.t("Link to share:"));
    linkToShareIntro.addStyleName("k-sharetonet-introtexts");
    linkToShare = new TextBox();
    linkToShare.setVisibleLength(35);
    linkToShare.setReadOnly(true);
    linkToShare.addStyleName("k-sharelink-box");

    linkToShare.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        linkToShare.setSelectionRange(0, linkToShare.getValue().length());
      }
    });

    flow.add(linkToShareIntro);
    flow.add(linkToShare);
    flow.add(shareThisLinkTitle);

    actionsPanel.add(btnFrom(twitterItem));
    actionsPanel.add(btnFrom(gPlusItem));
    actionsPanel.add(btnFrom(facebookItem));
    actionsPanel.add(btnFrom(redditItem));
    actionsPanel.add(btnFrom(diggItem));
    actionsPanel.add(btnFrom(linkedItem));
    actionsPanel.add(btnFrom(tumblrItem));

    flow.add(actionsPanel);

    shareThisLinkTitle.addStyleName("k-fl");
    actionsPanel.addStyleName("k-fl");

    initWidget(flow);
  }

  private ButtonDescriptor btnFrom(final MenuItemDescriptor menuItem) {
    // We convert ShareIn menu items to small buttons
    final ButtonDescriptor btn = new ButtonDescriptor(menuItem);
    final String text = (String) menuItem.getValue(Action.NAME);
    btn.withParent(GuiActionDescrip.NO_PARENT).withText("").withToolTip(text).withStyles(
        ActionStyles.MENU_BTN_STYLE_NO_BORDER_LEFT);
    return btn;
  }

  @Override
  public void setLinkToShare(final String link) {
    linkToShare.setText(link);
  }
}
