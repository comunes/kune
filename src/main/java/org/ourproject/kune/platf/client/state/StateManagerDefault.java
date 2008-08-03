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
 */

package org.ourproject.kune.platf.client.state;

import java.util.HashMap;

import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.signal.Signal;
import com.calclab.suco.client.signal.Signal2;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;
import com.calclab.suco.client.signal.Slot2;

public class StateManagerDefault implements StateManager {
    private final ContentProvider contentProvider;
    private StateDTO oldState;
    private final Session session;
    private final HistoryWrapper history;
    private final HashMap<String, Slot<StateToken>> siteTokens;
    private final Signal<StateDTO> onStateChanged;
    private final Signal<StateDTO> onSocialNetworkChanged;
    private final Signal2<String, String> onToolChanged;
    private final Signal2<String, String> onGroupChanged;

    public StateManagerDefault(final ContentProvider contentProvider, final Session session,
	    final HistoryWrapper history) {
	this.contentProvider = contentProvider;
	this.session = session;
	this.history = history;
	this.oldState = null;
	this.onStateChanged = new Signal<StateDTO>("onStateChanged");
	this.onGroupChanged = new Signal2<String, String>("onGroupChanged");
	this.onToolChanged = new Signal2<String, String>("onToolChanged");
	this.onSocialNetworkChanged = new Signal<StateDTO>("onSocialNetworkChanged");
	session.onUserSignIn(new Slot<UserInfoDTO>() {
	    public void onEvent(final UserInfoDTO parameter) {
		restorePreviousState();
	    }
	});
	session.onUserSignOut(new Slot0() {
	    public void onEvent() {
		reload();
	    }
	});
	siteTokens = new HashMap<String, Slot<StateToken>>();
    }

    public void addSiteToken(final String token, final Slot<StateToken> slot) {
	siteTokens.put(token, slot);
    }

    public void gotoContainer(final Long containerId) {
	final StateToken newStateToken = session.getCurrentState().getStateToken();
	newStateToken.setDocument(null);
	newStateToken.setFolder(containerId.toString());
	setState(newStateToken);
    }

    public void gotoToken(final String token) {
	setState(new StateToken(token));
    }

    public void onGroupChanged(final Slot2<String, String> slot) {
	onGroupChanged.add(slot);
    }

    public void onHistoryChanged(final String historyToken) {
	final Slot<StateToken> tokenSlot = siteTokens.get(historyToken);
	Log.debug("history token: " + historyToken);
	if (tokenSlot == null) {
	    onHistoryChanged(new StateToken(historyToken));
	} else {
	    StateToken stateToken;
	    if (oldState == null) {
		// Starting with some token like "signin": load defContent also
		stateToken = new StateToken();
		onHistoryChanged(stateToken);
	    } else {
		stateToken = oldState.getStateToken();
	    }
	    tokenSlot.onEvent(stateToken);
	}
    }

    public void onSocialNetworkChanged(final Slot<StateDTO> slot) {
	onSocialNetworkChanged.add(slot);
    }

    public void onStateChanged(final Slot<StateDTO> slot) {
	onStateChanged.add(slot);
    }

    public void onToolChanged(final Slot2<String, String> slot) {
	onToolChanged.add(slot);
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
	contentProvider.getContent(session.getUserHash(), new StateToken(history.getToken()),
		new AsyncCallbackSimple<StateDTO>() {
		    public void onSuccess(final StateDTO newStateDTO) {
			loadContextOnly(newStateDTO);
			oldState = newStateDTO;
			// workspace.getContentTitleComponent().setState(oldState);
			// workspace.getContentSubTitleComponent().setState(oldState);
			Site.hideProgress();
		    }
		});
    }

    public void removeSiteToken(final String token) {
	siteTokens.remove(token);
    }

    public void setRetrievedState(final StateDTO content) {
	final StateToken state = content.getStateToken();
	contentProvider.cache(state, content);
	setState(state);
    }

    public void setSocialNetwork(final SocialNetworkResultDTO socialNet) {
	StateDTO state;
	if (session != null && (state = session.getCurrentState()) != null) {
	    // After a SN operation, usually returns a SocialNetworkResultDTO
	    // with new SN data and we refresh the state
	    // to avoid to reload() again the state
	    final SocialNetworkDTO groupMembers = socialNet.getGroupMembers();
	    final ParticipationDataDTO userParticipation = socialNet.getUserParticipation();
	    state.setGroupMembers(groupMembers);
	    state.setParticipation(userParticipation);
	    onSocialNetworkChanged.fire(state);
	}
    }

    public void setState(final StateToken state) {
	history.newItem(state.getEncoded());
    }

    private void checkGroupAndToolChange(final StateDTO oldState, final StateDTO newState) {
	final String oldGroupName = oldState != null ? oldState.getGroup().getShortName() : null;
	final String newGroupName = newState.getGroup().getShortName();
	final String oldToolName = oldState != null ? oldState.getToolName() : null;
	final String newToolName = newState.getToolName();
	if (oldState == null || !oldGroupName.equals(newGroupName)) {
	    onGroupChanged.fire(oldGroupName, newGroupName);
	}
	if (oldState == null || !oldToolName.equals(newToolName)) {
	    onToolChanged.fire(oldToolName, newToolName);
	}

    }

    private void loadContent(final StateDTO state) {
	session.setCurrent(state);
	onStateChanged.fire(state);
	// final GroupDTO group = state.getGroup();
	// app.setGroupState(group.getShortName());
	// final boolean isAdmin = state.getGroupRights().isAdministrable();
	// if (isAdmin) {
	// workspace.getThemeMenuComponent().setVisible(true);
	// } else {
	// workspace.getThemeMenuComponent().setVisible(false);
	// }
	// workspace.showGroup(group, isAdmin);

	// final String toolName = state.getToolName();
	// workspace.setTool(toolName);

	// final ClientTool clientTool = app.getTool(toolName);
	// clientTool.setContent(state);
	// clientTool.setContext(state);
	// workspace.getContentTitleComponent().setState(state);
	// workspace.getContentSubTitleComponent().setState(state);
	// workspace.getContentBottomToolBarComponent().setRate(state,
	// session.isLogged());
	// workspace.setContent(clientTool.getContent());
	// workspace.setContext(clientTool.getContext());
	// workspace.getLicenseComponent().setLicense(state);
	Site.hideProgress();
    }

    private void loadContextOnly(final StateDTO state) {
	session.setCurrent(state);
	// /final String toolName = state.getToolName();
	// /final ClientTool clientTool = app.getTool(toolName);
	// /clientTool.setContext(state);
	// /workspace.setContext(clientTool.getContext());
    }

    private void onHistoryChanged(final StateToken newState) {
	contentProvider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateDTO>() {
	    public void onSuccess(final StateDTO newState) {
		loadContent(newState);
		checkGroupAndToolChange(oldState, newState);
		oldState = newState;
	    }
	});
    }

    private void restorePreviousState() {
	if (oldState == null) {
	    onHistoryChanged(new StateToken());
	} else {
	    final StateDTO currentState = session.getCurrentState();
	    checkGroupAndToolChange(oldState, currentState);
	    loadContent(oldState);
	}
    }
}
