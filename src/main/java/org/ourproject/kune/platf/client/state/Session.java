/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    private List ccLicenses;
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    private List notCCLicenses;
    private StateDTO currentState;

    public Session(final String userHash) {
	user = userHash;
	group = null;
	currentToolName = null;
	ccLicenses = null;
	notCCLicenses = null;
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

    public List getCCLicenses() {
	return ccLicenses;
    }

    public void setCCLicenses(final List licenses) {
	this.ccLicenses = licenses;
    }

    public List getNotCCLicenses() {
	return notCCLicenses;
    }

    public void setNotCCLicenses(final List licensesNotCC) {
	this.notCCLicenses = licensesNotCC;
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
