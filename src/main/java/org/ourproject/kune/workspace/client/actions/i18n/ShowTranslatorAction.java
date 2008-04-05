package org.ourproject.kune.workspace.client.actions.i18n;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

@SuppressWarnings("unchecked")
public class ShowTranslatorAction implements Action {

    private final Session session;
    private final Workspace workspace;

    public ShowTranslatorAction(final Session session, final Workspace workspace) {
        this.session = session;
        this.workspace = workspace;
    }

    public void execute(final Object value) {
        onShowTranslatorAction();
    }

    private void onShowTranslatorAction() {
        Site.showProgressLoading();
        if (session.isLogged()) {
            workspace.getI18nTranslatorComponent().show();
        } else {
            Site.info(Kune.I18N.t("Sign in or register to help with translation"));
        }
        Site.hideProgress();
    }
}
