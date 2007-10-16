package org.ourproject.kune.platf.client.services;

import com.google.gwt.i18n.client.Dictionary;

public class ColorTheme {

    private Dictionary theme;

    public ColorTheme() {
	this("defaultKuneTheme");
    }

    public ColorTheme(final String theme) {
	this.theme = Dictionary.getDictionary(theme);
    }

    public void setTheme(final String theme) {
	this.theme = Dictionary.getDictionary(theme);
    }

    public String getToolSelected() {
	return theme.get("toolSelected");
    }

    public String getToolUnselected() {
	return theme.get("toolUnselected");
    }

    public String getContentMainBorder() {
	return theme.get("contentMainBorder");
    }

    public String getContentTitle() {
	return theme.get("contentTitle");
    }

    public String getContentTitleText() {
	return theme.get("contentTitleText");
    }

    public String getContentSubTitleText() {
	return theme.get("contentSubTitleText");
    }

    public String getContentBottomText() {
	return theme.get("contentBottomText");
    }

    public String getContext() {
	return theme.get("context");
    }

    public String getSplitter() {
	return theme.get("splitter");
    }

    public String getGroupMembersDD() {
	return theme.get("groupMembersDD");
    }

    public String getParticipationDD() {
	return theme.get("participationDD");
    }

    public String getBuddiesPresenceDD() {
	return theme.get("buddiesPresenceDD");
    }

}