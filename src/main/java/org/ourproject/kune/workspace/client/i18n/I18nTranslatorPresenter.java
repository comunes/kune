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

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetChild;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetId;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.sitebar.Site;

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
    }

    public void attachIconToBottomBar(final View view) {
	DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXTENSIBLE_WIDGET,
		new ExtensibleWidgetChild(ExtensibleWidgetId.CONTENT_BOTTOM_ICONBAR, view));
    }

    public void doClose() {
	view.hide();
	DefaultDispatcher.getInstance().fire(PlatformEvents.DETACH_FROM_EXTENSIBLE_WIDGET,
		new ExtensibleWidgetChild(ExtensibleWidgetId.CONTENT_BOTTOM_ICONBAR, view));
    }

    public void doShowTranslator() {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.SHOW_TRANSLATOR, null);
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

    public void show() {
	view.show();
    }

}
