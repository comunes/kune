package org.ourproject.kune.workspace.client.actions.i18n;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GetLexiconAction implements Action<String> {

    public void execute(final String value) {
        onGetLexicon(value);
    }

    private void onGetLexicon(final String language) {
        final I18nServiceAsync server = I18nService.App.getInstance();
        server.getLexicon(language, new AsyncCallback<HashMap<String, String>>() {
            public void onFailure(final Throwable caught) {
                Log.debug("Workspace adaptation to your language failed");
            }

            public void onSuccess(final HashMap<String, String> result) {
                Kune.I18N.setLexicon(result);
            }
        });

    }
}
