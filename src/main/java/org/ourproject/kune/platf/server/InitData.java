package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;

public class InitData {
    private List<License> ccLicenses;
    private List<License> notCCLicenses;
    private User currentUser;
    private String chatHttpBase;
    private String chatDomain;
    private String chatRoomHost;

    public void setCCLicenses(final List<License> ccLicenses) {
	this.ccLicenses = ccLicenses;
    }

    public List<License> getCCLicenses() {
	return ccLicenses;
    }

    public void setNotCCLicenses(final List<License> notCCLicenses) {
	this.notCCLicenses = notCCLicenses;
    }

    public List<License> getNotCCLicenses() {
	return notCCLicenses;
    }

    public void setCurrentUser(final User currentUser) {
	this.currentUser = currentUser;
    }

    public User getCurrentUser() {
	return currentUser;
    }

    public void setChatHttpBase(String chatHttpBase) {
	this.chatHttpBase = chatHttpBase;
    }

    public String getChatHttpBase() {
	return chatHttpBase;
    }

    public void setChatDomain(String chatDomain) {
	this.chatDomain = chatDomain;
    }

    public String getChatDomain() {
	return chatDomain;
    }

    public void setChatRoomHost(String chatRoomHost) {
	this.chatRoomHost = chatRoomHost;
    }

    public String getChatRoomHost() {
	return chatRoomHost;
    }

}
