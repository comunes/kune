package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.themes.WsTheme;

public interface EntityLogoView {

    int LOGO_ICON_DEFAULT_HEIGHT = 60;
    int LOGO_ICON_DEFAULT_WIDTH = 468;

    int LOGO_ICON_MIN_HEIGHT = 28;
    int LOGO_ICON_MIN_WIDTH = 468;

    String LOGO_FORM_FIELD = "k-elogov-ff";

    void reloadImage(GroupDTO group);

    void setChangeYourLogoText();

    void setFullLogo(StateToken stateToken, boolean clipped);

    void setLargeFont();

    void setLogoImage(StateToken stateToken);

    void setLogoImageVisible(boolean visible);

    void setLogoText(final String groupName);

    void setMediumFont();

    void setPutYourLogoText();

    void setSetYourLogoVisible(final boolean visible);

    void setSmallFont();

    void setTheme(final WsTheme oldTheme, WsTheme newTheme);
}
