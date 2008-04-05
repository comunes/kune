package org.ourproject.kune.platf.server.users;

import java.util.List;

import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;

public class UserInfo {
    private String shortName;
    private String name;
    private String chatName;
    private String chatPassword;
    private String homePage;
    private List<Link> groupsIsAdmin;
    private List<Link> groupsIsCollab;
    private String userHash;
    private I18nLanguage language;
    private I18nCountry country;

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(final String homePage) {
        this.homePage = homePage;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(final String chatName) {
        this.chatName = chatName;
    }

    public String getChatPassword() {
        return chatPassword;
    }

    public void setChatPassword(final String chatPassword) {
        this.chatPassword = chatPassword;
    }

    public List<Link> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List<Link> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

    public List<Link> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List<Link> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }

    public String getUserHash() {
        return userHash;
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    public I18nCountry getCountry() {
        return country;
    }

    public void setCountry(final I18nCountry country) {
        this.country = country;
    }

}
