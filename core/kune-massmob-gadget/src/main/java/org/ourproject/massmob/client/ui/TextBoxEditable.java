package org.ourproject.massmob.client.ui;

import org.ourproject.massmob.client.ui.EditEvent.EditHandler;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;

public abstract class TextBoxEditable extends Composite {
    @UiField
    TextBox textbox;

    public TextBoxEditable() {
    }

    public HandlerRegistration addEditHandler(final EditHandler handler) {
        return addHandler(handler, EditEvent.getType());
    }

    protected void finishEdit() {
        fireEvent(new EditEvent(textbox.getValue()));
    }

    @UiHandler("textbox")
    void handleKeys(final KeyDownEvent event) {
        if (event.getNativeKeyCode() == 13) {
            finishEdit();
        }
    }

    @UiHandler("textbox")
    void onBlur(final BlurEvent event) {
        finishEdit();
    }
}
