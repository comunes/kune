package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class GxtSubMenuGui extends AbstractGxtMenuGui {

    private MenuItem item;

    public GxtSubMenuGui() {
        super();
    }

    public GxtSubMenuGui(final AbstractGuiActionDescrip descriptor) {
        super(descriptor);
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.create(descriptor);
        item = new MenuItem();
        item.setSubMenu(menu);
        final AbstractGxtMenuGui parentMenu = ((AbstractGxtMenuGui) descriptor.getParent().getValue(
                ParentWidget.PARENT_UI));
        final int position = descriptor.getPosition();
        if (position == AbstractGuiActionDescrip.NO_POSITION) {
            parentMenu.add(item);
        } else {
            parentMenu.insert(position, item);
        }
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        configureItemFromProperties();
        return this;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        item.setVisible(enabled);
    }

    @Override
    public void setIconStyle(final String style) {
        item.setIconStyle(style);
    }

    @Override
    public void setText(final String text) {
        item.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        item.setToolTip(new GxtDefTooltip(tooltip));
    }

    @Override
    public void setVisible(final boolean visible) {
        item.setVisible(visible);
    }

}
