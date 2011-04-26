package cc.kune.blogs.client.actions;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.DelContentMenuItem;

import com.google.inject.Inject;

public class DelPostMenuItem extends DelContentMenuItem {

    @Inject
    public DelPostMenuItem(final I18nTranslationService i18n, final DelContentAction action, final CoreResources res) {
        super(i18n, action, res);
    }
}
