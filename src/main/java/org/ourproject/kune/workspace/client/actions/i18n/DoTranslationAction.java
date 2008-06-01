/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.actions.i18n;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.DoTranslationActionParams;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DoTranslationAction implements Action<DoTranslationActionParams> {

    private final Session session;
    private final I18nUITranslationService i18n;

    public DoTranslationAction(final Session session, final I18nUITranslationService i18n) {
        this.session = session;
        this.i18n = i18n;
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
                Site.error(i18n.t("Server error saving translation"));
            }

            public void onSuccess(final String result) {
                Site.hideProgress();
                i18n.setTranslationAfterSave(params.getTrKey(), result);
            }
        });
    }
}
