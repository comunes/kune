package org.ourproject.kune.workspace.client.title;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.NameInUseException;
import org.ourproject.kune.platf.client.errors.NameNotPermittedException;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Event2;
import com.calclab.suco.client.listener.Listener2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RenameAction {
    private final I18nTranslationService i18n;
    private final Provider<ContentServiceAsync> contentService;
    private final Session session;
    private final Event2<StateToken, String> onSuccess;
    private final Event2<StateToken, String> onFail;

    public RenameAction(I18nTranslationService i18n, Session session, Provider<ContentServiceAsync> contentService) {
        this.i18n = i18n;
        this.session = session;
        this.contentService = contentService;
        this.onSuccess = new Event2<StateToken, String>("onSuccess");
        this.onFail = new Event2<StateToken, String>("onFail");
    }

    public void onFail(final Listener2<StateToken, String> slot) {
        onFail.add(slot);
    }

    public void onSuccess(final Listener2<StateToken, String> slot) {
        onSuccess.add(slot);
    }

    public void rename(final StateToken token, final String oldName, final String newName) {
        if (!newName.equals(oldName)) {
            Site.showProgress(i18n.t("Renaming"));
            final AsyncCallback<String> asyncCallback = new AsyncCallback<String>() {
                public void onFailure(final Throwable caught) {
                    Site.hideProgress();
                    try {
                        throw caught;
                    } catch (final NameInUseException e) {
                        Site.error(i18n.tWithNT("This name already exists",
                                "It is used when a file or a folder with the same name already exists"));
                    } catch (final NameNotPermittedException e) {
                        Site.error(i18n.tWithNT("This name is not permitted",
                                "It is used when a file or a folder does not have a permitted name"));
                    } catch (final Throwable e) {
                        Site.error(i18n.t("Error renaming"));
                    }
                    onFail.fire(token, oldName);
                }

                public void onSuccess(final String finalNewName) {
                    Site.hideProgress();
                    onSuccess.fire(token, finalNewName);
                }
            };
            if (token.isComplete()) {
                contentService.get().renameContent(session.getUserHash(), token, newName, asyncCallback);
            } else {
                contentService.get().renameContainer(session.getUserHash(), token, newName, asyncCallback);
            }
        }
    }
}
