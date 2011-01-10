package org.ourproject.kune.platf.client.ui.rte.inserttable;

import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.palette.SimplePalette;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class InsertTableDialogPanel extends BasicDialog implements InsertTableDialogView {

    private static final String INSERT_LINK_ID = "itdp-diag";
    private static final String R_FIELD = "itdp-wfield";
    private static final String C_FIELD = "itdp-cfield";
    private final TextField rowsField;
    private final TextField colsField;
    private final Checkbox sameWidth;
    private final DefaultForm form;
    private final TextField backColorField;
    private final TextField borderField;
    private final I18nTranslationService i18n;
    private final TextField borderColorField;

    public InsertTableDialogPanel(final InsertTableDialogPresenter presenter, I18nTranslationService i18n,
            final Provider<SimplePalette> simplePalette, RTEImgResources imgResources) {
        super(INSERT_LINK_ID, i18n.t("Insert Table"), true, false, 320, 260);
        // super.setIconCls(RTEImgResources.SUFFIX +
        // imgResources.insertspreadsheet().getName());
        this.i18n = i18n;
        form = new DefaultForm();
        form.setAutoWidth(true);
        form.setAutoHeight(true);

        rowsField = new TextField();
        rowsField.setTabIndex(4);
        rowsField.setFieldLabel(i18n.t("Rows"));
        rowsField.setWidth(DefaultForm.DEF_XSMALL_FIELD_WIDTH);
        rowsField.setName(R_FIELD);
        rowsField.setRegex(TextUtils.NUM_REGEXP);
        rowsField.setRegexText(i18n.t("This must be a number"));
        rowsField.setAllowBlank(false);
        rowsField.setValidationEvent(false);
        rowsField.setId(R_FIELD);
        rowsField.setValue("2");

        colsField = new TextField();
        colsField.setTabIndex(5);
        colsField.setFieldLabel(i18n.t("Columns"));
        colsField.setName(C_FIELD);
        colsField.setWidth(DefaultForm.DEF_XSMALL_FIELD_WIDTH);
        colsField.setRegex(TextUtils.NUM_REGEXP);
        colsField.setRegexText(i18n.t("This must be a number"));
        colsField.setAllowBlank(false);
        colsField.setValidationEvent(false);
        colsField.setId(C_FIELD);
        colsField.setValue("2");

        borderField = new TextField();
        borderField.setTabIndex(5);
        borderField.setFieldLabel(i18n.t("Border Size"));
        borderField.setWidth(DefaultForm.DEF_XSMALL_FIELD_WIDTH);
        borderField.setRegex(TextUtils.NUM_REGEXP);
        borderField.setRegexText(i18n.t("This must be a number"));
        borderField.setAllowBlank(false);
        borderField.setValidationEvent(false);
        borderField.setValue("0");

        sameWidth = new Checkbox(i18n.t("Columns of equal width"));
        sameWidth.setChecked(true);

        backColorField = createColorField(i18n.t("Background color"));
        borderColorField = createColorField(i18n.t("Border color"));

        FieldListenerAdapter colorListener = new FieldListenerAdapter() {
            @Override
            public void onFocus(final Field field) {
                super.onFocus(field);
                simplePalette.get().show(field.getAbsoluteLeft() + 10, field.getAbsoluteTop() + 10,
                        new Listener<String>() {
                            public void onEvent(String color) {
                                setBackgroupFieldColor(field, color);
                                field.setValue("#" + color);
                            }
                        });
            }
        };
        backColorField.addListener(colorListener);
        borderColorField.addListener(colorListener);

        form.add(rowsField);
        form.add(colsField);
        form.add(sameWidth);
        form.add(backColorField);
        form.add(borderField);
        form.add(borderColorField);

        Button insert = new Button(i18n.t("Insert"));
        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                if (form.getFormPanel().getForm().isValid()) {
                    presenter.onInsert(rowsField.getValueAsString(), colsField.getValueAsString(),
                            borderField.getValueAsString());
                }
            }
        });
        Button cancel = new Button(i18n.t("Cancel"));
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onCancel();
            }
        });

        super.add(form.getFormPanel());
        super.addButton(insert);
        super.addButton(cancel);
    }

    public String generateTable(int rows, int cols, boolean sameColWidth, String backgroundColor, int border,
            String borderColor) {
        Grid table = new Grid(rows, cols);
        table.setWidth("100%");
        if (backgroundColor != null) {
            DOM.setElementAttribute(table.getElement(), "bgcolor", backgroundColor);
        }

        if (borderColor != null) {
            DOM.setElementAttribute(table.getElement(), "bordercolor", borderColor);
        }

        table.setBorderWidth(border);
        table.setCellPadding(3);
        table.setCellSpacing(0);
        if (sameColWidth) {
            int colWith = 100 / cols;
            for (int i = 0; i < cols; i++) {
                table.getColumnFormatter().setWidth(i, colWith + "%");
            }
        }
        return table.getElement().getString();
    }

    public String getBackgroundColor() {
        return backColorField.getValueAsString();
    }

    public String getBorderColor() {
        return borderColorField.getValueAsString();
    }

    public boolean getSameColWidth() {
        return sameWidth.getValue();
    }

    public void reset() {
        form.getFormPanel().getForm().reset();
        rowsField.setValue("2");
        colsField.setValue("2");
        borderField.setValue("0");
        sameWidth.setChecked(true);
        setBackgroupFieldColor(backColorField, "FFFFFF");
        setBackgroupFieldColor(borderColorField, "FFFFFF");
    }

    private TextField createColorField(String text) {
        TextField field = new TextField(text);
        field.setWidth(DefaultForm.DEF_XSMALL_FIELD_WIDTH + 15);
        field.setTitle(i18n.t("Click to change"));
        return field;
    }

    private void setBackgroupFieldColor(Field field, String color) {
        field.setStyle("background-color: #" + color + ";background-image:none;");
    }
}
