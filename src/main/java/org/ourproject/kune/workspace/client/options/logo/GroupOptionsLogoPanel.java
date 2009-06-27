package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

public class GroupOptionsLogoPanel extends EntityOptionsLogoPanel {
    public static final String PANEL_ID = "k-golp-pan";
    public static final String BUTTON_ID = "k-golp-sendb";

    public GroupOptionsLogoPanel(final EntityOptionsLogoPresenter presenter, final WorkspaceSkeleton wskel,
            final I18nTranslationService i18n) {
        super(presenter, wskel, i18n, PANEL_ID, BUTTON_ID, FileConstants.GROUP_LOGO_FIELD);
    }

}
