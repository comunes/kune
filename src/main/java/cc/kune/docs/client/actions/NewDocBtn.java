package cc.kune.docs.client.actions;

import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.docs.client.DocsClientTool;
import cc.kune.gspace.client.actions.NewContentBtn;

import com.google.inject.Inject;

public class NewDocBtn extends NewContentBtn {

    @Inject
    public NewDocBtn(final I18nTranslationService i18n, final NewContentAction action, final NavResources res,
            final GlobalShortcutRegister shorcutReg) {
        super(i18n, action, res, shorcutReg, i18n.t("New document"), i18n.t("Create a New Document here. "
                + "This document will be a new 'Page' in the public web if you publish it"), i18n.t("New document"),
                DocsClientTool.TYPE_WAVE);
    }

}
