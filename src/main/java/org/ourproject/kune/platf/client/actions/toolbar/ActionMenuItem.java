package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuRadioDescriptor;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.CheckItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class ActionMenuItem<T> {

    Item item;

    public ActionMenuItem(final ActionItem<T> actionItem, final String id, final Listener0 onclick) {
        final ActionToolbarMenuDescriptor<T> action = (ActionToolbarMenuDescriptor<T>) actionItem.getAction();
        if (action instanceof ActionToolbarMenuRadioDescriptor) {
            CheckItem checkItem = new CheckItem();
            ActionToolbarMenuRadioDescriptor<T> radioDescriptor = (ActionToolbarMenuRadioDescriptor<T>) action;
            checkItem.setGroup(radioDescriptor.getGroup());
            checkItem.setChecked(radioDescriptor.mustBeChecked());
            item = checkItem;
        } else if (action instanceof ActionToolbarMenuCheckItemDescriptor) {
            CheckItem checkItem = new CheckItem();
            ActionToolbarMenuCheckItemDescriptor<T> checkItemDescriptor = (ActionToolbarMenuCheckItemDescriptor<T>) action;
            checkItem.setChecked(checkItemDescriptor.getMustBeChecked().mustBeChecked());
            item = checkItem;
        } else {
            item = new Item();
        }
        item.setText(genMenuItemText(action));
        BaseItemListenerAdapter clickListener = new BaseItemListenerAdapter() {
            @Override
            public void onClick(final BaseItem item, final EventObject e) {
                onclick.onEvent();
            }
        };
        item.addListener(clickListener);
        String iconCls = action.getIconCls();
        String iconUrl = action.getIconUrl();
        if (iconCls != null) {
            item.setIconCls(iconCls);
        }
        if (iconUrl != null) {
            item.setIcon(iconUrl);
        }
        item.setTitle(action.getToolTip());
        if (id != null) {
            item.setId(id);
        }
    }

    public Item getItem() {
        return item;
    }

    private String genMenuItemText(final ActionToolbarMenuDescriptor<T> action) {
        // HorizontalPanel hp = new HorizontalPanel();
        // Label title = new Label(action.getText());
        // hp.add(title);
        // hp.setCellWidth(title, "100%");
        // hp.add(new Label(action.getShortcutToS(i 18n)));
        // return hp.getElement().getInnerHTML();
        return action.getText() + action.getShortcutToS();
    }

}
