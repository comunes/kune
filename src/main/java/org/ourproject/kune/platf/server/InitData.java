package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.users.UserInfo;

public class InitData {
    private List<License> ccLicenses;
    private List<License> notCCLicenses;
    private UserInfo userInfo;
    private String chatHttpBase;
    private String chatDomain;
    private String chatRoomHost;

    public UserInfo getUserInfo() {
	return userInfo;
    }

    public void setUserInfo(final UserInfo currentUserInfo) {
	this.userInfo = currentUserInfo;
    }

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

    public void setChatHttpBase(final String chatHttpBase) {
	this.chatHttpBase = chatHttpBase;
    }

    public String getChatHttpBase() {
	return chatHttpBase;
    }

    public void setChatDomain(final String chatDomain) {
	this.chatDomain = chatDomain;
    }

    public String getChatDomain() {
	return chatDomain;
    }

    public void setChatRoomHost(final String chatRoomHost) {
	this.chatRoomHost = chatRoomHost;
    }

    public String getChatRoomHost() {
	return chatRoomHost;
    }

}
