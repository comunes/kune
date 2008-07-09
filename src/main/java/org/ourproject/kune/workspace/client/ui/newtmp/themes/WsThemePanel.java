package org.ourproject.kune.workspace.client.ui.newtmp.themes;

import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class WsThemePanel extends ToolbarButton implements WsThemeView {

    private final Menu menu;

    public WsThemePanel(final WorkspaceSkeleton ws) {
	menu = new Menu();
	for (final WsTheme theme : WsTheme.values()) {
	    final MenuItem item = new MenuItem();
	    item.setText(theme.toString());
	    menu.addItem(item);
	    item.addListener(new BaseItemListenerAdapter() {
		@Override
		public void onClick(final BaseItem item, final EventObject e) {
		    ws.setTheme(theme);
		}
	    });
	}
	menu.setDefaultAlign("br-tr");
	super.setMenu(menu);
	ws.getSiteTraybar().addButton(this);
    }

    public void setVisible(final boolean visible) {
	menu.setVisible(visible);
    }

}
