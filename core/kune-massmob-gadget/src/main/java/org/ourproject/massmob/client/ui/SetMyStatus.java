package org.ourproject.massmob.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SetMyStatus extends TextBoxEditable {

    interface SetMyStatusUiBinder extends UiBinder<Widget, SetMyStatus> {
    }

    private static SetMyStatusUiBinder uiBinder = GWT.create(SetMyStatusUiBinder.class);

    @UiField
    Label label;
    @UiField
    Button button;

    public SetMyStatus() {
        initWidget(uiBinder.createAndBindUi(this));

    }

    public void setEnabled(final boolean enabled) {
        button.setEnabled(enabled);
        textbox.setEnabled(enabled);
    }

    public void setValue(final String status) {
        textbox.setValue(status);
    }

    @Override
    protected void finishEdit() {
        label.setVisible(true);
        textbox.setVisible(false);
        button.setVisible(false);
        super.finishEdit();
    }

    @UiHandler("button")
    void onClick(final ClickEvent e) {
        Window.alert("Hello!");
    }

    @UiHandler("label")
    void onLabelClick(final ClickEvent e) {
        label.setVisible(false);
        textbox.setVisible(true);
        button.setVisible(true);
        textbox.setFocus(true);
    }
}
