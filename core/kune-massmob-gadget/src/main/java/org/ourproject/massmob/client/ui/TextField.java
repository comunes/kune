package org.ourproject.massmob.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class TextField extends TextBoxEditable implements HasText {

    interface TextFieldUiBinder extends UiBinder<Widget, TextField> {
    }

    private static TextFieldUiBinder uiBinder = GWT.create(TextFieldUiBinder.class);

    public TextField() {
        initWidget(uiBinder.createAndBindUi(this));
        textbox.addStyleName("mass-selectable");
    }

    @Override
    public String getText() {
        return textbox.getText();
    }

    @Override
    public void setText(final String text) {
        textbox.setText(text);
    }

    @Override
    protected void finishEdit() {
        super.finishEdit();
    }

}
