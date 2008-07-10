package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.google.gwt.user.client.ui.Image;

public interface EntityLogo {

    public abstract void setLogo(final Image image);

    public abstract void setLogo(final String groupName);

    public abstract void setPutYourLogoVisible(final boolean visible);

    public abstract void setTheme(final WsTheme oldTheme, WsTheme newTheme);

}