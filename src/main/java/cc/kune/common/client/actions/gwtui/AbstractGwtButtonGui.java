package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.ToggleButton;

public abstract class AbstractGwtButtonGui extends AbstractChildGuiItem {

    private ButtonBase button;
    private final boolean enableTongle;
    private IconLabel iconLabel;

    public AbstractGwtButtonGui() {
        this(null, false);
    }

    public AbstractGwtButtonGui(final ButtonDescriptor buttonDescriptor) {
        this(buttonDescriptor, false);
    }

    public AbstractGwtButtonGui(final ButtonDescriptor buttonDescriptor, final boolean enableTongle) {
        super(buttonDescriptor);
        this.enableTongle = enableTongle;
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        iconLabel = new IconLabel("");
        if (enableTongle) {
            button = new ToggleButton();
        } else {
            button = new Button();
        }
        button.setStylePrimaryName("oc-button");
        layout();
        // button.setEnableToggle(enableTongle);
        final String id = descriptor.getId();
        if (id != null) {
            button.ensureDebugId(id);
        }
        initWidget(button);
        if (descriptor.isChild()) {
            child = button;
        }
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                descriptor.fire(new ActionEvent(button, Event.as(event.getNativeEvent())));
            }
        });
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    private void layout() {
        button.setHTML(iconLabel.getElement().getInnerHTML());
    }

    @Override
    public void setEnabled(final boolean enabled) {
        button.setEnabled(enabled);
    }

    @Override
    protected void setIconStyle(final String style) {
        iconLabel.setIcon(style);
        layout();
    }

    public void setPressed(final boolean pressed) {
        final ToggleButton toggleButton = (ToggleButton) button;

        if (toggleButton.isDown() != pressed) {
            toggleButton.setDown(pressed);
        }
    }

    @Override
    public void setText(final String text) {
        iconLabel.setText(text);
        layout();
    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (tooltip != null && !tooltip.isEmpty()) {
            final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
            if (key == null) {
                button.setTitle(tooltip);
            } else {
                button.setTitle(tooltip + key.toString());
            }
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (button.isAttached()) {
            super.setVisible(visible);
        }
    }

    @Override
    public boolean shouldBeAdded() {
        return true;
    }

}
