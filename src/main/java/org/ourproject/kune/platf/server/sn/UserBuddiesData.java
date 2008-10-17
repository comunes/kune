package org.ourproject.kune.platf.server.sn;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.server.domain.User;

public class UserBuddiesData {

    private List<User> buddies;
    int otherExternalBuddies;

    public UserBuddiesData() {
        otherExternalBuddies = 0;
        buddies = new ArrayList<User>();
    }

    public List<User> getBuddies() {
        return buddies;
    }

    public int getOtherExternalBuddies() {
        return otherExternalBuddies;
    }

    public void setBuddies(List<User> buddies) {
        this.buddies = buddies;
    }

    public void setOtherExternalBuddies(int otherExternalBuddies) {
        this.otherExternalBuddies = otherExternalBuddies;
    }

    @Override
    public String toString() {
        return "UserBuddiesData[ext: " + otherExternalBuddies + " / int: " + buddies + "]";
    }
}
