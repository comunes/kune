
package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class StateManagerDefault implements StateManager {
    private final Application app;
    private final ContentProvider provider;
    private final Workspace workspace;
    private StateDTO oldState;
    private String lastTheme;
    private final Session session;
    private final HistoryWrapper history;

    public StateManagerDefault(final ContentProvider provider, final Application app, final Session session,
            final HistoryWrapper history) {
        this.provider = provider;
        this.app = app;
        this.session = session;
        this.history = history;
        this.workspace = app.getWorkspace();
        this.oldState = null;
    }

    /**
     * <p>
     * Reload current state (using client cache if available)
     * </p>
     */
    public void reload() {
        onHistoryChanged(history.getToken());
    }

    public void reloadContextAndTitles() {
        provider.getContent(session.getUserHash(), new StateToken(history.getToken()),
                new AsyncCallbackSimple<StateDTO>() {
                    public void onSuccess(final StateDTO newStateDTO) {
                        loadContextOnly(newStateDTO);
                        oldState = newStateDTO;
                        workspace.getContentTitleComponent().setState(oldState);
                        workspace.getContentSubTitleComponent().setState(oldState);
                        Site.hideProgress();
                    }
                });
    }

    public void onHistoryChanged(final String historyToken) {
        String oldStateEncoded = "";
        if (oldState != null) {
            oldStateEncoded = oldState.getStateToken().getEncoded();
        }
        if (historyToken.equals(Site.NEWGROUP_TOKEN)) {
            Site.doNewGroup(oldStateEncoded);
        } else if (historyToken.equals(Site.LOGIN_TOKEN)) {
            Site.doLogin(oldStateEncoded);
        } else if (historyToken.equals(Site.TRANSLATE_TOKEN)) {
            app.getDispatcher().fire(WorkspaceEvents.SHOW_TRANSLATOR, null);
        } else if (historyToken.equals(Site.FIXME_TOKEN)) {
            if (oldState == null) {
                onHistoryChanged(new StateToken());
            } else {
                loadContent(oldState);
            }
        } else {
            onHistoryChanged(new StateToken(historyToken));
        }
    }

    public void setRetrievedState(final StateDTO content) {
        final StateToken state = content.getStateToken();
        provider.cache(state, content);
        setState(state);
    }

    public void setState(final StateToken state) {
        history.newItem(state.getEncoded());
    }

    private void onHistoryChanged(final StateToken newState) {
        provider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateDTO>() {
            public void onSuccess(final StateDTO newStateDTO) {
                loadContent(newStateDTO);
                oldState = newStateDTO;
            }
        });
    }

    private void loadContextOnly(final StateDTO state) {
        session.setCurrent(state);
        final String toolName = state.getToolName();
        final ClientTool clientTool = app.getTool(toolName);
        clientTool.setContext(state);
        workspace.setContext(clientTool.getContext());
    }

    private void loadContent(final StateDTO state) {
        session.setCurrent(state);
        final GroupDTO group = state.getGroup();
        app.setGroupState(group.getShortName());
        boolean isAdmin = state.getGroupRights().isAdministrable();
        if (isAdmin) {
            workspace.getThemeMenuComponent().setVisible(true);
        } else {
            workspace.getThemeMenuComponent().setVisible(false);
        }
        setWsTheme(group);
        workspace.showGroup(group, isAdmin);
        final String toolName = state.getToolName();
        workspace.setTool(toolName);

        Site.sitebar.setState(state);
        final ClientTool clientTool = app.getTool(toolName);
        clientTool.setContent(state);
        clientTool.setContext(state);
        workspace.getContentTitleComponent().setState(state);
        workspace.getContentSubTitleComponent().setState(state);
        workspace.getContentBottomToolBarComponent().setRate(state, session.isLogged());
        workspace.setContent(clientTool.getContent());
        workspace.setContext(clientTool.getContext());
        workspace.getLicenseComponent().setLicense(state);
        workspace.getTagsComponent().setState(state);
        setSocialNetwork(state);
        workspace.getGroupSummaryComponent().setGroupSummary(state);
        Site.hideProgress();
    }

    private void setWsTheme(final GroupDTO group) {
        String nextTheme = group.getWorkspaceTheme();
        if (lastTheme == null || !lastTheme.equals(nextTheme)) {
            workspace.setTheme(nextTheme);
        }
        lastTheme = nextTheme;
    }

    public void setSocialNetwork(final SocialNetworkResultDTO socialNet) {
        StateDTO state;
        if (session != null && (state = session.getCurrentState()) != null) {
            // After a SN operation, usually returns a SocialNetworkResultDTO
            // with new SN data and we refresh the state
            // to avoid to reload() again the state
            SocialNetworkDTO groupMembers = socialNet.getGroupMembers();
            ParticipationDataDTO userParticipation = socialNet.getUserParticipation();
            state.setGroupMembers(groupMembers);
            state.setParticipation(userParticipation);
            setSocialNetwork(state);
        }
    }

    private void setSocialNetwork(final StateDTO state) {
        workspace.getGroupMembersComponent().setGroupMembers(state);
        workspace.getParticipationComponent().setParticipation(state);
    }
}
