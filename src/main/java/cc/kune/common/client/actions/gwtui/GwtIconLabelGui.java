package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;

public class GwtIconLabelGui extends AbstractGuiItem {
    private IconLabel iconLabel;

    public GwtIconLabelGui() {
    }

    public GwtIconLabelGui(final IconLabelDescriptor iconLabelDescriptor) {
        super(iconLabelDescriptor);
    }

    @Override
    protected void addStyle(final String style) {
        iconLabel.addStyleName(style);
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        iconLabel = new IconLabel("");
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        final String id = descriptor.getId();
        if (id != null) {
            iconLabel.ensureDebugId(id);
        }
        initWidget(iconLabel);
        iconLabel.getFocus().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                final AbstractAction action = descriptor.getAction();
                if (action != null) {
                    action.actionPerformed(new ActionEvent(iconLabel, Event.as(event.getNativeEvent())));
                }
            }
        });
        configureItemFromProperties();
        return this;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        // Not implemented
    }

    @Override
    protected void setIconStyle(final String style) {
        iconLabel.setIcon(style);
    }

    @Override
    public void setText(final String text) {
        iconLabel.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
        if (key == null) {
            iconLabel.setTitle(tooltip);
        } else {
            iconLabel.setTitle(tooltip + key.toString());
        }
    }

    @Override
    public boolean shouldBeAdded() {
        return true;
    }

}
