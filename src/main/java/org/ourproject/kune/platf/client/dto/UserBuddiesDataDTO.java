package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserBuddiesDataDTO implements IsSerializable {

    private List<UserSimpleDTO> buddies;
    int otherExternalBuddies;

    public UserBuddiesDataDTO() {
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
}