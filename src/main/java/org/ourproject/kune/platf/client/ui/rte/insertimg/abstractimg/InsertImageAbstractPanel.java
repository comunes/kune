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
import org.ourproject.kune.platf.client.ui.rte.insertimg.ContentPosition;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ImageInfo;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialogView;

import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;

public class InsertImageAbstractPanel extends InsertElementAbstractPanel implements InsertImageAbstractView {

    private Object[][] sizesObjs;
    private final ComboBox sizeCombo;
    protected final Checkbox clickOriginal;
    private final InsertImageAbstractPresenter presenter;

    public InsertImageAbstractPanel(final String title, final InsertImageAbstractPresenter presenter) {
        super(title, InsertImageDialogView.HEIGHT);
        this.presenter = presenter;

        final Store storeSizes = new SimpleStore(new String[] { "abbr", "trans", "size" }, getSizes());
        storeSizes.load();

        sizeCombo = createCombo(storeSizes, Resources.i18n.t("Size"));

        clickOriginal = new Checkbox(Resources.i18n.t("Clicking this image links to the original image file"));
        clickOriginal.setChecked(ImageInfo.DEF_CLICK_ORIGINAL_VALUE);
        clickOriginal.setWidth(DEF_FIELD_WIDTH);

        sizeCombo.addListener(new ComboBoxListenerAdapter() {
            @Override
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                String size = record.getAsString("abbr");
                presenter.setSize(size);
            }
        });

        positionCombo.addListener(new ComboBoxListenerAdapter() {
            @Override
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                super.onSelect(comboBox, record, index);
                String pos = record.getAsString("abbr");
                presenter.setPosition(pos);
            }
        });

        super.addListener(new FormPanelListenerAdapter() {
            @Override
            public void onActivate(final Panel panel) {
                ImageInfo linkImage = presenter.getImageInfo();
                updateValues(linkImage);
                presenter.onActivate();
            }
        });

        clickOriginal.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(final Checkbox field, final boolean checked) {
                presenter.onClickOriginalChecked(checked);
            }
        });

        wrapText.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(final Checkbox field, final boolean checked) {
                presenter.onWrapTextChecked(checked);
            }
        });

        advanced.add(sizeCombo);
        advanced.add(clickOriginal);
    }

    public boolean getClickOriginal() {
        return clickOriginal.getValue();
    }

    @Override
    public String getPosition() {
        return positionCombo.getValueAsString();
    }

    public String getSize() {
        return sizeCombo.getValueAsString();
    }

    @Override
    public String getSrc() {
        return null;
    }

    @Override
    public boolean getWrapText() {
        return wrapText.getValue();
    }

    @Override
    public void insert(final int index, final Component component) {
        super.insert(index, component);
    }

    @Override
    public void reset() {
        super.reset();
        ImageInfo linkImage = presenter.getImageInfo();
        updateValues(linkImage);
    }

    @Override
    public void setIntro(final String text) {
        intro.setHtml(text);
    }

    protected void updateValues(final ImageInfo imageInfo) {
        sizeCombo.setValue(imageInfo.getSize());
        String position = imageInfo.getPosition();
        positionCombo.setValue(position);
        wrapText.setChecked(imageInfo.isWraptext());
        clickOriginal.setChecked(imageInfo.getClickOriginal());
        setWrapVisible(position);
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

    private Object[][] getSizes() {
        if (sizesObjs == null) {
            String[][] values = ImageInfo.sizes;
            sizesObjs = new Object[values.length][1];
            int i = 0;
            for (String[] size : values) {
                final Object[] obj = new Object[] { size[0], Resources.i18n.t(size[1], size[2]) };
                sizesObjs[i++] = obj;
            }
        }
        return sizesObjs;
    }

    private void setWrapVisible(final String position) {
        if (position.equals(ContentPosition.CENTER)) {
            wrapText.setVisible(false);
        } else {
            wrapText.setVisible(true);
        }
    }

}
