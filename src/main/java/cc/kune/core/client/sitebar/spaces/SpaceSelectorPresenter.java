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

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.events.WindowFocusEvent;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.style.GSpaceBackManager;

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

public class SpaceSelectorPresenter extends
    Presenter<SpaceSelectorPresenter.SpaceSelectorView, SpaceSelectorPresenter.SpaceSelectorProxy> {

  @ProxyCodeSplit
  public interface SpaceSelectorProxy extends Proxy<SpaceSelectorPresenter> {
  }

  public interface SpaceSelectorView extends View {

    public static String GROUP_SPACE_ID = "k-space-group-id";
    public static String HOME_SPACE_ID = "k-space-home-id";
    public static String PUBLIC_SPACE_ID = "k-space-public-id";
    public static String USER_SPACE_ID = "k-space-user-id";

    HasClickHandlers getGroupBtn();

    HasClickHandlers getHomeBtn();

    HasClickHandlers getPublicBtn();

    HasClickHandlers getUserBtn();

    void setGroupBtnDown(boolean down);

    void setHomeBtnDown(boolean down);

    void setPublicBtnDown(boolean down);

    void setUserBtnDown(boolean down);

    void showGroupSpaceTooltip();

    void showHomeSpaceTooltip();

    void showPublicSpaceTooltip();

    void showUserSpaceTooltip();
  }

  private final GSpaceArmor armor;
  private final GSpaceBackManager backManager;
  private Space currentSpace;
  private String groupToken;
  private String homeToken;
  private final I18nTranslationService i18n;
  private String publicToken;
  private final Session session;
  private final Provider<SignIn> signIn;
  private final StateManager stateManager;
  private String userToken;

  @Inject
  public SpaceSelectorPresenter(final EventBus eventBus, final StateManager stateManager,
      final SpaceSelectorView view, final SpaceSelectorProxy proxy, final GSpaceArmor armor,
      final Session session, final Provider<SignIn> signIn, final GSpaceBackManager backManager,
      final I18nTranslationService i18n, final MaskWidgetView mask) {
    super(eventBus, view, proxy);
    this.stateManager = stateManager;
    this.armor = armor;
    this.session = session;
    this.signIn = signIn;
    this.backManager = backManager;
    this.i18n = i18n;
    currentSpace = null;
    homeToken = SiteTokens.HOME;
    userToken = SiteTokens.WAVEINBOX;
    groupToken = SiteTokens.GROUP_HOME;
    publicToken = TokenUtils.preview(SiteTokens.GROUP_HOME);
    view.getHomeBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        restoreToken(homeToken);
        setDown(Space.homeSpace);
      }
    });
    view.getUserBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        restoreToken(userToken);
        setDown(Space.userSpace);
      }
    });
    view.getGroupBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        restoreToken(groupToken);
        setDown(Space.groupSpace);
      }
    });
    view.getPublicBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        restoreToken(publicToken);
        setDown(Space.publicSpace);
      }

    });
    eventBus.addHandler(WindowFocusEvent.getType(), new WindowFocusEvent.WindowFocusHandler() {
      @Override
      public void onWindowFocus(final WindowFocusEvent event) {
        if (event.isHasFocus() && !mask.isShowing()) {
          // showTooltipWithDelay();
        }
      }
    });
  }

  @ProxyEvent
  public void onAppStart(final AppStartEvent event) {
    // showTooltipWithDelay();
  }

  private void onGroupSpaceSelect(final boolean shouldRestoreToken) {
    restoreToken(shouldRestoreToken, groupToken);
    armor.selectGroupSpace();
    backManager.restoreBackImage();
    setDown(Space.groupSpace);
    currentSpace = Space.groupSpace;
  }

  private void onHomeSpaceSelect(final boolean shouldRestoreToken) {
    restoreToken(shouldRestoreToken, homeToken);
    armor.selectHomeSpace();
    backManager.clearBackImage();
    setDown(Space.homeSpace);
    currentSpace = Space.homeSpace;
  }

  private void onPublicSpaceSelect(final boolean shouldRestoreToken) {
    restoreToken(shouldRestoreToken, userToken);
    armor.selectPublicSpace();
    backManager.restoreBackImage();
    setDown(Space.publicSpace);
    currentSpace = Space.publicSpace;
  }

  @ProxyEvent
  public void onSpaceConf(final SpaceConfEvent event) {
    final Space space = event.getSpace();
    final String token = event.getToken();
    switch (space) {
    case homeSpace:
      homeToken = token;
      break;
    case userSpace:
      userToken = token;
      break;
    case groupSpace:
      groupToken = token;
      break;
    case publicSpace:
      publicToken = token;
      break;
    }
  }

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

  @ProxyEvent
  public void onUserSignOut(final UserSignOutEvent event) {
    if (currentSpace == Space.userSpace) {
      restoreToken(homeToken);
    }
    userToken = SiteTokens.WAVEINBOX;
  }

  private void onUserSpaceSelect(final boolean shouldRestoreToken) {
    if (session.isLogged()) {
      restoreToken(shouldRestoreToken, userToken);
      armor.selectUserSpace();
      backManager.clearBackImage();
      setDown(Space.userSpace);
      currentSpace = Space.userSpace;
    } else {
      signIn.get().setErrorMessage(i18n.t("Sign in or create an account to access to your inbox"),
          NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGNIN, userToken));
      getView().setUserBtnDown(false);
    }
  }

  private void restoreToken(final boolean shouldRestoreToken, final String token) {
    if (shouldRestoreToken) {
      restoreToken(token);
    }
  }

  private void restoreToken(final String token) {
    stateManager.gotoHistoryToken(token);
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  private void setDown(final Space space) {
    getView().setHomeBtnDown(space.equals(Space.homeSpace));
    getView().setUserBtnDown(space.equals(Space.userSpace));
    getView().setGroupBtnDown(space.equals(Space.groupSpace));
    getView().setPublicBtnDown(space.equals(Space.publicSpace));
  }

  private void showTooltipNow() {
    if (currentSpace != null) {
      switch (currentSpace) {
      case homeSpace:
        getView().showHomeSpaceTooltip();
        break;
      case userSpace:
        getView().showUserSpaceTooltip();
        break;
      case groupSpace:
        getView().showGroupSpaceTooltip();
        break;
      case publicSpace:
        getView().showPublicSpaceTooltip();
        break;
      }
    }
  }

  protected void showTooltipWithDelay() {
    new Timer() {
      @Override
      public void run() {
        showTooltipNow();
      }
    }.schedule(200);
  }
}