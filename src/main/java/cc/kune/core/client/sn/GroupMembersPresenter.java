package cc.kune.core.client.sn;

import java.util.List;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.sn.actions.registry.AbstractSocialNetworActionsRegistry;
import cc.kune.core.client.sn.actions.registry.SNAdminsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.SNCollabsMenuItemsRegistry;
import cc.kune.core.client.sn.actions.registry.SNPendingsMenuItemsRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SocialNetworkChangedEvent;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class GroupMembersPresenter extends
        Presenter<GroupMembersPresenter.GroupMembersView, GroupMembersPresenter.GroupMembersProxy> {

    @ProxyCodeSplit
    public interface GroupMembersProxy extends Proxy<GroupMembersPresenter> {
    }

    public interface GroupMembersView extends View {
        int AVATARLABELMAXSIZE = 15;
        int AVATARSIZE = 32;
        String NOAVATAR = "";

        void addAdmin(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void addCollab(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void addPending(GroupDTO group, String avatarUrl, String tooltip, String tooltipTitle,
                GuiActionDescCollection menu);

        void clear();

        IsActionExtensible getBottomToolbar();

        void setVisible(boolean b);

        void showMemberNotPublic();

        void showMembers();

        void showOrphan();
    }

    private final SNAdminsMenuItemsRegistry adminsMenuItemsRegistry;
    private final SNCollabsMenuItemsRegistry collabsMenuItemsRegistry;

    private final Provider<FileDownloadUtils> downloadProvider;
    private final SNPendingsMenuItemsRegistry pendingsMenuItemsRegistry;
    private final Session session;

    @Inject
    public GroupMembersPresenter(final EventBus eventBus, final GroupMembersView view, final GroupMembersProxy proxy,
            final StateManager stateManager, final Session session, final Provider<FileDownloadUtils> downloadProvider,
            final SNAdminsMenuItemsRegistry adminsMenuItemsRegistry,
            final SNCollabsMenuItemsRegistry collabsMenuItemsRegistry,
            final SNPendingsMenuItemsRegistry pendingsMenuItemsRegistry) {
        super(eventBus, view, proxy);
        this.session = session;
        this.downloadProvider = downloadProvider;
        this.adminsMenuItemsRegistry = adminsMenuItemsRegistry;
        this.collabsMenuItemsRegistry = collabsMenuItemsRegistry;
        this.pendingsMenuItemsRegistry = pendingsMenuItemsRegistry;
        stateManager.onStateChanged(new StateChangedEvent.StateChangedHandler() {
            @Override
            public void onStateChanged(final StateChangedEvent event) {
                GroupMembersPresenter.this.onStateChanged(event.getState());
            }
        });
        stateManager.onSocialNetworkChanged(new SocialNetworkChangedEvent.SocialNetworkChangedHandler() {

            @Override
            public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
                GroupMembersPresenter.this.onStateChanged(event.getState());
            }
        });
        session.onInitDataReceived(new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                // FIXME
            }
        });
        createActions();
    }

    private void createActions() {
        // TODO
    }

    private GuiActionDescCollection createMenuItems(final GroupDTO group,
            final AbstractSocialNetworActionsRegistry registry) {
        final GuiActionDescCollection items = new GuiActionDescCollection();
        for (final Provider<MenuItemDescriptor> provider : registry) {
            final MenuItemDescriptor menuItem = provider.get();
            menuItem.setItem(group);
            items.add(menuItem);
        }
        return items;
    }

    private String getAvatar(final GroupDTO admin) {
        return admin.hasLogo() ? downloadProvider.get().getLogoImageUrl(admin.getStateToken()) : "images/unknown.jpg";
    }

    private void onStateChanged(final StateAbstractDTO state) {
        if (state.getGroup().isPersonal()) {
            getView().setVisible(false);
        } else {
            if (state.getSocialNetworkData().isMembersVisible()) {
                setGroupMembers(state.getGroupMembers(), state.getGroupRights());
            } else {
                getView().clear();
                getView().showMemberNotPublic();
                getView().setVisible(true);
            }
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

        if ((numAdmins + numCollabs + numPendings) == 0) {
            getView().showOrphan();
        } else {
            final boolean userIsAdmin = rights.isAdministrable();
            final boolean userCanView = rights.isVisible();

            if (userCanView) {
                for (final GroupDTO admin : adminsList) {
                    final String avatar = getAvatar(admin);
                    getView().addAdmin(admin, avatar, admin.getLongName(), "",
                            createMenuItems(admin, adminsMenuItemsRegistry));

                }
                for (final GroupDTO collab : collabList) {
                    final String avatar = getAvatar(collab);
                    getView().addCollab(collab, avatar, collab.getLongName(), "",
                            createMenuItems(collab, collabsMenuItemsRegistry));
                }
                if (userIsAdmin) {
                    for (final GroupDTO pendingCollab : pendingCollabsList) {
                        final String avatar = getAvatar(pendingCollab);
                        getView().addCollab(pendingCollab, avatar, pendingCollab.getLongName(), "",
                                createMenuItems(pendingCollab, pendingsMenuItemsRegistry));
                    }
                }
                getView().showMembers();
            }
        }
        getView().setVisible(true);
    }
}