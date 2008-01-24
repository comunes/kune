/*
 *
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

package org.ourproject.kune.blogs.client.actions;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBox.AlertCallback;

public class SaveDocument implements Action {
    public void execute(final Object value, final Object extra, final Services services) {
        save(services, (StateDTO) value, (DocumentContent) extra);
    }

    private void save(final Services services, final StateDTO content, final DocumentContent documentContent) {
        Site.showProgressSaving();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.save(services.session.getUserHash(), services.session.getCurrentState().getGroup().getShortName(),
                content.getDocumentId(), content.getContent(), new AsyncCallback() {
                    public void onFailure(final Throwable caught) {
                        Site.hideProgress();
                        try {
                            throw caught;
                        } catch (final SessionExpiredException e) {
                            Site.doLogout();
                            MessageBox.alert(Kune.I18N.t("Alert"), Kune.I18N
                                    .t("Your session has expired. Please login again."), new AlertCallback() {
                                public void execute() {
                                    Site.doLogin(null);
                                }
                            });
                        } catch (final Throwable e) {
                            Site.error(Kune.I18N.t("Error saving document. Retrying..."));
                            documentContent.onSaveFailed();
                        }
                    }

                    public void onSuccess(final Object result) {
                        // Site.info(Kune.I18N.t("Document Saved"));
                        Site.hideProgress();
                        documentContent.onSaved();
                    }

                });
    }

}
