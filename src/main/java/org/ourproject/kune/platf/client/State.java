package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.HistoryTokenOld;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.workspace.actions.StateAction;

/**
 * RESPONSABILITIES:
 * - Mantains the user's application state
 * - Gererates URLable's historyTokens
 *
 * @author danigb
 *
 */
public class State {
    public final String user;
    private GroupDTO group;
    private String currentToolName;

    public State(String userHash) {
	user = userHash;
	group = null;
	currentToolName = null;
    }

    public void setGroup(GroupDTO group) {
	this.group = group;
    }

    public GroupDTO getGroup() {
	return group;
    }

    public boolean isCurrentTool(String toolName) {
	return currentToolName != null && currentToolName.equals(toolName);
    }

    public void setCurrentToolName(String toolName) {
	this.currentToolName = toolName;
    }

    public String encode(String toolName, String contextRef, String reference) {
	return HistoryTokenOld.encodeState(StateAction.NAME, group.getShortName(),
		toolName, contextRef, reference);
    }

    public boolean isCurrentGroup(String groupName) {
	return group.getShortName().equals(groupName);
    }
}
