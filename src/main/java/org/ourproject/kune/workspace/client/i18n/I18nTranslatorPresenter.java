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
package org.ourproject.kune.workspace.client.i18n;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class I18nTranslatorPresenter implements I18nTranslator {
    private I18nTranslatorView view;
    private final Session session;
    private final I18nServiceAsync i18nService;
    private final I18nUITranslationService i18n;

    public I18nTranslatorPresenter(final Session session, final I18nServiceAsync i18nService,
            final I18nUITranslationService i18n) {
        this.session = session;
        this.i18nService = i18nService;
        this.i18n = i18n;
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                view.hideTranslatorAndIcon();
            }
        });
    }

    public void doClose() {
        view.hide();
    }

    public void doShowTranslator() {
        Site.showProgressLoading();
        if (session.isLogged()) {
            view.show();
        } else {
            Site.info(i18n.t("Sign in or register to help with translation"));
        }
        Site.hideProgress();
    }

    public void doTranslation(final String id, final String trKey, final String translation) {
        Site.showProgressSaving();
        i18nService.setTranslation(session.getUserHash(), id, translation, new AsyncCallback<String>() {
            public void onFailure(final Throwable caught) {
                Site.hideProgress();
                Site.error(i18n.t("Server error saving translation"));
            }

            public void onSuccess(final String result) {
                Site.hideProgress();
                i18n.setTranslationAfterSave(trKey, result);
            }
        });
    }

    public I18nLanguageDTO getLanguage() {
        return session.getCurrentLanguage();
    }

    public Object[][] getLanguages() {
        return session.getLanguagesArray();
    }

    public View getView() {
        return view;
    }

    public void hide() {
        view.hide();
    }

    public void init(final I18nTranslatorView view) {
        this.view = view;
    }

}
