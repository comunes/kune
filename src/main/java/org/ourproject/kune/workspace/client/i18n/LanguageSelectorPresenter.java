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

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.state.Session;

public class LanguageSelectorPresenter implements LanguageSelectorComponent {

    private LanguageSelectorView view;
    private final Session session;

    public LanguageSelectorPresenter(final Session session) {
        this.session = session;

    }

    public void init(final LanguageSelectorView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public String getSelectedLanguage() {
        return view.getLanguage();
    }

    public Object[][] getLanguages() {
        return session.getLanguagesArray();
    }

    public void setLanguage(final I18nLanguageDTO language) {
        view.setLanguage(language.getCode());
    }
}
