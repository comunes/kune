package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public abstract class AbstractMenuItem extends AbstractGuiItem {

    private transient final MenuItem item;

    public AbstractMenuItem(final MenuItemDescriptor descriptor) {
        super();
        item = new MenuItem();
        initWidget(item);
        item.addListener(new BaseItemListenerAdapter() {
            @Override
            public void onClick(final BaseItem item, final EventObject event) {
                if (action != null) {
                    action.actionPerformed(new ActionEvent(item, event.getBrowserEvent()));
                }
            }
        });
        setAction(descriptor.action);
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
        item.setText(text);
    }

    @Override
    protected void setToolTipText(final String text) {
        item.setTitle(text);
    }
}
