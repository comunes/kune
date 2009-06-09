package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.ui.img.ImgConstants;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.gwtext.client.widgets.menu.MenuItem;

public class SubMenuGui extends AbstractMenuGui {

    private final MenuItem item;

    public SubMenuGui(final GuiActionDescrip descriptor) {
        super();
        item = new MenuItem();
        item.setMenu(menu);
        setAction(descriptor.action);
        // initWidget(item);
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
            item.setIconCls(ImgConstants.CSS_SUFFIX + imageResource.getName());
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
