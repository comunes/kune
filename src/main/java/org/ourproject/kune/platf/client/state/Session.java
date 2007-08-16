package org.ourproject.kune.platf.client.state;

import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.workspace.client.dto.StateDTO;

/**
 * RESPONSABILITIES: - Mantains the user's application state - Gererates
 * URLable's historyTokens
 * 
 * @author danigb
 * 
 */
public class Session {
    public final String user;
    private GroupDTO group;
    private String currentToolName;
    private List licenses;
    private List licensesNotCC;
    private StateDTO currentState;

    public Session(final String userHash) {
	user = userHash;
	group = null;
	currentToolName = null;
	licenses = null;
	licensesNotCC = null;
    }

    public void setGroup(final GroupDTO group) {
	this.group = group;
    }

    public GroupDTO getGroup() {
	return group;
    }

    public boolean isCurrentTool(final String toolName) {
	return currentToolName != null && currentToolName.equals(toolName);
    }

    public void setCurrentToolName(final String toolName) {
	this.currentToolName = toolName;
    }

    public boolean isCurrentGroup(final String groupName) {
	return group.getShortName().equals(groupName);
    }

    public List getLicenses() {
	return licenses;
    }

    public void setLicenses(final List licenses) {
	this.licenses = licenses;
    }

    public List getLicensesNotCC() {
	return licensesNotCC;
    }

    public void setLicensesNotCC(final List licensesNotCC) {
	this.licensesNotCC = licensesNotCC;
    }

    public boolean currentGroupIs(final String groupName) {
	return group != null && group.getShortName().equals(groupName);
    }

    public void setCurrent(final StateDTO currentState) {
	this.currentState = currentState;
    }

    public StateDTO getCurrentState() {
	return currentState;
    }

    public void setCurrentState(final StateDTO currentState) {
	this.currentState = currentState;
    }

}
