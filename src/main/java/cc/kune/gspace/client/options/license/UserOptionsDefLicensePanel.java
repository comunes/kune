package cc.kune.gspace.client.options.license;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class UserOptionsDefLicensePanel extends EntityOptionsDefLicensePanel implements UserOptionsDefLicenseView {

    @Inject
    public UserOptionsDefLicensePanel(final I18nTranslationService i18n, final CoreResources res) {
        super(i18n, res);
    }

}
