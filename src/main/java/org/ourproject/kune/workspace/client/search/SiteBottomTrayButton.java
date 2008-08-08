package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class SiteBottomTrayButton {

    private final ToolbarButton traybarButton;

    public SiteBottomTrayButton(final String icon, final String tooltip, final Window dialog, final WorkspaceSkeleton ws) {
	traybarButton = new ToolbarButton();
	traybarButton.setTooltip(tooltip);
	traybarButton.setIcon(icon);
	traybarButton.addListener(new ButtonListenerAdapter() {
	    @Override
	    public void onClick(final Button button, final EventObject e) {
		if (dialog.isVisible()) {
		    dialog.hide();
		} else {
		    dialog.show();
		}
	    }

	});
	ws.getSiteTraybar().addButton(traybarButton);
    }

    public void destroy() {
	traybarButton.destroy();
    }

}
