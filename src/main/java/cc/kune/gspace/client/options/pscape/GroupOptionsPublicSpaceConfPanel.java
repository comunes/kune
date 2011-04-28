package cc.kune.gspace.client.options.pscape;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class GroupOptionsPublicSpaceConfPanel extends EntityOptionsPublicSpaceConfPanel implements
        GroupOptionsPublicSpaceConfView {

    @Inject
    public GroupOptionsPublicSpaceConfPanel(final I18nTranslationService i18n, final FileDownloadUtils downUtils,
            final CoreResources res) {
        super(i18n, downUtils, res);
    }

}
