package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dto.GroupDTO;

/**
 * Mantains all the application state
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
}
