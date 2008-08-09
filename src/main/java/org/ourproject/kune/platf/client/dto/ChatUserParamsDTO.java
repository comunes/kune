package org.ourproject.kune.platf.client.dto;

import com.calclab.emite.client.im.roster.RosterManager;
import com.calclab.emite.client.im.roster.RosterManager.SubscriptionMode;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatUserParamsDTO implements IsSerializable {

    private String avatar;

    private boolean publishRoster;

    private SubscriptionMode subscriptionMode;

    private String chatColor;

    public ChatUserParamsDTO() {
	this(null, false, SubscriptionMode.autoAcceptAll, null);
    };

    public ChatUserParamsDTO(final String avatar, final boolean publishRoster, final SubscriptionMode subscriptionMode,
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

    public RosterManager.SubscriptionMode getSubscriptionMode() {
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

    public void setSubscriptionMode(final RosterManager.SubscriptionMode subscriptionMode) {
	this.subscriptionMode = subscriptionMode;
    }

}
