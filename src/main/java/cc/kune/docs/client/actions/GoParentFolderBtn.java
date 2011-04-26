package cc.kune.docs.client.actions;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.GoParentContainerBtn;

import com.google.inject.Inject;

public class GoParentFolderBtn extends GoParentContainerBtn {

    @Inject
    public GoParentFolderBtn(final I18nTranslationService i18n, final GoParentContainerAction action, final CoreResources res) {
        super(i18n, action, res);
    }

}
