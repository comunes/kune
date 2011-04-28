package cc.kune.gspace.client.options.license;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class GroupOptionsDefLicensePanel extends EntityOptionsDefLicensePanel implements GroupOptionsDefLicenseView {

    @Inject
    public GroupOptionsDefLicensePanel(final I18nTranslationService i18n, final CoreResources res) {
        super(i18n, res);
    }

}
