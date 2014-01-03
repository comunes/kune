/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView;
import cc.kune.core.shared.SessionConstants;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.armor.resources.GSpaceArmorResources;

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

// TODO: Auto-generated Javadoc
/**
 * The Class SpaceSelectorPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SpaceSelectorPanel extends ViewImpl implements SpaceSelectorView {

  /**
   * The Interface SpaceSelectorPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface SpaceSelectorPanelUiBinder extends UiBinder<Widget, SpaceSelectorPanel> {
  }

  /** The ui binder. */
  private static SpaceSelectorPanelUiBinder uiBinder = GWT.create(SpaceSelectorPanelUiBinder.class);

  /** The group button. */
  @UiField
  ToggleButton groupButton;

  /** The group space tooltip. */
  private final Tooltip groupSpaceTooltip;

  /** The home button. */
  @UiField
  ToggleButton homeButton;

  /** The home space tooltip. */
  private final Tooltip homeSpaceTooltip;

  /** The panel. */
  @UiField
  FlowPanel panel;

  /** The public button. */
  @UiField
  ToggleButton publicButton;

  /** The public space tooltip. */
  private final Tooltip publicSpaceTooltip;

  /** The user button. */
  @UiField
  ToggleButton userButton;

  /** The user space tooltip. */
  private final Tooltip userSpaceTooltip;

  /**
   * Instantiates a new space selector panel.
   * 
   * @param armor
   *          the armor
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param session
   *          the session
   */
  @Inject
  public SpaceSelectorPanel(final GSpaceArmor armor, final I18nTranslationService i18n,
      final GSpaceArmorResources res, final SessionConstants session) {
    armor.getSitebar().insert(uiBinder.createAndBindUi(this), 0);
    // homeButton.setVisible(false);
    final String siteCommonName = i18n.getSiteCommonName();
    homeSpaceTooltip = Tooltip.to(homeButton, i18n.t("Your home page in [%s]", siteCommonName)
        + " (Alt+H)");
    userSpaceTooltip = Tooltip.to(userButton,
        i18n.t("Inbox: it shows a list of all documents and contents " + "in which you participate")
            + " (Alt+I)");
    groupSpaceTooltip = Tooltip.to(groupButton, i18n.t("Group and personal space: Where you can create "
        + "and publish contents for your personal or group web spaces")
        + " (Alt+G)");
    publicSpaceTooltip = Tooltip.to(publicButton,
        i18n.t("Public space: Where you can see a preview of how your Personal or "
            + "Group Space looks like on the web")
            + " (Alt+P)");
    // homeSpaceTooltip.setWidth(0);
    userSpaceTooltip.setWidth(190);
    groupSpaceTooltip.setWidth(170);
    publicSpaceTooltip.setWidth(150);

    homeButton.ensureDebugId(HOME_SPACE_ID);
    userButton.ensureDebugId(USER_SPACE_ID);
    groupButton.ensureDebugId(GROUP_SPACE_ID);
    publicButton.ensureDebugId(PUBLIC_SPACE_ID);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return panel;
  }

  /**
   * Blink.
   * 
   * @param btn
   *          the btn
   */
  private void blink(final UIObject btn) {
    final BlinkAnimation anim = new BlinkAnimation(btn, 400);
    anim.animate(3);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #blinkGroupBtn()
   */
  @Override
  public void blinkGroupBtn() {
    blink(groupButton);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #blinkHomeBtn()
   */
  @Override
  public void blinkHomeBtn() {
    blink(homeButton);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #blinkPublicBtn()
   */
  @Override
  public void blinkPublicBtn() {
    blink(publicButton);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #blinkUserBtn()
   */
  @Override
  public void blinkUserBtn() {
    blink(userButton);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #getGroupBtn()
   */
  @Override
  public HasClickHandlers getGroupBtn() {
    return groupButton;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #getHomeBtn()
   */
  @Override
  public HasClickHandlers getHomeBtn() {
    return homeButton;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #getPublicBtn()
   */
  @Override
  public HasClickHandlers getPublicBtn() {
    return publicButton;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #getUserBtn()
   */
  @Override
  public HasClickHandlers getUserBtn() {
    return userButton;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setGroupBtnDown(boolean)
   */
  @Override
  public void setGroupBtnDown(final boolean down) {
    groupButton.setDown(down);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setHomeBtnDown(boolean)
   */
  @Override
  public void setHomeBtnDown(final boolean down) {
    homeButton.setDown(down);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setPublicBtnDown(boolean)
   */
  @Override
  public void setPublicBtnDown(final boolean down) {
    publicButton.setDown(down);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setPublicVisible(boolean)
   */
  @Override
  public void setPublicVisible(final boolean visible) {
    publicButton.setVisible(visible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setUserBtnDown(boolean)
   */
  @Override
  public void setUserBtnDown(final boolean down) {
    userButton.setDown(down);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setWindowTitle(java.lang.String)
   */
  @Override
  public void setWindowTitle(final String title) {
    Window.setTitle(title);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #showGroupSpaceTooltip()
   */
  @Override
  public void showGroupSpaceTooltip() {
    groupSpaceTooltip.showTemporally();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #showHomeSpaceTooltip()
   */
  @Override
  public void showHomeSpaceTooltip() {
    homeSpaceTooltip.showTemporally();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #showPublicSpaceTooltip()
   */
  @Override
  public void showPublicSpaceTooltip() {
    publicSpaceTooltip.showTemporally();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #showUserSpaceTooltip()
   */
  @Override
  public void showUserSpaceTooltip() {
    userSpaceTooltip.showTemporally();
  }
}
