package cc.kune.core.client.sn;

import java.util.List;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.client.sn.actions.registry.GroupSNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupSNPendingsMenuItemsRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SocialNetworkChangedEvent;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class GroupSNPresenter extends AbstractSNPresenter<GroupSNPresenter.GroupSNView, GroupSNPresenter.GroupSNProxy> {

    @ProxyCodeSplit
    public interface GroupSNProxy extends Proxy<GroupSNPresenter> {
    }

    public interface GroupSNView extends View {

        void addAdmin(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void addCollab(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void addPending(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void clear();

        IsActionExtensible getBottomToolbar();

        void setAdminsCount(int count);

        void setCollabsCount(int count);

        void setCollabsVisible(boolean visible);

        void setPendingsCount(int count);

        void setPendingVisible(boolean visible);

        void setVisible(boolean visible);

        void showMemberNotPublic();

        void showMembers();

        void showOrphan();
    }

    private final GroupSNConfActions actionsRegistry;
    private final GroupSNAdminsMenuItemsRegistry adminsMenuItemsRegistry;
    private final GroupSNCollabsMenuItemsRegistry collabsMenuItemsRegistry;
    private final GroupSNPendingsMenuItemsRegistry pendingsMenuItemsRegistry;

    @Inject
    public GroupSNPresenter(final EventBus eventBus, final GroupSNView view, final GroupSNProxy proxy,
            final StateManager stateManager, final Session session, final Provider<FileDownloadUtils> downloadProvider,
            final GroupSNAdminsMenuItemsRegistry adminsMenuItemsRegistry,
            final GroupSNCollabsMenuItemsRegistry collabsMenuItemsRegistry,
            final GroupSNPendingsMenuItemsRegistry pendingsMenuItemsRegistry,
            final GroupSNConfActions actionsRegistry) {
        super(eventBus, view, proxy, downloadProvider);
        this.adminsMenuItemsRegistry = adminsMenuItemsRegistry;
        this.collabsMenuItemsRegistry = collabsMenuItemsRegistry;
        this.pendingsMenuItemsRegistry = pendingsMenuItemsRegistry;
        this.actionsRegistry = actionsRegistry;
        stateManager.onStateChanged(true, new StateChangedEvent.StateChangedHandler() {
            @Override
            public void onStateChanged(final StateChangedEvent event) {
                GroupSNPresenter.this.onStateChanged(event.getState());
            }
        });
        stateManager.onSocialNetworkChanged(true, new SocialNetworkChangedEvent.SocialNetworkChangedHandler() {
            @Override
            public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
                GroupSNPresenter.this.onStateChanged(event.getState());
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
        createActions();
    }

    private void createActions() {
        getView().getBottomToolbar().addActions(actionsRegistry);
    }

    @Override
    public GroupSNView getView() {
        return (GroupSNView) super.getView();
    }

    private void onStateChanged(final StateAbstractDTO state) {
        if (state.getGroup().isPersonal()) {
            getView().setVisible(false);
        } else {
            if (state.getSocialNetworkData().isMembersVisible()) {
                getView().clear();
                setGroupMembers(state.getGroupMembers(), state.getGroupRights());
            } else {
                getView().clear();
                getView().showMemberNotPublic();
                getView().setVisible(true);
            }
        }
    }

    private void refreshOnSignInSignOut(final Session session) {
        final StateAbstractDTO currentState = session.getCurrentState();
        if (currentState != null) {
            GroupSNPresenter.this.onStateChanged(currentState);
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    private void setGroupMembers(final SocialNetworkDTO socialNetwork, final AccessRights rights) {
        final AccessListsDTO accessLists = socialNetwork.getAccessLists();

        final List<GroupDTO> adminsList = accessLists.getAdmins().getList();
        final List<GroupDTO> collabList = accessLists.getEditors().getList();
        final List<GroupDTO> pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

        final int numAdmins = adminsList.size();
        final int numCollabs = collabList.size();
        final int numPendings = pendingCollabsList.size();

        getView().setAdminsCount(numAdmins);
        getView().setCollabsCount(numCollabs);
        getView().setPendingsCount(numPendings);

        if ((numAdmins + numCollabs) == 0) {
            getView().showOrphan();
        } else {
            final boolean userIsAdmin = rights.isAdministrable();
            final boolean userCanView = rights.isVisible();

            if (userCanView) {
                for (final GroupDTO admin : adminsList) {
                    final String avatar = getAvatar(admin);
                    getView().addAdmin(admin, avatar, admin.getLongName(), "",
                            createMenuItems(admin, adminsMenuItemsRegistry, admin.getLongName()));
                }
                getView().setCollabsVisible(numCollabs > 0);
                for (final GroupDTO collab : collabList) {
                    final String avatar = getAvatar(collab);
                    getView().addCollab(collab, avatar, collab.getLongName(), "",
                            createMenuItems(collab, collabsMenuItemsRegistry, collab.getLongName()));
                }
                if (userIsAdmin) {
                    getView().setPendingVisible(numPendings > 0);
                    for (final GroupDTO pendingCollab : pendingCollabsList) {
                        final String avatar = getAvatar(pendingCollab);
                        getView().addPending(pendingCollab, avatar, pendingCollab.getLongName(), "",
                                createMenuItems(pendingCollab, pendingsMenuItemsRegistry, pendingCollab.getLongName()));
                    }
                } else {
                    getView().setPendingVisible(false);
                }
                getView().showMembers();
            }
        }
        getView().setVisible(true);
    }
}