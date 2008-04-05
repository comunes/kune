package org.ourproject.kune.workspace.client.actions.i18n;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.DoTranslationActionParams;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DoTranslationAction implements Action<DoTranslationActionParams> {

    private final Session session;

    public DoTranslationAction(final Session session) {
        this.session = session;
    }

    public void execute(final DoTranslationActionParams params) {
        onDoTranslationAction(params);
    }

    private void onDoTranslationAction(final DoTranslationActionParams params) {
        Site.showProgressSaving();
        final I18nServiceAsync server = I18nService.App.getInstance();
        server.setTranslation(session.getUserHash(), params.getId(), params.getText(), new AsyncCallback<String>() {
            public void onFailure(final Throwable caught) {
                Site.hideProgress();
                Site.error(Kune.I18N.t("Server error saving translation"));
            }

            public void onSuccess(final String result) {
                Site.hideProgress();
                Kune.I18N.setTranslationAfterSave(params.getTrKey(), result);
            }
        });
    }
}
