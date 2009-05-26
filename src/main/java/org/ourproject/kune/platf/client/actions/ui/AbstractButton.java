package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public abstract class AbstractButton extends AbstractGuiItem {

    private transient final Button button;

    public AbstractButton(final ButtonDescriptor buttonDescriptor) {
        this(buttonDescriptor, false);
    }

    public AbstractButton(final ButtonDescriptor buttonDescriptor, final boolean enableTongle) {
        super();
        button = new Button();
        button.setEnableToggle(enableTongle);
        initWidget(button);
        button.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject event) {
                if (action != null) {
                    action.actionPerformed(new ActionEvent(button, event.getBrowserEvent()));
                }
            }
        });
        setAction(buttonDescriptor.action);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            button.enable();
        } else {
            button.disable();
        }
    }

    @Override
    public void setIcon(final ImageResource imageResource) {
        if (imageResource != null) {
            // FIXME
            button.setIconCls(RTEImgResources.SUFFIX + imageResource.getName());
        }
    }

    public void setPressed(final boolean pressed) {
        if (button.isPressed() != pressed) {
            button.toggle();
        }
    }

    @Override
    public void setText(final String text) {
        button.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        button.setTooltip(tooltip);
    }

}
