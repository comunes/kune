package org.ourproject.kune.platf.client.ui.dialogs;

import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;

public class DefaultForm {

    protected static final int DEF_FIELD_WIDTH = 200;
    private static final int DEF_FIELD_LABEL_WITH = 75;

    private final FormPanel form;

    public DefaultForm() {
	form = new FormPanel();
	form.setPaddings(10);
	form.setBorder(false);
	form.setLabelWidth(DEF_FIELD_LABEL_WITH);
	form.setLabelAlign(Position.RIGHT);
	form.setButtonAlign(Position.RIGHT);
	form.setHeader(false);
    }

    public void add(final Field field) {
	form.add(field);
    }

    public void addStyleName(final String cls) {
	form.addStyleName(cls);
    }

    public FormPanel getForm() {
	return form;
    }

    public boolean isValid() {
	return form.getForm().isValid();
    }

    public void removeStyleName(final String cls) {
	form.removeStyleName(cls);
    }

    public void reset() {
	form.getForm().reset();
    }

    public void setAutoHeight(final boolean autoHeight) {
	form.setAutoHeight(autoHeight);
    }

    public void setAutoWidth(final boolean autoWidth) {
	form.setAutoWidth(autoWidth);
    }

    public void validate() {
	final Field[] fields = form.getFields();
	for (int i = 0; i < fields.length; i++) {
	    fields[i].validate();
	}
    }

}
