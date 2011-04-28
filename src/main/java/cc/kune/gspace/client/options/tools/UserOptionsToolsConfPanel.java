package cc.kune.gspace.client.options.tools;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class UserOptionsToolsConfPanel extends EntityOptionsToolsConfPanel implements UserOptionsToolsConfView {

    @Inject
    public UserOptionsToolsConfPanel(final I18nTranslationService i18n, final CoreResources res) {
        super(i18n, res);
    }

}
