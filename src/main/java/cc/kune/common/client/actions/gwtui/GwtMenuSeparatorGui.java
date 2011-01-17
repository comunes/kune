package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.errors.UIException;

public class GwtMenuSeparatorGui extends AbstractGuiItem {

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        final AbstractGwtMenuGui menu = ((AbstractGwtMenuGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
        if (menu == null) {
            throw new UIException("To add a menu separator you need to add the menu before. Item: " + descriptor);
        }
        menu.addSeparator();
        return menu;
    }

    @Override
    protected void setEnabled(final boolean enabled) {
    }

    @Override
    protected void setIconStyle(final String style) {
    }

    @Override
    protected void setText(final String text) {
    }

    @Override
    protected void setToolTipText(final String text) {
    }

    @Override
    public boolean shouldBeAdded() {
        return false;
    }
}
