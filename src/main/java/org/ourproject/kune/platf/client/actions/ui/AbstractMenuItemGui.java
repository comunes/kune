package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.ui.img.ImgConstants;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public abstract class AbstractMenuItemGui extends AbstractGuiItem {

    private transient Item item;

    public AbstractMenuItemGui(final MenuItemDescriptor descriptor) {
        super();
        if (descriptor instanceof MenuRadioItemDescriptor) {
            final CheckItem checkItem = createCheckItem(descriptor);
            checkItem.setGroup(((MenuRadioItemDescriptor) descriptor).getGroup());
            item = checkItem;
        } else if (descriptor instanceof MenuCheckItemDescriptor) {
            final CheckItem checkItem = createCheckItem(descriptor);
            item = checkItem;
        } else {
            item = new Item();
        }
        // initWidget(item);
        final BaseItemListenerAdapter clickListener = new BaseItemListenerAdapter() {
            @Override
            public void onClick(final BaseItem item, final EventObject event) {
                if (action != null) {
                    action.actionPerformed(new ActionEvent(item, event.getBrowserEvent()));
                }
            }
        };
        item.addListener(clickListener);
        setAction(descriptor.action);
    }

    @Override
    public void setVisible(final boolean visible) {
        item.setVisible(visible);
    }

    @Override
    protected Widget getWidget() {
        return item;
    }

    @Override
    protected void setEnabled(final boolean enabled) {
        if (enabled) {
            item.enable();
        } else {
            item.disable();
        }
    }

    @Override
    protected void setIcon(final ImageResource imageResource) {
        if (imageResource != null) {
            // FIXME
            item.setIconCls(ImgConstants.CSS_SUFFIX + imageResource.getName());
        }
    }

    @Override
    protected void setText(final String text) {
        if (text != null) {
            final KeyStroke key = (KeyStroke) action.getValue(Action.ACCELERATOR_KEY);
            if (key == null) {
                item.setText(text);
            } else {
                final FlowPanel fpanel = new FlowPanel();
                fpanel.setWidth("100%");
                fpanel.add(new Label(text));
                final Label keyLabel = new Label(key.toString());
                keyLabel.addStyleName("kune-floatright");
                fpanel.add(keyLabel);
                item.setText(DOM.getInnerHTML(fpanel.getElement()));
            }
        }
    }

    @Override
    protected void setToolTipText(final String text) {
        if (text != null) {
            item.setTitle(text);
        }
    }

    private CheckItem createCheckItem(final MenuItemDescriptor descriptor) {
        final CheckItem checkItem = new CheckItem();
        checkItem.setChecked(((MenuCheckItemDescriptor) descriptor).isChecked());
        return checkItem;
    }
}
