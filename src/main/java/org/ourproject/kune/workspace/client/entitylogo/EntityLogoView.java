package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.themes.WsTheme;

public interface EntityLogoView {

    void setLogo(StateToken stateToken, boolean clipped);

    void setLogo(final String groupName);

    void setPutYourLogoVisible(final boolean visible);

    void setTheme(final WsTheme oldTheme, WsTheme newTheme);
}
