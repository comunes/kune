package cc.kune.core.client.sn;

import java.util.List;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.sn.UserSNPresenter.UserSNProxy;
import cc.kune.core.client.sn.UserSNPresenter.UserSNView;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.core.client.sn.actions.registry.UserSNMenuItemsRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SocialNetworkChangedEvent;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.ParticipationDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserBuddiesDataDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class UserSNPresenter extends AbstractSNPresenter<UserSNView, UserSNProxy> {

    @ProxyCodeSplit
    public interface UserSNProxy extends Proxy<UserSNPresenter> {
    }

    public interface UserSNView extends View {

        void addBuddie(UserSimpleDTO user, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void addParticipation(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void addTextToBuddieList(String text);

        void clear();

        IsActionExtensible getBottomToolbar();

        void setBuddiesCount(int count);

        void setBuddiesVisible(boolean visible);

        void setParticipationCount(int count);

        void setParticipationVisible(boolean visible);

        void setVisible(boolean visible);

        void showBuddies();

        void showBuddiesNotPublic();
    }

    private final UserSNConfActions confActionsRegistry;
    private final I18nTranslationService i18n;
    private final UserSNMenuItemsRegistry userMenuItemsRegistry;

    @Inject
    public UserSNPresenter(final EventBus eventBus, final UserSNView view, final UserSNProxy proxy,
            final I18nTranslationService i18n, final StateManager stateManager, final Session session,
            final Provider<FileDownloadUtils> downloadProvider, final UserSNMenuItemsRegistry userMenuItemsRegistry,
            final UserSNConfActions confActionsRegistry) {
        super(eventBus, view, proxy, downloadProvider);
        this.i18n = i18n;
        this.userMenuItemsRegistry = userMenuItemsRegistry;
        this.confActionsRegistry = confActionsRegistry;
        stateManager.onStateChanged(true, new StateChangedEvent.StateChangedHandler() {
            @Override
            public void onStateChanged(final StateChangedEvent event) {
                UserSNPresenter.this.onStateChanged(event.getState());
            }
        });
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                refreshOnSignInSignOut(session);
            }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                refreshOnSignInSignOut(session);
            }
        });
        stateManager.onSocialNetworkChanged(true, new SocialNetworkChangedEvent.SocialNetworkChangedHandler() {
            @Override
            public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
                UserSNPresenter.this.onStateChanged(event.getState());
            }
        });
        createActions();
    }

    private void createActions() {
        getView().getBottomToolbar().addAll(confActionsRegistry);
    }

    @Override
    public UserSNView getView() {
        return (UserSNView) super.getView();
    }

    private void onStateChanged(final StateAbstractDTO state) {
        if (state.getGroup().isNotPersonal()) {
            getView().setVisible(false);
        } else {
            getView().clear();
            if (state.getSocialNetworkData().isBuddiesVisible()) {
                setBuddiesState(state);
                setParticipationState(state);
            } else {
                getView().showBuddiesNotPublic();
            }
            getView().setVisible(true);
        }
    }

    private void refreshOnSignInSignOut(final Session session) {
        final StateAbstractDTO currentState = session.getCurrentState();
        if (currentState != null) {
            UserSNPresenter.this.onStateChanged(currentState);
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    private void setBuddiesState(final StateAbstractDTO state) {
        final UserBuddiesDataDTO userBuddiesData = state.getUserBuddies();
        final List<UserSimpleDTO> buddies = userBuddiesData.getBuddies();
        for (final UserSimpleDTO user : buddies) {
            final String avatarUrl = user.hasLogo() ? downloadProvider.get().getLogoImageUrl(user.getStateToken())
                    : PERSON_NO_AVATAR_IMAGE;
            getView().addBuddie(user, avatarUrl, user.getName(), "",
                    createMenuItems(user, userMenuItemsRegistry, user.getName()));
        }
        getView().setBuddiesVisible(buddies.size() > 0);
        final boolean hasLocalBuddies = buddies.size() > 0;
        final int numExtBuddies = userBuddiesData.getOtherExtBuddies();
        if (numExtBuddies > 0) {
            if (hasLocalBuddies) {
                // i18n: plural
                getView().addTextToBuddieList(
                        i18n.t(numExtBuddies == 1 ? "and [%d] external user" : "and [%d] external users", numExtBuddies));
            } else {
                getView().addTextToBuddieList(
                        i18n.t(numExtBuddies == 1 ? "[%d] external user" : "[%d] external users", numExtBuddies));
            }
        } else {
            // if (hasLocalBuddies) {
            // view.clearOtherUsers();
            // } else {
            // view.setNoBuddies();
            // }
        }
    }

    private void setParticipationState(final StateAbstractDTO state) {
        final ParticipationDataDTO participation = state.getParticipation();
        final List<GroupDTO> groupsIsAdmin = participation.getGroupsIsAdmin();
        final List<GroupDTO> groupsIsCollab = participation.getGroupsIsCollab();
        final int numAdmins = groupsIsAdmin.size();
        final int numCollaborators = groupsIsCollab.size();
        for (final GroupDTO group : groupsIsAdmin) {
            getView().addParticipation(group, getAvatar(group), group.getLongName(), "",
                    createMenuItems(group, userMenuItemsRegistry, group.getLongName()));
        }
        for (final GroupDTO group : groupsIsCollab) {
            getView().addParticipation(group, getAvatar(group), group.getLongName(), "",
                    createMenuItems(group, userMenuItemsRegistry, group.getLongName()));
        }
        final int totalGroups = numAdmins + numCollaborators;
        // getView().setParticipationCount(totalGroups);
        getView().setParticipationVisible(totalGroups > 0);
    }
}