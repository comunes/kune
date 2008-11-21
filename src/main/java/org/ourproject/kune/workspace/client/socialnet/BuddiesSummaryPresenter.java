/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionAddCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.UserActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionBuddiesSummaryToolbar;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.UserBuddiesDataDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class BuddiesSummaryPresenter implements BuddiesSummary {

    private BuddiesSummaryView view;
    private final StateManager stateManager;
    private final UserActionRegistry actionRegistry;
    private final I18nTranslationService i18n;
    private final Provider<ChatEngine> chatEngineProvider;
    private final Session session;
    private final ActionBuddiesSummaryToolbar toolbar;

    public BuddiesSummaryPresenter(StateManager stateManager, final Session session, UserActionRegistry actionRegistry,
            I18nTranslationService i18n, final Provider<ChatEngine> chatEngineProvider,
            final ActionBuddiesSummaryToolbar toolbar) {
        this.stateManager = stateManager;
        this.session = session;
        this.actionRegistry = actionRegistry;
        this.i18n = i18n;
        this.chatEngineProvider = chatEngineProvider;
        this.toolbar = toolbar;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(StateAbstractDTO state) {
                setState(state);
            }
        });
        stateManager.onSocialNetworkChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(StateAbstractDTO state) {
                setState(state);
            }
        });
        registerActions();
    }

    public View getView() {
        return view;
    }

    public void init(BuddiesSummaryView view) {
        this.view = view;
    }

    protected void setState(StateAbstractDTO state) {
        if (state.getGroup().getGroupType().equals(GroupType.PERSONAL)) {
            view.clear();
            UserBuddiesDataDTO userBuddies = state.getUserBuddies();
            if (userBuddies != UserBuddiesDataDTO.NO_BUDDIES) {
                List<UserSimpleDTO> buddies = userBuddies.getBuddies();
                for (UserSimpleDTO user : buddies) {
                    view.addBuddie(user, actionRegistry.getCurrentActions(user, session.isLogged(),
                            new AccessRightsDTO(true, true, true), false));
                }
                boolean hasLocalBuddies = buddies.size() > 0;
                int numExtBuddies = userBuddies.getOtherExternalBuddies();
                if (numExtBuddies > 0) {
                    if (hasLocalBuddies) {
                        // i18n: plural
                        view.setOtherUsers(i18n.t(numExtBuddies == 1 ? "and [%d] external user"
                                : "and [%d] external users", numExtBuddies));
                    } else {
                        view.setOtherUsers(i18n.t(numExtBuddies == 1 ? "[%d] external user" : "[%d] external users",
                                numExtBuddies));
                    }
                } else {
                    if (hasLocalBuddies) {
                        view.clearOtherUsers();
                    } else {
                        view.setNoBuddies();
                    }
                }
                toolbar.disableMenusAndClearButtons();
                toolbar.setActions(actionRegistry.getCurrentActions(session.getCurrentUser(), session.isLogged(),
                        state.getGroupRights(), true));
                toolbar.attach();
                view.show();
            } else {
                view.hide();
            }
        } else {
            view.hide();
        }
    }

    private void createShowAction(String textDescription) {
        ActionToolbarMenuDescriptor<UserSimpleDTO> showBuddies = new ActionToolbarMenuDescriptor<UserSimpleDTO>(
                AccessRolDTO.Administrator, ActionToolbarPosition.bottombar, new Listener<UserSimpleDTO>() {
                    public void onEvent(UserSimpleDTO parameter) {
                        Site.info("In development");
                    }
                });
        showBuddies.setTextDescription(textDescription);
        showBuddies.setParentMenuTitle(i18n.t("Options"));
        showBuddies.setParentSubMenuTitle(i18n.t("Who can view your buddies list"));
        actionRegistry.addAction(showBuddies);
    }

    private void registerActions() {
        final ActionToolbarButtonAndItemDescriptor<UserSimpleDTO> addAsBuddie = new ActionToolbarButtonAndItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, ActionToolbarPosition.bottombar, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO group) {
                        chatEngineProvider.get().addNewBuddie(group.getShortName());
                    }
                });
        addAsBuddie.setMustBeAuthenticated(true);
        addAsBuddie.setTextDescription(i18n.t("Add as buddie"));
        addAsBuddie.setIconUrl("images/add-green.png");
        addAsBuddie.setAddCondition(new ActionAddCondition<UserSimpleDTO>() {
            public boolean mustBeAdded(UserSimpleDTO item) {
                return !session.getCurrentUserInfo().getShortName().equals(item.getShortName());
            }
        });
        actionRegistry.addAction(addAsBuddie);

        final ActionMenuItemDescriptor<UserSimpleDTO> go = new ActionMenuItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO user) {
                        stateManager.gotoToken(user.getShortName());
                    }
                });
        go.setMustBeAuthenticated(false);
        go.setTextDescription(i18n.t("Visit this user homepage"));
        go.setIconUrl("images/group-home.gif");
        actionRegistry.addAction(go);

        createShowAction(i18n.t("anyone"));
        createShowAction(i18n.t("only your buddies"));
        createShowAction(i18n.t("only you"));
    }

}
