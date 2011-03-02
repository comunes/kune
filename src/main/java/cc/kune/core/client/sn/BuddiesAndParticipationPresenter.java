package cc.kune.core.client.sn;

import java.util.List;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.sn.actions.registry.AbstractSNMembersActionsRegistry;
import cc.kune.core.client.sn.actions.registry.GroupMembersActionsRegistry;
import cc.kune.core.client.sn.actions.registry.SNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.SNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.SNPendingsMenuItemsRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SocialNetworkChangedEvent;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.ParticipationDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class BuddiesAndParticipationPresenter
        extends
        Presenter<BuddiesAndParticipationPresenter.BuddiesAndParticipationView, BuddiesAndParticipationPresenter.BuddiesAndParticipationProxy> {

    @ProxyCodeSplit
    public interface BuddiesAndParticipationProxy extends Proxy<BuddiesAndParticipationPresenter> {
    }

    public interface BuddiesAndParticipationView extends View {

        void addBuddie(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void addParticipation(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void clear();

        IsActionExtensible getBottomToolbar();

        void setBuddiesCount(int count);

        void setParticipationCount(int count);

        void setParticipationVisible(boolean visible);

        void setVisible(boolean visible);

        void showBuddies();

        void showBuddiesNotPublic();
    }

    private final GroupMembersActionsRegistry actionsRegistry;
    private final SNAdminsMenuItemsRegistry adminsMenuItemsRegistry;
    private final SNCollabsMenuItemsRegistry collabsMenuItemsRegistry;
    private final Provider<FileDownloadUtils> downloadProvider;
    private final SNPendingsMenuItemsRegistry pendingsMenuItemsRegistry;

    @Inject
    public BuddiesAndParticipationPresenter(final EventBus eventBus, final BuddiesAndParticipationView view,
            final BuddiesAndParticipationProxy proxy, final StateManager stateManager, final Session session,
            final Provider<FileDownloadUtils> downloadProvider,
            final SNAdminsMenuItemsRegistry adminsMenuItemsRegistry,
            final SNCollabsMenuItemsRegistry collabsMenuItemsRegistry,
            final SNPendingsMenuItemsRegistry pendingsMenuItemsRegistry,
            final GroupMembersActionsRegistry actionsRegistry) {
        super(eventBus, view, proxy);
        this.downloadProvider = downloadProvider;
        this.adminsMenuItemsRegistry = adminsMenuItemsRegistry;
        this.collabsMenuItemsRegistry = collabsMenuItemsRegistry;
        this.pendingsMenuItemsRegistry = pendingsMenuItemsRegistry;
        this.actionsRegistry = actionsRegistry;
        stateManager.onStateChanged(true, new StateChangedEvent.StateChangedHandler() {
            @Override
            public void onStateChanged(final StateChangedEvent event) {
                BuddiesAndParticipationPresenter.this.onStateChanged(event.getState());
            }
        });
        stateManager.onSocialNetworkChanged(true, new SocialNetworkChangedEvent.SocialNetworkChangedHandler() {

            @Override
            public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
                BuddiesAndParticipationPresenter.this.onStateChanged(event.getState());
            }
        });
        createActions();
    }

    private void createActions() {
        getView().getBottomToolbar().addActions(actionsRegistry);
    }

    private GuiActionDescCollection createMenuItems(final GroupDTO group,
            final AbstractSNMembersActionsRegistry registry) {
        final GuiActionDescCollection items = new GuiActionDescCollection();
        for (final Provider<MenuItemDescriptor> provider : registry) {
            final MenuItemDescriptor menuItem = provider.get();
            menuItem.setTarget(group);
            items.add(menuItem);
        }
        return items;
    }

    private String getAvatar(final GroupDTO admin) {
        return admin.hasLogo() ? downloadProvider.get().getLogoImageUrl(admin.getStateToken()) : "images/unknown.jpg";
    }

    private void onStateChanged(final StateAbstractDTO state) {
        if (state.getGroup().isNotPersonal()) {
            getView().setVisible(false);
        } else {
            setParticipationState(state);
            if (state.getSocialNetworkData().isBuddiesVisible()) {
                getView().clear();
                // setGroupMembers(state.getGroupMembers(),
                // state.getGroupRights());
            } else {
                getView().clear();
                getView().showBuddiesNotPublic();
            }
            getView().setVisible(true);
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    // private void setGroupMembers(final SocialNetworkDTO socialNetwork, final
    // AccessRights rights) {
    // final AccessListsDTO accessLists = socialNetwork.getAccessLists();
    //
    // final List<GroupDTO> adminsList = accessLists.getAdmins().getList();
    // final List<GroupDTO> collabList = accessLists.getEditors().getList();
    // final List<GroupDTO> pendingCollabsList =
    // socialNetwork.getPendingCollaborators().getList();
    //
    // final int numAdmins = adminsList.size();
    // final int numCollabs = collabList.size();
    // final int numPendings = pendingCollabsList.size();
    //
    // getView().setAdminsCount(numAdmins);
    // getView().setCollabsCount(numCollabs);
    // getView().setPendingsCount(numPendings);
    //
    // if ((numAdmins + numCollabs) == 0) {
    // getView().showOrphan();
    // } else {
    // final boolean userIsAdmin = rights.isAdministrable();
    // final boolean userCanView = rights.isVisible();
    //
    // if (userCanView) {
    // for (final GroupDTO admin : adminsList) {
    // final String avatar = getAvatar(admin);
    // getView().addAdmin(admin, avatar, admin.getLongName(), "",
    // createMenuItems(admin, adminsMenuItemsRegistry));
    // }
    // getView().setCollabsVisible(numCollabs > 0);
    // for (final GroupDTO collab : collabList) {
    // final String avatar = getAvatar(collab);
    // getView().addCollab(collab, avatar, collab.getLongName(), "",
    // createMenuItems(collab, collabsMenuItemsRegistry));
    // }
    // if (userIsAdmin) {
    // getView().setPendingVisible(numPendings > 0);
    // for (final GroupDTO pendingCollab : pendingCollabsList) {
    // final String avatar = getAvatar(pendingCollab);
    // getView().addPending(pendingCollab, avatar, pendingCollab.getLongName(),
    // "",
    // createMenuItems(pendingCollab, pendingsMenuItemsRegistry));
    // }
    // } else {
    // getView().setPendingVisible(false);
    // }
    // getView().showMembers();
    // }
    // }
    // getView().setVisible(true);
    // }

    private void setParticipationState(final StateAbstractDTO state) {
        final ParticipationDataDTO participation = state.getParticipation();
        final AccessRights rights = state.getGroupRights();
        getView().clear();
        final List<GroupDTO> groupsIsAdmin = participation.getGroupsIsAdmin();
        final List<GroupDTO> groupsIsCollab = participation.getGroupsIsCollab();
        final int numAdmins = groupsIsAdmin.size();
        final int numCollaborators = groupsIsCollab.size();
        for (final GroupDTO group : groupsIsAdmin) {
            // getView().addParticipation(group, null, null, null,
            // actionsRegistry);
            // view.addItem(createGridItem(adminCategory, group, rights,
            // unJoinMenuItem));
        }
        for (final GroupDTO group : groupsIsCollab) {
            // view.addItem(createGridItem(collabCategory, group, rights,
            // unJoinMenuItem));
        }
        final int totalGroups = numAdmins + numCollaborators;
        if (totalGroups > 0) {
            getView().setParticipationCount(totalGroups);
            getView().setParticipationVisible(true);
        } else {
            getView().setParticipationVisible(false);
        }
    }
}