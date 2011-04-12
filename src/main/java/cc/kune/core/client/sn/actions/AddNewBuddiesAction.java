package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class AddNewBuddiesAction extends AbstractExtendedAction {

    private final I18nTranslationService i18n;

    @Inject
    public AddNewBuddiesAction(final I18nTranslationService i18n, final CoreResources res) {
        this.i18n = i18n;
        putValue(Action.NAME, i18n.t("Add a new buddy"));
        putValue(Action.SMALL_ICON, res.addGreen());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
    }

}
