package org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg;

import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ImageInfo;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialogView;

import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;

public class InsertImageAbstractPanel extends DefaultForm implements InsertImageAbstractView {

    private Object[][] sizesObjs;
    private Object[][] positionObjs;
    private final ComboBox sizeCombo;
    private final ComboBox positionCombo;
    protected final Checkbox wrapText;
    protected final Checkbox clickOriginal;
    private final Label intro;
    private final InsertImageAbstractPresenter presenter;

    public InsertImageAbstractPanel(final String title, final InsertImageAbstractPresenter presenter) {
        super(title);
        this.presenter = presenter;
        super.setAutoWidth(true);
        super.setHeight(InsertImageDialogView.HEIGHT);
        super.getFormPanel().setLabelWidth(DEF_FIELD_LABEL_WITH + 20);

        intro = new Label();

        final Store storeSizes = new SimpleStore(new String[] { "abbr", "trans", "size" }, getSizes());
        storeSizes.load();

        final Store storePositions = new SimpleStore(new String[] { "abbr", "trans" }, getPositions());
        storePositions.load();

        sizeCombo = createCombo(storeSizes, Resources.i18n.t("Size"));
        positionCombo = createCombo(storePositions, Resources.i18n.t("Position"));

        wrapText = new Checkbox(Resources.i18n.t("Wrap text around image"));
        wrapText.setChecked(ImageInfo.DEF_WRAP_VALUE);

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
                String pos = record.getAsString("abbr");
                presenter.setPosition(pos);
                setWrapVisible(pos);
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

        FieldSet advanced = new FieldSet(Resources.i18n.t("More options"));
        advanced.setCollapsible(true);
        advanced.setCollapsed(true);
        advanced.setAutoHeight(true);
        advanced.setAutoWidth(true);
        advanced.setAutoScroll(false);
        advanced.addClass("kune-Margin-Large-t");
        // advanced.setPaddings(10, 0, 0, 0);

        advanced.add(sizeCombo);
        advanced.add(positionCombo);
        advanced.add(wrapText);
        advanced.add(clickOriginal);
        add(intro);
        add(advanced);
    }

    public boolean getClickOriginal() {
        return clickOriginal.getValue();
    }

    public String getPosition() {
        return positionCombo.getValueAsString();
    }

    public String getSize() {
        return sizeCombo.getValueAsString();
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

    @Override
    public void reset() {
        super.reset();
        ImageInfo linkImage = presenter.getImageInfo();
        updateValues(linkImage);
    }

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

    private Object[][] getPositions() {
        if (positionObjs == null) {
            String[][] values = ImageInfo.positions;
            positionObjs = new Object[values.length][1];
            int i = 0;
            for (String[] position : values) {
                final Object[] obj = new Object[] { position[0], Resources.i18n.t(position[0]) };
                positionObjs[i++] = obj;
            }
        }
        return positionObjs;
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
        if (position.equals(ImageInfo.POSITION_CENTER)) {
            wrapText.setVisible(false);
        } else {
            wrapText.setVisible(true);
        }
    }

}
