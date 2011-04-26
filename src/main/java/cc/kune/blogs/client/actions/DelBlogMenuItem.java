package cc.kune.blogs.client.actions;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.DelContainerMenuItem;

import com.google.inject.Inject;

public class DelBlogMenuItem extends DelContainerMenuItem {

    @Inject
    public DelBlogMenuItem(final I18nTranslationService i18n, final DelContainerAction action, final CoreResources res) {
        super(i18n, action, res);
    }

}
