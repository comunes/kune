package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.NotNull;

import com.calclab.emite.client.im.roster.RosterManager;
import com.calclab.emite.client.im.roster.RosterManager.SubscriptionMode;

@Entity
@Indexed
@Table(name = "usersparams")
public class UserParams {

    @Id
    @DocumentId
    @GeneratedValue
    private Long id;

    private String avatar;

    @NotNull
    private boolean publishRoster;

    @NotNull
    private SubscriptionMode subscriptionMode;

    @NotNull
    private String chatColor;

    public UserParams(final String avatar, final boolean publishRoster, final SubscriptionMode subscriptionMode,
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

    public Long getId() {
	return id;
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
