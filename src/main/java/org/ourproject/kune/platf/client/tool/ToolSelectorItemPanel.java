package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.ui.RoundedPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Hyperlink;

public class ToolSelectorItemPanel extends RoundedPanel implements ToolSelectorItemView {
    private final Hyperlink hl;

    public ToolSelectorItemPanel(final ToolSelectorItemPresenter presenter, final WorkspaceSkeleton ws) {
	super(RoundedPanel.RIGHT, 2);
	hl = new Hyperlink();
	super.setWidget(hl);
	ws.getEntitySummary().addInTools(this);
	super.sinkEvents(Event.ONCLICK);
	super.addStyleName("k-toolselectoritem");
    }

    @Override
    public void onBrowserEvent(final Event event) {
	super.onBrowserEvent(event);
    }

    public void setLink(final String text, final String targetHistoryToken) {
	hl.setText(text);
	hl.setTargetHistoryToken(targetHistoryToken);
    }

    public void setSelected(final boolean selected) {
	if (selected) {
	    hl.setStylePrimaryName("k-toolselectoritem-sel");
	} else {
	    hl.setStylePrimaryName("k-toolselectoritem-notsel");
	}
	super.setCornerStyleName(hl.getStyleName());
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	if (oldTheme != null) {
	    final String oldName = oldTheme.getName();
	    hl.removeStyleDependentName(oldName);
	}
	final String newName = newTheme.getName();
	hl.addStyleDependentName(newName);
	super.setCornerStyleName(hl.getStyleName());
    }
}
