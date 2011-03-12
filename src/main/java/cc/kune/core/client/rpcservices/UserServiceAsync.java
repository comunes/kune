/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.rpcservices;

import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

    void createUser(UserDTO user, boolean wantPersonalHomepage, AsyncCallback<Void> asyncCallback);

    void getUserAvatarBaser64(String userHash, StateToken userToken, AsyncCallback<String> asyncCallback);

    void login(String nickOrEmail, String passwd, String waveCookieValue, AsyncCallback<UserInfoDTO> asyncCallback);

    void logout(String userHash, AsyncCallback<Void> asyncCallback);

    void onlyCheckSession(String userHash, AsyncCallback<Void> asyncCallback);

    void reloadUserInfo(String userHash, AsyncCallback<UserInfoDTO> asyncCallback);

    void setBuddiesVisibility(String userHash, StateToken groupToken, UserSNetVisibility visibility,
            AsyncCallback<Void> asyncCallback);

}
