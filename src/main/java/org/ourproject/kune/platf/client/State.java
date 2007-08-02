package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dto.GroupDTO;

/**
 * Mantains all the application state
 *
 * @author danigb
 *
 */
public class State {
    private String userHash;
    private GroupDTO group;
    private String currentToolName;

    public State() {
	userHash = null;
	group = null;
	currentToolName = null;
    }

    public void setGroup(GroupDTO group) {
	this.group = group;
    }

    public GroupDTO getGroup() {
	return group;
    }

    public String getUserHash() {
	return userHash;
    }

    public void setUserHash(String userHash) {
	this.userHash = userHash;
    }

    public boolean isCurrentTool(String toolName) {
	return currentToolName != null && currentToolName.equals(toolName);
    }
}
