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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserBuddiesDataDTO implements IsSerializable {

    public static UserBuddiesDataDTO NO_BUDDIES = new UserBuddiesDataDTO();

    private List<UserSimpleDTO> buddies;
    int otherExternalBuddies;

    public UserBuddiesDataDTO() {
        buddies = new ArrayList<UserSimpleDTO>();
        otherExternalBuddies = 0;
    }

    public boolean contains(String shortName) {
        for (UserSimpleDTO buddie : buddies) {
            if (buddie.getShortName().equals(shortName)) {
                return true;
            }
        }
        return false;
    }

    public List<UserSimpleDTO> getBuddies() {
        return buddies;
    }

    public int getOtherExternalBuddies() {
        return otherExternalBuddies;
    }

    public void setBuddies(List<UserSimpleDTO> buddies) {
        this.buddies = buddies;
    }

    public void setOtherExternalBuddies(int otherExternalBuddies) {
        this.otherExternalBuddies = otherExternalBuddies;
    }

    @Override
    public String toString() {
        return "UserBuddiesDataDTO[ext: " + otherExternalBuddies + " / int: " + buddies + "]";
    }
}