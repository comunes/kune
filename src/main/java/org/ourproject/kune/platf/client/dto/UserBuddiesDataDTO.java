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