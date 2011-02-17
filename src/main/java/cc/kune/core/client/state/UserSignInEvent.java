/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.state;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class UserSignInEvent extends GwtEvent<UserSignInEvent.UserSignInHandler> {

    public interface HasUserSignInHandlers extends HasHandlers {
        HandlerRegistration addUserSignInHandler(UserSignInHandler handler);
    }

    public interface UserSignInHandler extends EventHandler {
        public void onUserSignIn(UserSignInEvent event);
    }

    private static final Type<UserSignInHandler> TYPE = new Type<UserSignInHandler>();

    public static Type<UserSignInHandler> getType() {
        return TYPE;
    }

    private final cc.kune.core.shared.dto.UserInfoDTO userInfo;

    public UserSignInEvent(final cc.kune.core.shared.dto.UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    protected void dispatch(final UserSignInHandler handler) {
        handler.onUserSignIn(this);
    }

    @Override
    public boolean equals(final Object other) {
        if (other != null && other.getClass().equals(this.getClass())) {
            final UserSignInEvent o = (UserSignInEvent) other;
            return true && ((o.userInfo == null && this.userInfo == null) || (o.userInfo != null && o.userInfo.equals(this.userInfo)));
        }
        return false;
    }

    @Override
    public Type<UserSignInHandler> getAssociatedType() {
        return TYPE;
    }

    public cc.kune.core.shared.dto.UserInfoDTO getUserInfo() {
        return userInfo;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + getClass().hashCode();
        hashCode = (hashCode * 37) + (userInfo == null ? 1 : userInfo.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "UserSignInEvent[" + userInfo + "]";
    }

}
