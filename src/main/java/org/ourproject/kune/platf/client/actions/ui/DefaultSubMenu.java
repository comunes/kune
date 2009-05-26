package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.gwtext.client.widgets.menu.MenuItem;

public class DefaultSubMenu extends AbstractMenu {

    private transient final MenuItem item;

    public DefaultSubMenu(final AbstractUIActionDescriptor descriptor) {
        super();
        item = new MenuItem();
        item.setMenu(menu);
        initWidget(menu);
        setAction(descriptor.action);
    }

    public MenuItem getMenuItem() {
        return item;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            item.enable();
        } else {
            item.disable();
        }
    }

    @Override
    public void setIcon(final ImageResource imageResource) {
        if (imageResource != null) {
            // FIXME
            item.setIconCls(RTEImgResources.SUFFIX + imageResource.getName());
        }
    }

    @Override
    public void setText(final String text) {
        item.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        item.setTitle(tooltip);
    }
}
