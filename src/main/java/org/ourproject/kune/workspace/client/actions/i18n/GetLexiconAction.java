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

package org.ourproject.kune.workspace.client.actions.i18n;

import java.util.HashMap;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class GetLexiconAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onGetLexicon(services, (String) value);
    }

    private void onGetLexicon(final Services services, final String language) {
        final I18nServiceAsync server = I18nService.App.getInstance();
        server.getLexicon(language, new AsyncCallback() {
            public void onFailure(final Throwable caught) {
                FireLog.debug("Workspace adaptation to your language failed");
            }

            public void onSuccess(final Object result) {
                Kune.I18N.setLexicon((HashMap) result);
            }
        });

    }
}
