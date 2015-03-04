/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

import br.com.rpa.client._paperelements.PaperIconButton;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BlinkAnimation;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.armor.resources.GSpaceArmorResources;
import cc.kune.polymer.client.PolymerId;
import cc.kune.polymer.client.PolymerUtils;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
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

  private final PaperIconButton groupButton;

  private final PaperIconButton homeButton;

  private final PaperIconButton userButton;

  /**
   * Instantiates a new space selector panel.
   *
   * @param armor
   *          the armor
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param sessionConstants
   *          the session
   */
  @Inject
  public SpaceSelectorPanel(final I18nTranslationService i18n, final GSpaceArmorResources res,    
final Session session) {

    homeButton = PaperIconButton.wrap(PolymerId.HOME_SPACE_ICON.getId());
    groupButton = PaperIconButton.wrap(PolymerId.GROUP_SPACE_ICON.getId());
    userButton = PaperIconButton.wrap(PolymerId.USER_SPACE_ICON.getId());

    final String siteCommonName = i18n.getSiteCommonName();
    
    session.onUserSignIn(true, new UserSignInHandler() {
      
      @Override
      public void onUserSignIn(UserSignInEvent event) {
        if (session.isNewbie()) {
          Tooltip.to(homeButton, i18n.t("Home page of [%s]", siteCommonName) + " (Alt+H)");
          Tooltip userSpaceTooltip = Tooltip.to(userButton,
              i18n.t("Inbox: it shows a list of all documents and contents " + "in which you participate")
              + " (Alt+I)");
          Tooltip groupSpaceTooltip = Tooltip.to(groupButton, i18n.t("Group and personal space: Where you can create "
              + "and publish contents for your personal or group web spaces")
              + " (Alt+G)");
          // publicSpaceTooltip = Tooltip.to(publicButton,
          // i18n.t("Public space: Where you can see a preview of how your Personal or "
          // + "Group Space looks like on the web")
          // + " (Alt+P)");
          // homeSpaceTooltip.setWidth(0);
          userSpaceTooltip.setWidth(190);
          groupSpaceTooltip.setWidth(170);

        }
      }
    });

    // publicSpaceTooltip.setWidth(150);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
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
  public void setGroupBtnActive(final boolean active) {
    groupButton.setActive(active);

  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setHomeBtnDown(boolean)
   */
  @Override
  public void setHomeBtnActive(final boolean active) {
    homeButton.setActive(active);
  }

  @Override
  public void setNarrowSwipeEnabled(final boolean enabled) {
    PolymerUtils.setNarrowSwipeEnabled(enabled);
  }

  @Override
  public void setNarrowVisible(final boolean visible) {
    PolymerUtils.setNarrowVisible(visible);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView
   * #setUserBtnDown(boolean)
   */
  @Override
  public void setUserBtnActive(final boolean active) {
    userButton.setActive(active);
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
}
