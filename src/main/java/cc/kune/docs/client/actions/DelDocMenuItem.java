package cc.kune.docs.client.actions;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.DelContentMenuItem;

import com.google.inject.Inject;

public class DelDocMenuItem extends DelContentMenuItem {

    @Inject
    public DelDocMenuItem(final I18nTranslationService i18n, final DelContentAction action, final CoreResources res) {
        super(i18n, action, res);
    }
}
