package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.errors.UIException;

public class GwtToolbarSeparatorGui extends AbstractGuiItem {

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        final GwtToolbarGui toolbar = ((GwtToolbarGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
        if (toolbar == null) {
            throw new UIException("To add a toolbar separator you need to add the toolbar before. Item: " + descriptor);
        }
        final Type type = ((ToolbarSeparatorDescriptor) descriptor).getSeparatorType();
        switch (type) {
        case fill:
            toolbar.addFill();
            break;
        case spacer:
            toolbar.addSpacer();
            break;
        case separator:
            toolbar.addSeparator();
            break;
        default:
            break;
        }
        return toolbar;
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
