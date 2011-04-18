package cc.kune.docs.client.actions;

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
import cc.kune.docs.client.DocsClientTool;
import cc.kune.gspace.client.actions.perspective.ViewPerspective;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewDocBtn extends ButtonDescriptor {

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
            stateManager.gotoStateToken(((HasContent) session.getCurrentState()).getContainer().getStateToken());
            NotifyUser.showProgressProcessing();
            contentService.get().addContent(session.getUserHash(), session.getCurrentStateToken(),
                    i18n.t("New document"), DocsClientTool.TYPE_WAVE, new AsyncCallbackSimple<StateContentDTO>() {
                        @Override
                        public void onSuccess(final StateContentDTO state) {
                            // contextNavigator.setEditOnNextStateChange(true);
                            stateManager.setRetrievedState(state);
                        }
                    });
            cache.removeContent(session.getCurrentStateToken());
        }
    }

    @Inject
    public NewDocBtn(final I18nTranslationService i18n, final NewDocAction action, final NavResources res,
            final GlobalShortcutRegister shorcutReg) {
        super(action);
        final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('N'));
        shorcutReg.put(shortcut, action);
        this.withText(i18n.t("New document")).withToolTip(
                "Create a New Document here. This document will be a new 'Page' in the public web if you publish it").withIcon(
                res.pageAdd()).in(ViewPerspective.class).withShortcut(shortcut).withStyles("k-def-docbtn");
    }

}
