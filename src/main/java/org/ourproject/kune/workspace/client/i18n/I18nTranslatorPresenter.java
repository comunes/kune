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

package org.ourproject.kune.workspace.client.i18n;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.DoTranslationActionParams;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

public class I18nTranslatorPresenter implements I18nTranslatorComponent {
    private I18nTranslatorView view;
    private final Session session;

    public I18nTranslatorPresenter(final Session session) {
        this.session = session;
    }

    public void init(final I18nTranslatorView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public Object[][] getLanguages() {
        return session.getLanguagesArray();
    }

    public void show() {
        view.show();
    }

    public void hide() {
        view.hide();
    }

    public void doClose() {
        view.hide();
        DefaultDispatcher.getInstance().fire(PlatformEvents.DETACH_FROM_EXT_POINT,
                new UIExtensionElement(UIExtensionPoint.CONTENT_BOTTOM_ICONBAR, view));
    }

    public void doTranslation(final String id, final String trKey, final String translation) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.DO_TRANSLATION,
                new DoTranslationActionParams(id, trKey, translation));
    }

    public I18nLanguageDTO getLanguage() {
        return session.getCurrentLanguage();
    }

    public void doShowTranslator() {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.SHOW_TRANSLATOR, null);
    }

    public void attachIconToBottomBar(final View view) {
        DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                new UIExtensionElement(UIExtensionPoint.CONTENT_BOTTOM_ICONBAR, view));
    }

}
