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
package org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg;

import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ContentPosition;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ImageInfo;

import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

public class InsertElementAbstractPanel extends DefaultForm {

    protected final ComboBox positionCombo;
    protected final Checkbox wrapText;
    protected final Label intro;
    protected final FieldSet advanced;

    public InsertElementAbstractPanel(final String title, final int height) {
        super(title);
        super.setAutoWidth(true);
        super.setHeight(height);
        super.getFormPanel().setLabelWidth(DEF_FIELD_LABEL_WITH + 20);

        intro = new Label();

        final Store storePositions = new SimpleStore(new String[] { "abbr", "trans" }, ContentPosition.getPositions());
        storePositions.load();

        positionCombo = createCombo(storePositions, Resources.i18n.t("Position"));

        wrapText = new Checkbox(Resources.i18n.t("Wrap text around"));
        wrapText.setChecked(ImageInfo.DEF_WRAP_VALUE);

        positionCombo.addListener(new ComboBoxListenerAdapter() {
            @Override
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                String pos = record.getAsString("abbr");
                // presenter.setPosition(pos);
                setWrapVisible(pos);
            }
        });

        advanced = new FieldSet(Resources.i18n.t("More options"));
        advanced.setCollapsible(true);
        advanced.setCollapsed(true);
        advanced.setAutoHeight(true);
        advanced.setAutoWidth(true);
        advanced.setAutoScroll(false);
        advanced.addClass("kune-Margin-Large-t");
        // advanced.setPaddings(10, 0, 0, 0);

        advanced.add(positionCombo);
        advanced.add(wrapText);
        add(intro);
        add(advanced);
    }

    public String getPosition() {
        return positionCombo.getValueAsString();
    }

    public String getSrc() {
        return null;
    }

    public boolean getWrapText() {
        return wrapText.getValue();
    }

    @Override
    public void insert(final int index, final Component component) {
        super.insert(index, component);
    }

    public void setIntro(final String text) {
        intro.setHtml(text);
    }

    private ComboBox createCombo(final Store storeSizes, final String title) {
        ComboBox cb = new ComboBox();
        cb.setEditable(false);
        cb.setForceSelection(true);
        cb.setMinChars(1);
        cb.setFieldLabel(title);
        cb.setStore(storeSizes);
        cb.setDisplayField("trans");
        cb.setValueField("abbr");
        cb.setMode(ComboBox.LOCAL);
        cb.setTriggerAction(ComboBox.ALL);
        cb.setTypeAhead(true);
        cb.setSelectOnFocus(true);
        cb.setWidth(DEF_FIELD_WIDTH);
        cb.setHideTrigger(false);
        cb.setAllowBlank(false);
        cb.setValidationEvent(false);
        cb.setListWidth(DEF_FIELD_WIDTH + 30);
        cb.setResizable(true);
        return cb;
    }

    private void setWrapVisible(final String position) {
        if (position.equals(ContentPosition.CENTER)) {
            wrapText.setVisible(false);
        } else {
            wrapText.setVisible(true);
        }
    }

}
