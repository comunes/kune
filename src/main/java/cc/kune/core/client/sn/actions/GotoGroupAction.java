package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class GotoGroupAction extends AbstractExtendedAction {

    private final StateManager stateManager;

    @Inject
    public GotoGroupAction(final StateManager stateManager, final I18nTranslationService i18n, final CoreResources res) {
        this.stateManager = stateManager;
        putValue(NAME, i18n.t("Visit this group homepage"));
        putValue(Action.SMALL_ICON, res.groupHome());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        StateToken token;
        final Object target = event.getTarget();
        if (target instanceof GroupDTO) {
            token = ((GroupDTO) target).getStateToken();
        } else {
            token = ((UserSimpleDTO) target).getStateToken();
        }
        stateManager.gotoStateToken(token);
    }

}
