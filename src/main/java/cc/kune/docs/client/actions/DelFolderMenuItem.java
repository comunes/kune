package cc.kune.docs.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.docs.client.viewers.FolderViewerPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DelFolderMenuItem extends MenuItemDescriptor {

    public static class DelFolderAction extends RolAction {

        private final Provider<ContentServiceAsync> contentService;
        private final EventBus eventBus;
        private final I18nTranslationService i18n;
        private final Provider<FolderViewerPresenter> presenter;
        private final Session session;
        private final StateManager stateManager;

        @Inject
        public DelFolderAction(final EventBus eventBus, final StateManager stateManager, final Session session,
                final Provider<ContentServiceAsync> contentService, final I18nTranslationService i18n,
                final Provider<FolderViewerPresenter> presenter) {
            super(AccessRolDTO.Administrator, true);
            this.eventBus = eventBus;
            this.stateManager = stateManager;
            this.session = session;
            this.contentService = contentService;
            this.i18n = i18n;
            this.presenter = presenter;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final StateToken token = (StateToken) event.getTarget();
            ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"),
                    i18n.t("You will delete it with all its contents. Are you sure?"), i18n.t("Yes"), i18n.t("No"),
                    null, null, new OnAcceptCallback() {
                        @Override
                        public void onSuccess() {
                            NotifyUser.showProgress();
                            NotifyUser.info("Sorry, in development");
                            NotifyUser.hideProgress();
                        }
                    });
        }

    }

    @Inject
    public DelFolderMenuItem(final I18nTranslationService i18n, final DelFolderAction action, final CoreResources res) {
        super(action);
        this.withText(i18n.t("Delete")).withIcon(res.cancel());
    }

}
