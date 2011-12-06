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
package cc.kune.core.client.sitebar.spaces;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BlinkAnimation;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.resources.GSpaceArmorResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SpaceSelectorPanel extends ViewImpl implements SpaceSelectorView {
  interface SpaceSelectorPanelUiBinder extends UiBinder<Widget, SpaceSelectorPanel> {
  }
  private static SpaceSelectorPanelUiBinder uiBinder = GWT.create(SpaceSelectorPanelUiBinder.class);
  @UiField
  ToggleButton groupButton;
  private final Tooltip groupSpaceTooltip;
  @UiField
  ToggleButton homeButton;
  private final Tooltip homeSpaceTooltip;
  @UiField
  FlowPanel panel;
  @UiField
  ToggleButton publicButton;
  private final Tooltip publicSpaceTooltip;
  @UiField
  ToggleButton userButton;
  private final Tooltip userSpaceTooltip;

  @Inject
  public SpaceSelectorPanel(final GSpaceArmor armor, final I18nUITranslationService i18n,
      final GSpaceArmorResources res, final Session session) {
    armor.getSitebar().insert(uiBinder.createAndBindUi(this), 0);
    // homeButton.setVisible(false);
    final String siteCommonName = i18n.getSiteCommonName();
    homeSpaceTooltip = Tooltip.to(homeButton, i18n.t("Your home page in [%s]", siteCommonName));
    userSpaceTooltip = Tooltip.to(
        userButton,
        i18n.t("User space: it shows a list of all documents and contents " + "in which you participate"));
    groupSpaceTooltip = Tooltip.to(groupButton, i18n.t("Group and personal space: Where you can create "
        + "and publish contents for your personal or group web spaces"));
    publicSpaceTooltip = Tooltip.to(publicButton,
        i18n.t("Public space: Where you can see a preview of how your Personal or "
            + "Group Space looks like on the web"));
    // homeSpaceTooltip.setWidth(0);
    userSpaceTooltip.setWidth(190);
    groupSpaceTooltip.setWidth(170);
    publicSpaceTooltip.setWidth(150);

    homeButton.ensureDebugId(HOME_SPACE_ID);
    userButton.ensureDebugId(USER_SPACE_ID);
    groupButton.ensureDebugId(GROUP_SPACE_ID);
    publicButton.ensureDebugId(PUBLIC_SPACE_ID);
  }

  @Override
  public Widget asWidget() {
    return panel;
  }

  private void blink(final UIObject btn) {
    final BlinkAnimation anim = new BlinkAnimation(btn, 400);
    anim.animate(3);
  }

  @Override
  public void blinkGroupBtn() {
    blink(groupButton);
  }

  @Override
  public void blinkHomeBtn() {
    blink(homeButton);
  }

  @Override
  public void blinkPublicBtn() {
    blink(publicButton);
  }

  @Override
  public void blinkUserBtn() {
    blink(userButton);
  }

  @Override
  public HasClickHandlers getGroupBtn() {
    return groupButton;
  }

  @Override
  public HasClickHandlers getHomeBtn() {
    return homeButton;
  }

  @Override
  public HasClickHandlers getPublicBtn() {
    return publicButton;
  }

  @Override
  public HasClickHandlers getUserBtn() {
    return userButton;
  }

  @Override
  public void setGroupBtnDown(final boolean down) {
    groupButton.setDown(down);
  }

  @Override
  public void setHomeBtnDown(final boolean down) {
    homeButton.setDown(down);
  }

  @Override
  public void setPublicBtnDown(final boolean down) {
    publicButton.setDown(down);
  }

  @Override
  public void setUserBtnDown(final boolean down) {
    userButton.setDown(down);
  }

  @Override
  public void setWindowTitle(final String title) {
    Window.setTitle(title);
  }

  @Override
  public void showGroupSpaceTooltip() {
    groupSpaceTooltip.showTemporally();
  }

  @Override
  public void showHomeSpaceTooltip() {
    homeSpaceTooltip.showTemporally();
  }

  @Override
  public void showPublicSpaceTooltip() {
    publicSpaceTooltip.showTemporally();
  }

  @Override
  public void showUserSpaceTooltip() {
    userSpaceTooltip.showTemporally();
  }
}
