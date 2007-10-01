package org.ourproject.kune.platf.server.users;

import java.util.List;

public class UserInfo {
    private String name;
    private String chatName;
    private String chatPassword;
    private String homePage;
    private List<Link> groupsIsAdmin;
    private List<Link> groupsIsEditor;

    public String getHomePage() {
	return homePage;
    }

    public void setHomePage(String homePage) {
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

    public List<Link> getGroupsIsEditor() {
	return groupsIsEditor;
    }

    public void setGroupsIsCollab(final List<Link> groupsIsEditor) {
	this.groupsIsEditor = groupsIsEditor;
    }

    public List<Link> getGroupsIsAdmin() {
	return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List<Link> groupsIsAdmin) {
	this.groupsIsAdmin = groupsIsAdmin;
    }

}
