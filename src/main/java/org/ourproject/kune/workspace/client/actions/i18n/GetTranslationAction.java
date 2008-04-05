package org.ourproject.kune.workspace.client.actions.i18n;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.GetTranslationActionParams;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.state.Session;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class GetTranslationAction implements Action<GetTranslationActionParams> {
    private final Session session;

    public GetTranslationAction(final Session session) {
        this.session = session;
    }

    public void execute(final GetTranslationActionParams params) {
        onGetTranslation(params.getLanguage(), params.getText());
    }

    private void onGetTranslation(final String language, final String text) {
        final I18nServiceAsync server = I18nService.App.getInstance();
        server.getTranslation(session.getUserHash(), language, text, new AsyncCallback<String>() {
            public void onFailure(final Throwable caught) {
            }

            public void onSuccess(final String result) {
            }
        });

    }
}
