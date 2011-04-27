package cc.kune.docs.client.actions;

import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.docs.shared.DocsConstants;
import cc.kune.gspace.client.actions.NewContainerBtn;

import com.google.inject.Inject;

public class NewFolderBtn extends NewContainerBtn {

    @Inject
    public NewFolderBtn(final I18nTranslationService i18n, final NewContainerAction action, final NavResources res) {
        super(i18n, action, res, i18n.t("New folder"),
                i18n.t("Create a new folder here. A folder will be a 'section' in the public web"),
                i18n.t("New folder"), DocsConstants.TYPE_FOLDER);
    }

}
