
package org.ourproject.kune.workspace.client.ui.ctx.items;

import java.util.HashMap;

import org.ourproject.kune.platf.client.ui.EditableClickListener;
import org.ourproject.kune.platf.client.ui.EditableIconLabel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.VerticalPanel;

class ItemsPanel extends VerticalPanel {
    private final HashMap<String, AbstractImagePrototype> fileIcons;
    private final ContextItemsPresenter presenter;

    public ItemsPanel(final ContextItemsPresenter presenter) {
        this.presenter = presenter;
        addStyleName("kune-NavigationBar");
        addStyleName("Items");
        fileIcons = new HashMap<String, AbstractImagePrototype>();
    }

    public void add(final String name, final String type, final String token, final boolean editable) {
        // IconHyperlink item = new IconHyperlink((AbstractImagePrototype)
        // fileIcons.get(type), name, event);
        EditableIconLabel item = new EditableIconLabel(fileIcons.get(type), name, token, true,
                new EditableClickListener() {
                    public void onEdited(String text) {
                        presenter.onTitleRename(text, token);
                    }
                });
        item.setEditable(editable);
        item.addStyleName("Items");
        item.addStyleName("kune-floatleft");
        add(item);
    }

    public void registerType(final String typeName, final AbstractImagePrototype image) {
        fileIcons.put(typeName, image);
    }
}
