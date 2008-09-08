package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.google.gwt.user.client.ui.Image;

public interface EntityLogoView {

    // FIXME: Don' use Image
    public abstract void setLogo(final Image image);

    public abstract void setLogo(final String groupName);

    public abstract void setPutYourLogoVisible(final boolean visible);

    public abstract void setTheme(final WsTheme oldTheme, WsTheme newTheme);
}
