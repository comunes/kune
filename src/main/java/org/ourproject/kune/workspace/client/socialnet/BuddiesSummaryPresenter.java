package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.UserActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserBuddiesDataDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class BuddiesSummaryPresenter implements BuddiesSummary {

    private BuddiesSummaryView view;
    private final StateManager stateManager;
    private final UserActionRegistry actionRegistry;
    private final I18nTranslationService i18n;
    private final Provider<ChatClientTool> chatClientToolProvider;
    private final Session session;

    public BuddiesSummaryPresenter(StateManager stateManager, final Session session, UserActionRegistry actionRegistry,
            I18nTranslationService i18n, final Provider<ChatClientTool> chatClientToolProvider) {
        this.stateManager = stateManager;
        this.session = session;
        this.actionRegistry = actionRegistry;
        this.i18n = i18n;
        this.chatClientToolProvider = chatClientToolProvider;
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(StateDTO state) {
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

    protected void setState(StateDTO state) {
        if (state.getGroup().getGroupType().equals(GroupType.PERSONAL)) {
            view.clear();
            UserBuddiesDataDTO userBuddies = state.getUserBuddies();
            List<UserSimpleDTO> buddies = userBuddies.getBuddies();
            for (UserSimpleDTO user : buddies) {
                view.addBuddie(user, actionRegistry.getCurrentActions(user, UserActionRegistry.GENERAL,
                                                                      session.isLogged(), new AccessRightsDTO(true,
                                                                              true, true), false));
            }
            boolean hasLocalBuddies = buddies.size() > 0;
            int numExtBuddies = userBuddies.getOtherExternalBuddies();
            if (numExtBuddies > 0) {
                if (hasLocalBuddies) {
                    // i18n: plural
                    view.setOtherUsers(i18n.t(
                                              numExtBuddies == 1 ? "and [%d] external user" : "and [%d] external users",
                                              numExtBuddies));
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
            view.show();
        } else {
            view.hide();
        }
    }

    private void registerActions() {
        final ActionToolbarButtonAndItemDescriptor<UserSimpleDTO> addAsBuddie = new ActionToolbarButtonAndItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, ActionToolbarPosition.bottombar, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO group) {
                        chatClientToolProvider.get().getChat().addNewBuddie(group.getShortName());
                    }
                });
        addAsBuddie.setMustBeAuthenticated(true);
        addAsBuddie.setTextDescription(i18n.t("Add as buddie"));
        addAsBuddie.setIconUrl("images/add-green.png");
        addAsBuddie.setEnableCondition(new ActionEnableCondition<UserSimpleDTO>() {
            public boolean mustBeEnabled(UserSimpleDTO item) {
                return !session.getCurrentUserInfo().getShortName().equals(item.getShortName());
            }
        });
        actionRegistry.addAction(addAsBuddie, UserActionRegistry.GENERAL);

        final ActionMenuItemDescriptor<UserSimpleDTO> go = new ActionMenuItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO user) {
                        stateManager.gotoToken(user.getShortName());
                    }
                });
        go.setMustBeAuthenticated(false);
        go.setTextDescription(i18n.t("Visit this user homepage"));
        go.setIconUrl("images/group-home.gif");
        actionRegistry.addAction(go, UserActionRegistry.GENERAL);
    }

}
