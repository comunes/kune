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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.style.GSpaceBackgroundManager;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SpaceSelectorPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SpaceSelectorPresenter extends
    Presenter<SpaceSelectorPresenter.SpaceSelectorView, SpaceSelectorPresenter.SpaceSelectorProxy> {

  /**
   * The Interface SpaceSelectorProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface SpaceSelectorProxy extends Proxy<SpaceSelectorPresenter> {
  }

  /**
   * The Interface SpaceSelectorView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SpaceSelectorView extends View {

    /** The group space id. */
    public static String GROUP_SPACE_ID = "k-space-group-id";

    /** The home space id. */
    public static String HOME_SPACE_ID = "k-space-home-id";

    /** The public space id. */
    public static String PUBLIC_SPACE_ID = "k-space-public-id";

    /** The user space id. */
    public static String USER_SPACE_ID = "k-space-user-id";

    /**
     * Blink group btn.
     */
    void blinkGroupBtn();

    /**
     * Blink home btn.
     */
    void blinkHomeBtn();

    /**
     * Blink public btn.
     */
    void blinkPublicBtn();

    /**
     * Blink user btn.
     */
    void blinkUserBtn();

    /**
     * Gets the group btn.
     * 
     * @return the group btn
     */
    HasClickHandlers getGroupBtn();

    /**
     * Gets the home btn.
     * 
     * @return the home btn
     */
    HasClickHandlers getHomeBtn();

    /**
     * Gets the public btn.
     * 
     * @return the public btn
     */
    HasClickHandlers getPublicBtn();

    /**
     * Gets the user btn.
     * 
     * @return the user btn
     */
    HasClickHandlers getUserBtn();

    /**
     * Sets the group btn down.
     * 
     * @param down
     *          the new group btn down
     */
    void setGroupBtnDown(boolean down);

    /**
     * Sets the home btn down.
     * 
     * @param down
     *          the new home btn down
     */
    void setHomeBtnDown(boolean down);

    /**
     * Sets the public btn down.
     * 
     * @param down
     *          the new public btn down
     */
    void setPublicBtnDown(boolean down);

    /**
     * Sets the public visible.
     * 
     * @param visible
     *          the new public visible
     */
    void setPublicVisible(boolean visible);

    /**
     * Sets the user btn down.
     * 
     * @param down
     *          the new user btn down
     */
    void setUserBtnDown(boolean down);

    /**
     * Sets the window title.
     * 
     * @param title
     *          the new window title
     */
    void setWindowTitle(String title);

    /**
     * Show group space tooltip.
     */
    void showGroupSpaceTooltip();

    /**
     * Show home space tooltip.
     */
    void showHomeSpaceTooltip();

    /**
     * Show public space tooltip.
     */
    void showPublicSpaceTooltip();

    /**
     * Show user space tooltip.
     */
    void showUserSpaceTooltip();

  }

  /** The armor. */
  private final GSpaceArmor armor;

  /** The back manager. */
  private final GSpaceBackgroundManager backManager;

  /** The current space. */
  private Space currentSpace;

  /** The group token. */
  private String groupToken;

  /** The home token. */
  private String homeToken;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The inbox token. */
  private String inboxToken;

  /** The public token. */
  private String publicToken;

  /** The session. */
  private final Session session;

  /** The shortcut register. */
  private final GlobalShortcutRegister shortcutRegister;

  /** The sign in. */
  private final Provider<SignIn> signIn;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new space selector presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param stateManager
   *          the state manager
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param armor
   *          the armor
   * @param session
   *          the session
   * @param signIn
   *          the sign in
   * @param backManager
   *          the back manager
   * @param i18n
   *          the i18n
   * @param mask
   *          the mask
   * @param shortcutRegister
   *          the shortcut register
   */
  @Inject
  public SpaceSelectorPresenter(final EventBus eventBus, final StateManager stateManager,
      final SpaceSelectorView view, final SpaceSelectorProxy proxy, final GSpaceArmor armor,
      final Session session, final Provider<SignIn> signIn, final GSpaceBackgroundManager backManager,
      final I18nTranslationService i18n, final MaskWidgetView mask,
      final GlobalShortcutRegister shortcutRegister) {
    super(eventBus, view, proxy);
    this.stateManager = stateManager;
    this.armor = armor;
    this.session = session;
    this.signIn = signIn;
    this.backManager = backManager;
    this.i18n = i18n;
    this.shortcutRegister = shortcutRegister;
    currentSpace = null;
    homeToken = SiteTokens.HOME;
    inboxToken = SiteTokens.WAVE_INBOX;
    groupToken = SiteTokens.GROUP_HOME;
    publicToken = TokenUtils.preview(SiteTokens.GROUP_HOME);
    configureClickListeners();
    configureShortcuts();
    // eventBus.addHandler(WindowFocusEvent.getType(), new
    // WindowFocusEvent.WindowFocusHandler() {
    // @Override
    // public void onWindowFocus(final WindowFocusEvent event) {
    // if (event.isHasFocus() && !mask.isShowing()) {
    // // showTooltipWithDelay();
    // }
    // }
    // });
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        getView().setPublicVisible(event.getInitData().isPublicSpaceVisible());
      }
    });
  }

  /**
   * Configure click listeners.
   */
  private void configureClickListeners() {
    getView().getHomeBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onHomeBtnClick();
      }

    });
    getView().getUserBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onUserBtnClick();
      }
    });
    getView().getGroupBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onGroupBtnClick();
      }

    });
    getView().getPublicBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onPublicBtnClick();
      }
    });
  }

  /**
   * Configure shortcuts.
   */
  private void configureShortcuts() {
    shortcutRegister.put(Shortcut.getShortcut("Alt+H"), new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        onHomeBtnClick();
      }
    });
    shortcutRegister.put(Shortcut.getShortcut("Alt+I"), new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        onUserBtnClick();
      }
    });
    shortcutRegister.put(Shortcut.getShortcut("Alt+G"), new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        onGroupBtnClick();
      }
    });
    shortcutRegister.put(Shortcut.getShortcut("Alt+P"), new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        onPublicBtnClick();
      }
    });

  }

  /**
   * On app start.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onAppStart(final AppStartEvent event) {
    showTooltipWithDelay();
  }

  /**
   * On group btn click.
   */
  private void onGroupBtnClick() {
    if (groupToken.equals(SiteTokens.GROUP_HOME)) {
      // as current home is equal to "no content" token, we shall go to
      // group space def home page
      stateManager.gotoDefaultHomepage();
    } else {
      restoreToken(groupToken);
    }
    setDown(Space.groupSpace);
  }

  /**
   * On group space select.
   * 
   * @param shouldRestoreToken
   *          the should restore token
   */
  private void onGroupSpaceSelect(final boolean shouldRestoreToken) {
    restoreToken(shouldRestoreToken, groupToken);
    armor.selectGroupSpace();
    backManager.restoreBackgroundImage();
    setDown(Space.groupSpace);
    currentSpace = Space.groupSpace;
  }

  /**
   * On home btn click.
   */
  private void onHomeBtnClick() {
    restoreToken(homeToken);
    setDown(Space.homeSpace);
  }

  /**
   * On home space select.
   * 
   * @param shouldRestoreToken
   *          the should restore token
   */
  private void onHomeSpaceSelect(final boolean shouldRestoreToken) {
    restoreToken(shouldRestoreToken, homeToken);
    armor.selectHomeSpace();
    backManager.clearBackgroundImage();
    setDown(Space.homeSpace);
    currentSpace = Space.homeSpace;
    getView().setWindowTitle(i18n.t("Home"));
  }

  /**
   * On public btn click.
   */
  private void onPublicBtnClick() {
    restoreToken(publicToken);
    setDown(Space.publicSpace);
  }

  /**
   * On public space select.
   * 
   * @param shouldRestoreToken
   *          the should restore token
   */
  private void onPublicSpaceSelect(final boolean shouldRestoreToken) {
    restoreToken(shouldRestoreToken, inboxToken);
    armor.selectPublicSpace();
    backManager.restoreBackgroundImage();
    setDown(Space.publicSpace);
    currentSpace = Space.publicSpace;
  }

  /**
   * On space conf.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onSpaceConf(final SpaceConfEvent event) {
    final Space space = event.getSpace();
    final String token = event.getToken();
    switch (space) {
    case homeSpace:
      homeToken = token;
      break;
    case userSpace:
      inboxToken = token;
      break;
    case groupSpace:
      groupToken = token;
      break;
    case publicSpace:
      publicToken = token;
      break;
    }
  }

  /**
   * On space select.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onSpaceSelect(final SpaceSelectEvent event) {
    final Space space = event.getSpace();
    if (space != currentSpace) {
      final boolean restoreToken = event.shouldRestoreToken();
      switch (space) {
      case homeSpace:
        onHomeSpaceSelect(restoreToken);
        break;
      case userSpace:
        onUserSpaceSelect(restoreToken);
        break;
      case groupSpace:
        onGroupSpaceSelect(restoreToken);
        break;
      case publicSpace:
        onPublicSpaceSelect(restoreToken);
        break;
      default:
        break;
      }
    }
  }

  /**
   * On user btn click.
   */
  private void onUserBtnClick() {
    signIn.get().setGotoTokenOnCancel(stateManager.getCurrentToken());
    restoreToken(inboxToken);
    if (session.isLogged()) {
      setDown(Space.userSpace);
    }
  }

  /**
   * On user sign out.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onUserSignOut(final UserSignOutEvent event) {
    if (currentSpace == Space.userSpace) {
      restoreToken(homeToken);
    }
    inboxToken = SiteTokens.WAVE_INBOX;
  }

  /**
   * On user space select.
   * 
   * @param shouldRestoreToken
   *          the should restore token
   */
  private void onUserSpaceSelect(final boolean shouldRestoreToken) {
    if (session.isLogged()) {
      restoreToken(shouldRestoreToken, inboxToken);
      armor.selectUserSpace();
      backManager.clearBackgroundImage();
      setDown(Space.userSpace);
      currentSpace = Space.userSpace;
      getView().setWindowTitle(i18n.t("Inbox"));
    } else {
      signIn.get().setErrorMessage(i18n.t(CoreMessages.SIGN_IN_TO_ACCESS_INBOX), NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN, inboxToken));
      getView().setUserBtnDown(false);
    }
  }

  /**
   * Restore token.
   * 
   * @param shouldRestoreToken
   *          the should restore token
   * @param token
   *          the token
   */
  private void restoreToken(final boolean shouldRestoreToken, final String token) {
    if (shouldRestoreToken) {
      restoreToken(token);
    }
  }

  /**
   * Restore token.
   * 
   * @param token
   *          the token
   */
  private void restoreToken(final String token) {
    Log.info("Restoring token from SpaceSelector: " + token);
    stateManager.gotoHistoryToken(token);
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

  /**
   * Sets the down.
   * 
   * @param space
   *          the new down
   */
  private void setDown(final Space space) {
    getView().setHomeBtnDown(space.equals(Space.homeSpace));
    getView().setUserBtnDown(space.equals(Space.userSpace));
    getView().setGroupBtnDown(space.equals(Space.groupSpace));
    getView().setPublicBtnDown(space.equals(Space.publicSpace));
  }

  /**
   * Show tooltip now.
   */
  private void showTooltipNow() {
    if (currentSpace != null) {
      switch (currentSpace) {
      case homeSpace:
        getView().showHomeSpaceTooltip();
        getView().blinkHomeBtn();
        break;
      case userSpace:
        getView().showUserSpaceTooltip();
        getView().blinkUserBtn();
        break;
      case groupSpace:
        getView().showGroupSpaceTooltip();
        getView().blinkGroupBtn();
        break;
      case publicSpace:
        getView().showPublicSpaceTooltip();
        getView().blinkPublicBtn();
        break;
      }
    }
  }

  /**
   * Show tooltip with delay.
   */
  protected void showTooltipWithDelay() {
    new Timer() {
      @Override
      public void run() {
        showTooltipNow();
      }
    }.schedule(200);
  }
}
