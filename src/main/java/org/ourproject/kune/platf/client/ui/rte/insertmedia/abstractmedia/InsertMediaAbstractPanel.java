/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertElementAbstractPanel;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialogView;

import cc.kune.core.client.ui.utils.ContentPosition;

import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;

public class InsertMediaAbstractPanel extends InsertElementAbstractPanel implements InsertMediaAbstractView {

    protected TextField hrefField;

    public InsertMediaAbstractPanel(final String title, final InsertMediaAbstractPresenter presenter) {
        super(title, InsertMediaDialogView.HEIGHT);

        hrefField = new TextField();
        hrefField.setTabIndex(1);
        hrefField.setLabel(Resources.i18n.t("Link"));
        hrefField.setWidth(DEF_FIELD_WIDTH);
        hrefField.setAllowBlank(false);
        hrefField.setMinLength(3);
        hrefField.setMaxLength(250);
        hrefField.setValidationEvent(false);

        super.addListener(new FormPanelListenerAdapter() {
            @Override
            public void onActivate(final Panel panel) {
                presenter.onActivate();
            }
        });

        insertImpl(1, hrefField);
        defValues();
    }

    @Override
    public String getSrc() {
        return hrefField.getRawValue();
    }

    @Override
    public void insert(final int index, final Component component) {
        insertImpl(index, component);
    }

    @Override
    public void reset() {
        defValues();
        super.reset();
    }

    @Override
    public void setIntro(final String text) {
        intro.setHtml(text + "<br/>");
    }

    public String setPosition(final String embedElement) {
        return ContentPosition.setPosition(embedElement, getWrapText(), getPosition());
    }

    private void defValues() {
        wrapText.setVisible(true);
        wrapText.setChecked(true);
        positionCombo.setValue(ContentPosition.RIGHT);
    }

    private void insertImpl(final int index, final Component component) {
        super.insert(index, component);
    }
}
