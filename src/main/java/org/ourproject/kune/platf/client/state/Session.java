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

import org.ourproject.kune.workspace.client.dto.StateDTO;

/**
 * RESPONSABILITIES: - Mantains the user's application state - Generates
 * URLable's historyTokens
 * 
 * @author danigb
 * 
 */
public class Session {
    public final String user;
    private List ccLicenses;
    private List notCCLicenses;
    private StateDTO currentState;
    private String[] wsThemes;
    private String defaultWsTheme;

    public Session(final String userHash) {
	user = userHash;
	ccLicenses = null;
	notCCLicenses = null;
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

    public void setCurrent(final StateDTO currentState) {
	this.currentState = currentState;
    }

    public StateDTO getCurrentState() {
	return currentState;
    }

    public void setCurrentState(final StateDTO currentState) {
	this.currentState = currentState;
    }

    public void setDefaultWsTheme(final String defaultWsTheme) {
	this.defaultWsTheme = defaultWsTheme;

    }

    public void setWsThemes(final String[] wsThemes) {
	this.wsThemes = wsThemes;
    }

    public String[] getWsThemes() {
	return wsThemes;
    }

    public String getDefaultWsTheme() {
	return defaultWsTheme;
    }

}
