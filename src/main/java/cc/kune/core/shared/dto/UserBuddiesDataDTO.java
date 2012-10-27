/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserBuddiesDataDTO implements IsSerializable {

  public static final UserBuddiesDataDTO NO_BUDDIES = new UserBuddiesDataDTO();

  private List<UserSimpleDTO> buddies;
  private int otherExtBuddies;

  public UserBuddiesDataDTO() {
    buddies = new ArrayList<UserSimpleDTO>();
    otherExtBuddies = 0;
  }

  public boolean contains(final String shortName) {
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

  public int getOtherExtBuddies() {
    return otherExtBuddies;
  }

  public void setBuddies(final List<UserSimpleDTO> buddies) {
    this.buddies = buddies;
  }

  public void setOtherExtBuddies(final int otherExternalBuddies) {
    this.otherExtBuddies = otherExternalBuddies;
  }

  @Override
  public String toString() {
    return "UserBuddiesDataDTO[ext: " + otherExtBuddies + " / int: " + buddies + "]";
  }
}
