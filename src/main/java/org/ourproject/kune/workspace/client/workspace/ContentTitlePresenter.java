
package org.ourproject.kune.workspace.client.workspace;

import java.util.Date;

import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentTitlePresenter implements ContentTitleComponent {

    private ContentTitleView view;

    public void init(final ContentTitleView view) {
        this.view = view;
    }

    public void setState(final StateDTO state) {
        if (state.hasDocument()) {
            setContentTitle(state.getTitle(), state.getContentRights().isEditable());
            setContentDateVisible(true);
            setContentDate(state.getPublishedOn());
        } else {
            if (state.getFolder().getParentFolderId() == null) {
                // We translate root folder names (documents, chat room,
                // etcetera)
                setContentTitle(Kune.I18N.t(state.getTitle()), false);
            } else {
                setContentTitle(state.getTitle(), state.getContentRights().isEditable());
            }
            setContentDateVisible(false);
        }
    }

    public void setContentDate(final Date publishedOn) {
        DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy, Z");
        view.setContentDate(Kune.I18N.t("Published on: [%s]", fmt.format(publishedOn)));
    }

    private void setContentTitle(final String title, final boolean editable) {
        view.setContentTitle(title);
        view.setContentTitleEditable(editable);
    }

    private void setContentDateVisible(final boolean visible) {
        view.setDateVisible(visible);
    }

    public View getView() {
        return view;
    }

    public void onTitleRename(final String text) {
        Site.showProgressSaving();
        AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onFailure(final Throwable caught) {
                view.restoreOldTitle();
                KuneErrorHandler.getInstance().process(caught);
            }

            public void onSuccess(final String result) {
                Site.hideProgress();
                view.setContentTitle(result);
                DefaultDispatcher.getInstance().fire(WorkspaceEvents.RELOAD_CONTEXT, null);
            }
        };
        DefaultDispatcher.getInstance().fire(DocsEvents.RENAME_CONTENT,
                new ParamCallback<String, String>(text, callback));
    }

}
