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
 \*/
package org.ourproject.kune.platf.client.dto;

import com.calclab.emiteuimodule.client.SubscriptionMode;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatUserParamsDTO implements IsSerializable {

    private String avatar;

    private boolean publishRoster;

    private SubscriptionMode subscriptionMode;

    private String chatColor;

    public ChatUserParamsDTO() {
        this(null, false, SubscriptionMode.autoAcceptAll, null);
    };

    public ChatUserParamsDTO(final String avatar, final boolean publishRoster, final SubscriptionMode subscriptionMode,
            final String chatColor) {
        this.avatar = avatar;
        this.publishRoster = publishRoster;
        this.subscriptionMode = subscriptionMode;
        this.chatColor = chatColor;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getChatColor() {
        return chatColor;
    }

    public SubscriptionMode getSubscriptionMode() {
        return subscriptionMode;
    }

    public boolean isPublishRoster() {
        return publishRoster;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public void setChatColor(final String chatColor) {
        this.chatColor = chatColor;
    }

    public void setPublishRoster(final boolean publishRoster) {
        this.publishRoster = publishRoster;
    }

    public void setSubscriptionMode(final SubscriptionMode subscriptionMode) {
        this.subscriptionMode = subscriptionMode;
    }

}
