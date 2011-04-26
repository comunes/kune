package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewContentBtn extends ButtonDescriptor {

    public static class NewContentAction extends RolAction {

        private final ContentCache cache;
        private final Provider<ContentServiceAsync> contentService;
        private final Session session;
        private final StateManager stateManager;

        @Inject
        public NewContentAction(final Session session, final StateManager stateManager,
                final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService,
                final ContentCache cache) {
            super(AccessRolDTO.Editor, true);
            this.session = session;
            this.stateManager = stateManager;
            this.contentService = contentService;
            this.cache = cache;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            NotifyUser.showProgressProcessing();
            stateManager.gotoStateToken(((HasContent) session.getCurrentState()).getContainer().getStateToken());
            contentService.get().addContent(session.getUserHash(), session.getCurrentStateToken(),
                    (String) getValue(NEW_NAME), (String) getValue(ID), new AsyncCallbackSimple<StateContentDTO>() {
                        @Override
                        public void onSuccess(final StateContentDTO state) {
                            stateManager.setRetrievedStateAndGo(state);
                            NotifyUser.hideProgress();
                            // stateManager.refreshCurrentGroupState();
                            // contextNavigator.setEditOnNextStateChange(true);
                        }
                    });
            cache.removeContent(session.getCurrentStateToken());
        }
    }

    private static final String ID = "ctnnewid";
    private static final String NEW_NAME = "ctnnewname";

    public NewContentBtn(final I18nTranslationService i18n, final NewContentAction action, final NavResources res,
            final GlobalShortcutRegister shorcutReg, final String title, final String tooltip, final String newName,
            final String id) {
        super(action);
        // The name given to this new content
        action.putValue(NEW_NAME, newName);
        action.putValue(ID, id);
        final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('N'));
        shorcutReg.put(shortcut, action);
        this.withText(title).withToolTip(tooltip).withIcon(res.pageAdd()).withShortcut(shortcut).withStyles(
                "k-def-docbtn");
    }

}
