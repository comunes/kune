package cc.kune.blogs.client.actions;

import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.OpenContentMenuItem;

import com.google.inject.Inject;

public class OpenBlogMenuItem extends OpenContentMenuItem {

    @Inject
    public OpenBlogMenuItem(final I18nTranslationService i18n, final OpenContentAction action, final NavResources res) {
        super(i18n, action, res);
    }

}
