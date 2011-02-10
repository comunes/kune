package cc.kune.core.client.sn.actions;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class GotoPersonAction extends GotoGroupAction {

    @Inject
    public GotoPersonAction(final StateManager stateManager, final I18nTranslationService i18n, final CoreResources res) {
        super(stateManager, i18n, res);
        putValue(NAME, i18n.t("Visit his/her homepage"));
    }

}
