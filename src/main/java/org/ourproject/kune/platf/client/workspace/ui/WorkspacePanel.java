package org.ourproject.kune.platf.client.workspace.ui;


import org.ourproject.kune.platf.client.workspace.WorkspaceListener;
import org.ourproject.kune.platf.client.workspace.WorkspaceView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private LogoPanel logoPanel;
    private ContextTitleBar contextTitle;
    private GroupNavBar groupNavBar;
    private HorizontalSplitPanel contextHSP;

    public WorkspacePanel(WorkspaceListener listener) {
	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);

	logoPanel = new LogoPanel();
	HorizontalPanel generalHP = new HorizontalPanel();
	generalVP.add(logoPanel);
	generalVP.add(generalHP);

	VerticalPanel contextVP = new VerticalPanel();
	VerticalPanel localNavVP = new VerticalPanel();
	generalHP.add(contextVP);
	generalHP.add(localNavVP);

	groupNavBar = new GroupNavBar(listener);
	SummaryPanel summaryPanel = new SummaryPanel();
	localNavVP.add(groupNavBar);
	localNavVP.add(summaryPanel);

	ContextToolBar contextToolBar = new ContextToolBar();
	contextTitle = new ContextTitleBar();
	contextHSP = new HorizontalSplitPanel();
	ContextBottomBar contextBottomBar = new ContextBottomBar();
	contextVP.add(contextToolBar);
	contextVP.add(contextTitle);
	contextVP.add(contextHSP);
	contextVP.add(contextBottomBar);

	// SiteMessageDialog contextMessagesBar = new SiteMessageDialog();

	// Set properties
	addStyleName("kune-WorkspacePanel");
	contextVP.addStyleName("ContextPanel");
	generalHP.addStyleName("NoSeLoQueEs");
    }

    public void addTab(String name) {
	groupNavBar.addItem(name);
    }

    public void setLogo(String groupName) {
	logoPanel.setLogo(groupName);
    }

    public void setLogo(Image image) {
	logoPanel.setLogo(image);
    }

    public void setContextTitle(String title) {
	contextTitle.setTitle(title);
    }

    public void setSelectedTab(int tabIndex) {
	groupNavBar.selectItem(tabIndex);
    }

    public void setContent(Widget content) {
	Widget left = contextHSP.getLeftWidget();
	if (left != null) contextHSP.remove(left);
	contextHSP.setLeftWidget(content);
    }

    public void setContextMenu(Widget contextMenu) {
	Widget right = contextHSP.getRightWidget();
	if (right != null) contextHSP.remove(right);
	contextHSP.setRightWidget(contextMenu);
    }

    public void adjustSize(int windowWidth, int windowHeight) {
	int contextWidth = windowWidth - 163;
	int contextHeight = windowHeight - 204;

	contextHSP.setSize("" + contextWidth + "px", "" + contextHeight + "px");
	contextHSP.setSplitPosition("" + (contextWidth - 125) + "px");
    }

}
