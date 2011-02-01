package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;

import com.extjs.gxt.ui.client.Style.ButtonScale;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.google.gwt.user.client.Event;

public abstract class AbstractGxtButtonGui extends AbstractChildGuiItem {

    private Button button;

    public AbstractGxtButtonGui() {
        super();
    }

    public AbstractGxtButtonGui(final ButtonDescriptor buttonDescriptor) {
        super(buttonDescriptor);
    }

    @Override
    protected void addStyle(final String style) {
        button.addStyleName(style);
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        if (descriptor instanceof PushButtonDescriptor) {
            button = new ToggleButton();
        } else {
            button = new Button();
        }
        button.setAutoWidth(false);
        button.setAutoHeight(false);
        button.setBorders(false);
        final String id = descriptor.getId();
        if (id != null) {
            button.ensureDebugId(id);
        }
        button.addSelectionListener(new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(final ButtonEvent event) {
                descriptor.fire(new ActionEvent(button, Event.as(event.getEvent())));
            }
        });
        if (!descriptor.isChild()) {
            // If button is inside a toolbar don't init...
            initWidget(button);
        } else {
            if (descriptor.isChild()) {
                child = button;
            }
        }
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    @Override
    protected void setEnabled(final boolean enabled) {
        button.setEnabled(enabled);
    }

    @Override
    protected void setIconStyle(final String style) {
        button.setIconStyle(style);
        button.setScale(ButtonScale.SMALL);
    }

    public void setPressed(final boolean pressed) {
        final ToggleButton toggleButton = (ToggleButton) button;

        if (toggleButton.isPressed() != pressed) {
            toggleButton.toggle(pressed);
        }
    }

    @Override
    protected void setText(final String text) {
        button.setText(text);
    }

    @Override
    protected void setToolTipText(final String tooltip) {
        if (tooltip != null && !tooltip.isEmpty()) {
            final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
            if (key == null) {
                button.setToolTip(new GxtDefTooltip(tooltip));
            } else {
                button.setToolTip(new GxtDefTooltip(tooltip + key.toString()));
            }
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (button.isRendered()) {
            // ??
        }
        button.setVisible(visible);

    }

    @Override
    public boolean shouldBeAdded() {
        return !descriptor.isChild();
    }
}
