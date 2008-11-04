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
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.calclab.suco.client.listener.Listener2;

public interface StateManager {

    void addBeforeStateChangeListener(BeforeActionListener listener);

    void addSiteToken(String token, Listener0 whenToken);

    void gotoToken(StateToken newToken);

    void gotoToken(String newToken);

    void onGroupChanged(Listener2<String, String> listener);

    void onSocialNetworkChanged(Listener<StateAbstractDTO> listener);

    void onStateChanged(Listener<StateAbstractDTO> listener);

    void onToolChanged(Listener2<String, String> listener);

    void reload();

    void removeBeforeStateChangeListener(BeforeActionListener listener);

    void removeSiteToken(String token);

    void restorePreviousToken();

    void resumeTokenChange();

    void setRetrievedState(StateAbstractDTO state);

    void setSocialNetwork(SocialNetworkResultDTO socialNet);

}
