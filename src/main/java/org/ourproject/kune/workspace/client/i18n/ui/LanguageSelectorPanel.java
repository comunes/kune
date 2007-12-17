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

package org.ourproject.kune.workspace.client.i18n.ui;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPresenter;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorView;

import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

public class LanguageSelectorPanel extends Form implements LanguageSelectorView {

    private static final String LANG_FIELD = "lang";

    private final Object[][] languages;

    private ComboBox langCombo;

    public LanguageSelectorPanel(final LanguageSelectorPresenter presenter, final Object[][] languages,
            final String fieldTitle) {
        super();
        this.languages = languages;
        createForm(fieldTitle);
        super.add(langCombo);
        super.end();
        super.render();
    }

    public void selectLanguage(final String languageCode) {
        langCombo.selectByValue(languageCode, true);
    }

    public String getLanguage() {
        return langCombo.getValueAsString();
    }

    public void reset() {
        langCombo.reset();
    }

    public void addChangeListener(final ComboBoxListenerAdapter listener) {
        langCombo.addComboBoxListener(listener);
    }

    private void createForm(final String fieldTitle) {
        final Store langStore = new SimpleStore(new String[] { "abbr", "language" }, getLanguages());
        langStore.load();

        langCombo = new ComboBox(new ComboBoxConfig() {
            {
                setName(LANG_FIELD);
                setMinChars(1);
                setFieldLabel(fieldTitle);
                setStore(langStore);
                setDisplayField("language");
                setMode(ComboBox.LOCAL);
                setTriggerAction(ComboBox.ALL);
                setEmptyText(Kune.I18N.t("Enter language"));
                setLoadingText(Kune.I18N.t("Searching..."));
                setTypeAhead(true);
                setSelectOnFocus(false);
                setWidth(187);
                setMsgTarget("side");
                setValueField("abbr");
                setPageSize(7);
            }
        });
    }

    private Object[][] getLanguages() {
        return languages;
    }
}
