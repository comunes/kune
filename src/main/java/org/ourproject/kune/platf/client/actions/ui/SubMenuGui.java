package org.ourproject.kune.platf.client.actions.ui;

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
    public void setIconStyle(final String style) {
        item.setIconCls(style);
    }

    @Override
    public void setIconUrl(final String imageUrl) {
        item.setIcon(imageUrl);
    }

    @Override
    public void setText(final String text) {
        item.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        item.setTitle(tooltip);
    }

    @Override
    public void setVisible(final boolean visible) {
        item.setVisible(visible);
    }
}
