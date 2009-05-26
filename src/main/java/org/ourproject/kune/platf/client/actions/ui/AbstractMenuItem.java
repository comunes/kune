package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public abstract class AbstractMenuItem extends AbstractGuiItem {

    private transient Item item;

    public AbstractMenuItem(final MenuItemDescriptor descriptor) {
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
            item.setIconCls(RTEImgResources.SUFFIX + imageResource.getName());
        }
    }

    @Override
    protected void setText(final String text) {
        if (text != null) {
            item.setText(text);
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
