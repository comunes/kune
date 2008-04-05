package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class ContentSetLanguageAction implements Action<String> {

    private final Session session;
    private final Workspace workspace;

    public ContentSetLanguageAction(final Session session, final Workspace workspace) {
        this.session = session;
        this.workspace = workspace;
    }

    public void execute(final String value) {
        onContentSetLanguage(value);
    }

    private void onContentSetLanguage(final String languageCode) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = session.getCurrentState();
        server.setLanguage(session.getUserHash(), currentState.getGroup().getShortName(), currentState.getDocumentId(),
                languageCode, new AsyncCallbackSimple<I18nLanguageDTO>() {
                    public void onSuccess(final I18nLanguageDTO lang) {
                        Site.hideProgress();
                        workspace.getContentSubTitleComponent().setContentLanguage(lang.getEnglishName());
                    }
                });
    }
}
