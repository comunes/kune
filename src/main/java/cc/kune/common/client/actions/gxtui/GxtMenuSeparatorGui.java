package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

import com.google.gwt.user.client.ui.UIObject;

public class GxtMenuSeparatorGui extends AbstractChildGuiItem implements ParentWidget {

    @Override
    public void add(final UIObject uiObject) {
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.create(descriptor);
        final GxtMenuGui menu = (GxtMenuGui) parent;
        menu.addSeparator();
        return menu;
    }

    @Override
    public void insert(final int position, final UIObject widget) {
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
