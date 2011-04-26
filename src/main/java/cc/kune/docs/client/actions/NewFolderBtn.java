package cc.kune.docs.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.docs.client.DocsClientTool;
import cc.kune.gspace.client.actions.perspective.ViewActionsGroup;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewFolderBtn extends ButtonDescriptor {

    public static class NewDocAction extends RolAction {

        private final ContentCache cache;
        private final Provider<ContentServiceAsync> contentService;
        private final I18nTranslationService i18n;
        private final Session session;
        private final StateManager stateManager;

        @Inject
        public NewDocAction(final Session session, final StateManager stateManager, final I18nTranslationService i18n,
                final Provider<ContentServiceAsync> contentService, final ContentCache cache) {
            super(AccessRolDTO.Editor, true);
            this.session = session;
            this.stateManager = stateManager;
            this.i18n = i18n;
            this.contentService = contentService;
            this.cache = cache;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            NotifyUser.showProgressProcessing();
            stateManager.gotoStateToken(((HasContent) session.getCurrentState()).getContainer().getStateToken());
            contentService.get().addFolder(session.getUserHash(), session.getCurrentStateToken(), i18n.t("New folder"),
                    DocsClientTool.TYPE_FOLDER, new AsyncCallbackSimple<StateContainerDTO>() {
                        @Override
                        public void onSuccess(final StateContainerDTO state) {
                            // contextNavigator.setEditOnNextStateChange(true);
                            stateManager.setRetrievedStateAndGo(state);
                            NotifyUser.hideProgress();
                        }
                    });
            cache.removeContent(session.getCurrentStateToken());
        }

    }

    @Inject
    public NewFolderBtn(final I18nTranslationService i18n, final NewDocAction action, final NavResources res) {
        super(action);
        this.withText(i18n.t("New folder")).withToolTip(
                i18n.t("Create a new folder here. A folder will be a 'section' in the public web")).withIcon(
                res.folderAdd()).in(ViewActionsGroup.class).withStyles("k-def-docbtn");
    }
}
