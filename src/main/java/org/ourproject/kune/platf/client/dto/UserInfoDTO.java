package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserInfoDTO implements IsSerializable {
    private String shortName;
    private String name;
    private String chatName;
    private String chatPassword;
    private String homePage;
    private String userHash;
    private I18nLanguageDTO language;
    private I18nCountryDTO country;
    private List<LinkDTO> groupsIsAdmin;
    private List<LinkDTO> groupsIsCollab;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatPassword() {
        return chatPassword;
    }

    public void setChatPassword(final String password) {
        this.chatPassword = password;

    }

    public void setChatName(final String chatName) {
        this.chatName = chatName;
    }

    public List<LinkDTO> getGroupsIsAdmin() {
        return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List<LinkDTO> groupsIsAdmin) {
        this.groupsIsAdmin = groupsIsAdmin;
    }

    public List<LinkDTO> getGroupsIsCollab() {
        return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List<LinkDTO> groupsIsCollab) {
        this.groupsIsCollab = groupsIsCollab;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(final String homePage) {
        this.homePage = homePage;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }

    public I18nLanguageDTO getLanguage() {
        return language;
    }

    public void setLanguage(final I18nLanguageDTO language) {
        this.language = language;
    }

    public I18nCountryDTO getCountry() {
        return country;
    }

    public void setCountry(final I18nCountryDTO country) {
        this.country = country;
    }

}
