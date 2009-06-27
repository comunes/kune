package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

public class UserOptionsLogoPanel extends EntityOptionsLogoPanel {
    public static final String PANEL_ID = "k-uolp-pan";
    public static final String BUTTON_ID = "k-uolp-sendb";

    public UserOptionsLogoPanel(final EntityOptionsLogoPresenter presenter, final WorkspaceSkeleton wskel,
            final I18nTranslationService i18n) {
        super(presenter, wskel, i18n, PANEL_ID, BUTTON_ID, FileConstants.USER_LOGO_FIELD);
    }

}
