package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;

import com.extjs.gxt.ui.client.widget.Component;

public class GxtToolbarSeparatorGui extends AbstractChildGuiItem {

    Component separator;

    @Override
    protected void addStyle(final String style) {
        if (separator != null) {
            separator.addStyleName(style);
        }
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.create(descriptor);
        final GxtToolbarGui toolbar = (GxtToolbarGui) parent;

        final Type type = ((ToolbarSeparatorDescriptor) descriptor).getSeparatorType();
        switch (type) {
        case fill:
            separator = toolbar.addFill();
            break;
        case spacer:
            separator = toolbar.addSpacer();
            break;
        case separator:
            separator = toolbar.addSeparator();
            break;
        default:
            break;
        }
        return toolbar;
    }

    @Override
    protected void setEnabled(final boolean enabled) {
        // do nothing
    }

    @Override
    protected void setIconStyle(final String style) {
        // do nothing
    }

    @Override
    protected void setText(final String text) {
        // do nothing
    }

    @Override
    protected void setToolTipText(final String text) {
        // do nothing
    }

    @Override
    public boolean shouldBeAdded() {
        return false;
    }
}
