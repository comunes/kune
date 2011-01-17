package cc.kune.common.client.actions.ui.bind;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.errors.UIException;

import com.google.gwt.user.client.ui.UIObject;

public abstract class GuiChildBinding extends AbstractGuiBinding {

    protected UIObject child;
    protected ParentWidget parent;

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        final int position = descriptor.getPosition();
        if (descriptor.isChild()) {
            // A menu item is a child, a toolbar separator, also. A button can
            // be a child of a toolbar or not
            parent = ((ParentWidget) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
            if (parent == null) {
                throw new UIException("To add a item you need to add its parent before. Item: " + descriptor);
            }
            if (child != null) {
                // Sometimes (menu/toolbar separators), there is no Widget to
                // add/insert
                if (position == AbstractGuiActionDescrip.NO_POSITION) {
                    parent.add(child);
                } else {
                    parent.insert(position, child);
                }
            }
        }
        return super.create(descriptor);
    }
}
