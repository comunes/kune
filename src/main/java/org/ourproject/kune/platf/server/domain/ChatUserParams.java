package org.ourproject.kune.platf.server.domain;

import java.io.Serializable;

import com.calclab.emiteuimodule.client.SubscriptionMode;

public class ChatUserParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String avatar;

    private boolean publishRoster;

    private SubscriptionMode subscriptionMode;

    private String chatColor;

    public ChatUserParams() {
	this(null, false, SubscriptionMode.autoAcceptAll, null);
    };

    public ChatUserParams(final String avatar, final boolean publishRoster, final SubscriptionMode subscriptionMode,
	    final String chatColor) {
	this.avatar = avatar;
	this.publishRoster = publishRoster;
	this.subscriptionMode = subscriptionMode;
	this.chatColor = chatColor;
    }

    public String getAvatar() {
	return avatar;
    }

    public String getChatColor() {
	return chatColor;
    }

    public SubscriptionMode getSubscriptionMode() {
	return subscriptionMode;
    }

    public boolean isPublishRoster() {
	return publishRoster;
    }

    public void setAvatar(final String avatar) {
	this.avatar = avatar;
    }

    public void setChatColor(final String chatColor) {
	this.chatColor = chatColor;
    }

    public void setPublishRoster(final boolean publishRoster) {
	this.publishRoster = publishRoster;
    }

    public void setSubscriptionMode(final SubscriptionMode subscriptionMode) {
	this.subscriptionMode = subscriptionMode;
    }

}
