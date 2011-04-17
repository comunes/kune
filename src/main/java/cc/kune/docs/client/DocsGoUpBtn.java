package cc.kune.docs.client;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.perspective.ViewPerspective;

import com.google.inject.Inject;

public class DocsGoUpBtn extends ButtonDescriptor {

    public class DocNewAction extends AbstractExtendedAction {

        public DocNewAction() {
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
        }

    }

    @Inject
    public DocsGoUpBtn(final I18nTranslationService i18n, final DocNewAction action, final CoreResources res) {
        super(action);
        this.withToolTip(i18n.t("Go up: Open the container folder")).withIcon(res.folderGoUp()).in(
                ViewPerspective.class);
    }

}
