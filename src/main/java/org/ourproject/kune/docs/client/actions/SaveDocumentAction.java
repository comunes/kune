
package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.SaveDocumentActionParams;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBox.AlertCallback;

public class SaveDocumentAction implements Action<SaveDocumentActionParams> {
    private final Session session;

    public SaveDocumentAction(final Session session) {
        this.session = session;
    }

    public void execute(final SaveDocumentActionParams params) {
        save(params);
    }

    private void save(final SaveDocumentActionParams params) {
        Site.showProgressSaving();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.save(session.getUserHash(), session.getCurrentState().getGroup().getShortName(), params.getStateDTO()
                .getDocumentId(), params.getStateDTO().getContent(), new AsyncCallback<Integer>() {
            public void onFailure(final Throwable caught) {
                Site.hideProgress();
                try {
                    throw caught;
                } catch (final SessionExpiredException e) {
                    Site.doLogout();
                    MessageBox.alert(Kune.I18N.t("Alert"),
                            Kune.I18N.t("Your session has expired. Please login again."), new AlertCallback() {
                                public void execute() {
                                    Site.doLogin(null);
                                }
                            });
                } catch (final Throwable e) {
                    Site.error(Kune.I18N.t("Error saving document. Retrying..."));
                    params.getDocumentContent().onSaveFailed();
                }
            }

            public void onSuccess(final Integer result) {
                Site.hideProgress();
                params.getDocumentContent().onSaved();
            }

        });
    }

}
