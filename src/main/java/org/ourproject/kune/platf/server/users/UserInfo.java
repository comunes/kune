package org.ourproject.kune.platf.server.users;

public class UserInfo {
    private String name;
    private String chatName;
    private String chatPassword;

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

}
