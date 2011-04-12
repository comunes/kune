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

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
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

        HasClickHandlers getGroupBtn();

        HasClickHandlers getHomeBtn();

        HasClickHandlers getPublicBtn();

        HasClickHandlers getUserBtn();

        void hideTooltip();

        void setGroupBtnDown(boolean down);

        void setHomeBtnDown(boolean down);

        void setPublicBtnDown(boolean down);

        void setUserBtnDown(boolean down);

        void showTooltip();
    }

    private final WsArmor armor;
    private Space currentSpace;
    private String groupToken;
    private String homeToken;
    private String publicToken;
    private final Session session;
    private final StateManager stateManager;
    private String userToken;

    @Inject
    public SpaceSelectorPresenter(final EventBus eventBus, final StateManager stateManager,
            final SpaceSelectorView view, final SpaceSelectorProxy proxy, final WsArmor armor, final Session session) {
        super(eventBus, view, proxy);
        this.stateManager = stateManager;
        this.armor = armor;
        this.session = session;
        currentSpace = null;
        homeToken = SiteTokens.HOME;
        userToken = SiteTokens.WAVEINBOX;
        groupToken = SiteTokens.GROUP_HOME;
        publicToken = TokenUtils.preview(SiteTokens.GROUP_HOME);
        view.getHomeBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                stateManager.gotoHistoryToken(homeToken);
            }
        });
        view.getUserBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                stateManager.gotoHistoryToken(userToken);
            }
        });
        view.getGroupBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                stateManager.gotoHistoryToken(groupToken);
            }
        });
        view.getPublicBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                stateManager.gotoHistoryToken(publicToken);
            }
        });
    }

    @ProxyEvent
    public void onAppStart(final AppStartEvent event) {
    }

    private void onGroupSpaceSelect() {
        armor.selectGroupSpace();
        getView().setHomeBtnDown(false);
        getView().setUserBtnDown(false);
        getView().setGroupBtnDown(true);
        getView().setPublicBtnDown(false);
        currentSpace = Space.groupSpace;
    }

    private void onHomeSpaceSelect() {
        armor.selectHomeSpace();
        getView().setHomeBtnDown(true);
        getView().setUserBtnDown(false);
        getView().setGroupBtnDown(false);
        getView().setPublicBtnDown(false);
        currentSpace = Space.homeSpace;
    }

    private void onPublicSpaceSelect() {
        armor.selectPublicSpace();
        getView().setHomeBtnDown(false);
        getView().setUserBtnDown(false);
        getView().setGroupBtnDown(false);
        getView().setPublicBtnDown(true);
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
            switch (space) {
            case homeSpace:
                onHomeSpaceSelect();
                break;
            case userSpace:
                onUserSpaceSelect();
                break;
            case groupSpace:
                onGroupSpaceSelect();
                break;
            case publicSpace:
                onPublicSpaceSelect();
                break;
            default:
                break;
            }
            // getView().hideTooltip();
        }
    }

    @ProxyEvent
    public void onUserSignOut(final UserSignOutEvent event) {
        if (currentSpace == Space.userSpace) {
            stateManager.gotoHistoryToken(homeToken);
        }
        userToken = SiteTokens.WAVEINBOX;
    }

    private void onUserSpaceSelect() {
        if (session.isLogged()) {
            armor.selectUserSpace();
            getView().setHomeBtnDown(false);
            getView().setUserBtnDown(true);
            getView().setGroupBtnDown(false);
            getView().setPublicBtnDown(false);
            currentSpace = Space.userSpace;
        } else {
            stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGNIN, userToken));
            getView().setUserBtnDown(false);
            NotifyUser.info("Sign in to access to your workspace");
            // signIn.get().setErrorMessage("Sign in to access to your workspace",
            // NotifyLevel.info);
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    public void showTooltip() {
        getView().showTooltip();
    }
}