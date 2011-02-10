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
 \*/
package org.ourproject.kune.workspace.client.oldsn;

import java.util.List;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuRadioDescriptor;
import org.ourproject.kune.platf.client.actions.RadioMustBeChecked;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionBuddiesSummaryToolbar;

import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.services.ImageUtils;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.UserBuddiesVisibility;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserBuddiesDataDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class BuddiesSummaryPresenter extends SocialNetworkPresenter implements BuddiesSummary {

    public static final String BUDDIES_VISIBILITY_GROUP = "k-bsp-bud-visib";

    private final UserActionRegistry actionRegistry;
    private final Provider<ChatEngine> chatEngineProvider;
    private final Provider<FileDownloadUtils> fileDownUtilsProvider;
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;
    private final ActionBuddiesSummaryToolbar toolbar;
    private final Provider<UserServiceAsync> userServiceAsync;
    private BuddiesSummaryView view;

    @Inject
    public BuddiesSummaryPresenter(final StateManager stateManager, final Session session,
            final Provider<UserServiceAsync> userServiceAsync, final UserActionRegistry actionRegistry,
            final I18nTranslationService i18n, final Provider<ChatEngine> chatEngineProvider,
            final ActionBuddiesSummaryToolbar toolbar, final Provider<FileDownloadUtils> fileDownUtilsProvider,
            final ImageUtils imageUtils, final Provider<SocialNetworkServiceAsync> snServiceAsync,
            final GroupActionRegistry groupActionRegistry, final AccessRightsClientManager accessRightManager,
            final IconResources img) {
        super(i18n, stateManager, accessRightManager, session, snServiceAsync, groupActionRegistry,
                fileDownUtilsProvider, img);
        this.stateManager = stateManager;
        this.session = session;
        this.userServiceAsync = userServiceAsync;
        this.actionRegistry = actionRegistry;
        this.i18n = i18n;
        this.chatEngineProvider = chatEngineProvider;
        this.toolbar = toolbar;
        this.fileDownUtilsProvider = fileDownUtilsProvider;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            @Override
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
        stateManager.onSocialNetworkChanged(new Listener<StateAbstractDTO>() {
            @Override
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
        registerActions();
    }

    private void createAddNewBuddiesAction() {
        final ActionToolbarMenuDescriptor<UserSimpleDTO> addNewBuddiesAction = new ActionToolbarMenuDescriptor<UserSimpleDTO>(
                AccessRolDTO.Administrator, buddiesBottom, new Listener<UserSimpleDTO>() {
                    @Override
                    public void onEvent(final UserSimpleDTO parameter) {
                        NotifyUser.info("In development");
                    }
                });
        addNewBuddiesAction.setTextDescription(i18n.t("Add a new buddy"));
        addNewBuddiesAction.setParentMenuTitle(i18n.t("Options"));
        addNewBuddiesAction.setIconUrl("images/add-green.gif");
        actionRegistry.addAction(addNewBuddiesAction);
    }

    private void createSetBuddiesVisibilityAction(final String textDescription, final UserBuddiesVisibility visibility) {
        final ActionToolbarMenuRadioDescriptor<UserSimpleDTO> buddiesVisibilityAction = new ActionToolbarMenuRadioDescriptor<UserSimpleDTO>(
                AccessRolDTO.Administrator, buddiesBottom, new Listener<UserSimpleDTO>() {
                    @Override
                    public void onEvent(final UserSimpleDTO parameter) {
                        userServiceAsync.get().setBuddiesVisibility(session.getUserHash(),
                                session.getCurrentState().getGroup().getStateToken(), visibility,
                                new AsyncCallbackSimple<Void>() {
                                    @Override
                                    public void onSuccess(final Void result) {
                                        NotifyUser.info(i18n.t("Buddies visibility changed"));
                                    }
                                });
                    }
                }, BUDDIES_VISIBILITY_GROUP, new RadioMustBeChecked() {
                    @Override
                    public boolean mustBeChecked() {
                        final StateAbstractDTO currentState = session.getCurrentState();
                        if (currentState.getGroup().isPersonal()) {
                            final SocialNetworkDataDTO socialNetworkData = currentState.getSocialNetworkData();
                            return socialNetworkData.getUserBuddiesVisibility().equals(visibility);
                        }
                        return false;
                    }
                });
        buddiesVisibilityAction.setTextDescription(textDescription);
        buddiesVisibilityAction.setParentMenuTitle(i18n.t("Options"));
        buddiesVisibilityAction.setParentSubMenuTitle(i18n.t("Those who can view your buddies list"));
        actionRegistry.addAction(buddiesVisibilityAction);
    }

    public View getView() {
        return view;
    }

    public void init(final BuddiesSummaryView view) {
        this.view = view;
    }

    private void registerActions() {
        final ActionMenuItemDescriptor<UserSimpleDTO> addAsBuddie = new ActionMenuItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, new Listener<UserSimpleDTO>() {
                    @Override
                    public void onEvent(final UserSimpleDTO user) {
                        chatEngineProvider.get().addNewBuddie(user.getShortName());
                    }
                });
        addAsBuddie.setMustBeAuthenticated(true);
        addAsBuddie.setTextDescription(i18n.t("Add as a buddie"));
        addAsBuddie.setIconUrl("images/add-green.png");
        addAsBuddie.setEnableCondition(new ActionEnableCondition<UserSimpleDTO>() {
            @Override
            public boolean mustBeEnabled(final UserSimpleDTO user) {
                return !chatEngineProvider.get().isBuddie(user.getShortName());
            }
        });
        actionRegistry.addAction(addAsBuddie);

        final ActionMenuItemDescriptor<UserSimpleDTO> go = new ActionMenuItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, new Listener<UserSimpleDTO>() {
                    @Override
                    public void onEvent(final UserSimpleDTO user) {
                        stateManager.gotoToken(user.getShortName());
                    }
                });
        go.setMustBeAuthenticated(false);
        go.setTextDescription(i18n.t("Visit this user's homepage"));
        go.setIconUrl("images/group-home.gif");
        actionRegistry.addAction(go);

        createAddNewBuddiesAction();
        createSetBuddiesVisibilityAction(i18n.t("anyone"), UserBuddiesVisibility.anyone);
        createSetBuddiesVisibilityAction(i18n.t("only your buddies"), UserBuddiesVisibility.yourbuddies);
        createSetBuddiesVisibilityAction(i18n.t("only you"), UserBuddiesVisibility.onlyyou);
    }

    protected void setState(final StateAbstractDTO state) {
        if (state.getGroup().isPersonal()) {
            view.clear();
            final UserBuddiesDataDTO userBuddies = state.getUserBuddies();
            if (state.getSocialNetworkData().isBuddiesVisible()) {
                final List<UserSimpleDTO> buddies = userBuddies.getBuddies();
                for (final UserSimpleDTO user : buddies) {
                    final String avatarUrl = user.hasLogo() ? fileDownUtilsProvider.get().getLogoImageUrl(
                            user.getStateToken()) : BuddiesSummaryView.NOAVATAR;
                    final String tooltip = super.createTooltipWithLogo(user.getShortName(), user.getStateToken(),
                            user.hasLogo(), true);
                    view.addBuddie(user, actionRegistry.getCurrentActions(user, session.isLogged(), new AccessRights(
                            true, true, true), false), avatarUrl, user.getName(), tooltip);
                }
                final boolean hasLocalBuddies = buddies.size() > 0;
                final int numExtBuddies = userBuddies.getOtherExtBuddies();
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
                toolbar.addActions(
                        actionRegistry.getCurrentActions(session.getCurrentUser(), session.isLogged(),
                                state.getGroupRights(), true), ActionToolbar.IN_ANY);
                toolbar.attach();
                view.show();
            } else {
                view.showBuddiesNotVisible();
                view.show();
            }
        } else {
            view.hide();
        }
    }

}
