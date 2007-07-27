package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Content {
    private AccessRights access;
    private ArrayList<Tag> tags;
    private License license;
    private HashMap<User, Score> scores;
    
    public AccessRights getAccess() {
        return access;
    }
    public void setAccess(AccessRights access) {
        this.access = access;
    }
    public ArrayList<Tag> getTags() {
        return tags;
    }
    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
    public License getLicense() {
        return license;
    }
    public void setLicense(License license) {
        this.license = license;
    }
    public HashMap<User, Score> getScores() {
        return scores;
    }
    public void setScores(HashMap<User, Score> scores) {
        this.scores = scores;
    }
  
    
    
}
