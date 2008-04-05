
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;

public class CustomPushButton extends Composite {

    private final PushButton button;

    public CustomPushButton(final String text, final ClickListener listener) {
        button = new PushButton(text, listener);
        RoundedBorderDecorator roundedButton = new RoundedBorderDecorator(button, RoundedBorderDecorator.ALL,
                RoundedBorderDecorator.SIMPLE);
        initWidget(roundedButton);
        roundedButton.setColor("#AAA");
        button.setStyleName("kune-CustomPushButton");
        this.addStyleName("kune-CustomPushButton-space");
    }

    public boolean isEnabled() {
        return button.isEnabled();
    }

    public void setEnabled(final boolean enabled) {
        button.setEnabled(enabled);
    }

    public void setText(final String text) {
        button.setText(text);
    }

}
