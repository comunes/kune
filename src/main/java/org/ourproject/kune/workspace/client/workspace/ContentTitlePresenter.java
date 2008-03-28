/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
