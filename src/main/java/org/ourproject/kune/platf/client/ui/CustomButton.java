
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class CustomButton implements SourcesClickEvents {

    private ClickListenerCollection clickListeners;
    private final Button button;

    public CustomButton(final String text) {
        this(text, null);
    }

    public CustomButton(final String text, final ClickListener listener) {
        button = new Button();
        button.setText(text);
        button.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                fireClickListeners();
            }
        });
        if (listener != null) {
            addClickListener(listener);
        }
    }

    public void addClickListener(final ClickListener listener) {
        if (clickListeners == null) {
            clickListeners = new ClickListenerCollection();
        }
        clickListeners.add(listener);
    }

    public void removeClickListener(final ClickListener listener) {
        if (clickListeners != null) {
            clickListeners.remove(listener);
        }

    }

    public Button getButton() {
        return button;
    }

    public boolean isEnabled() {
        return !button.isDisabled();
    }

    private void fireClickListeners() {
        if (clickListeners != null) {
            clickListeners.fireClick(button);
        }
    }

    public void setEnabled(final boolean enabled) {
        if (enabled) {
            button.enable();
        } else {
            button.disable();
        }
    }

    public void setText(final String text) {
        button.setText(text);
    }

    public void setVisible(final boolean b) {
        button.setVisible(b);
    }

    public void setHeight(final String height) {
        button.setHeight(height);
    }

    public void setWidth(final String width) {
        button.setWidth(width);
    }

    public void addStyleName(final String style) {
        button.addStyleName(style);
    }
}
