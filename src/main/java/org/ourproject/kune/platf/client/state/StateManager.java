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

import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot2;
import com.google.gwt.user.client.HistoryListener;

public interface StateManager extends HistoryListener {

    void addSiteToken(String token, Slot<StateToken> whenToken);

    void gotoContainer(Long containerId);

    void gotoToken(String token);

    void onGroupChanged(Slot2<String, String> slot);

    void onSocialNetworkChanged(Slot<StateDTO> slot);

    void onStateChanged(Slot<StateDTO> slot);

    void onToolChanged(Slot2<String, String> slot);

    void reload();

    void reloadContextAndTitles();

    void removeSiteToken(String token);

    void setRetrievedState(StateDTO content);

    void setSocialNetwork(SocialNetworkResultDTO socialNet);

    void setState(StateToken state);

}
