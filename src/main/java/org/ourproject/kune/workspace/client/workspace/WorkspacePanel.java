package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.ToolTrigger;
import org.ourproject.kune.platf.client.ui.BorderDecorator;
import org.ourproject.kune.workspace.client.WorkspaceView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private final LogoPanel logoPanel;
    private final ContextTitleBar contextTitleBar;
    private final GroupToolsBar groupNavBar;
    private final HorizontalSplitPanel contextHSP;

    public WorkspacePanel() {
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

	groupNavBar = new GroupToolsBar();
	SummaryPanel summaryPanel = new SummaryPanel();
	localNavVP.add(groupNavBar);
	localNavVP.add(summaryPanel);

	ContextToolBar contextToolBar = new ContextToolBar();
	contextTitleBar = new ContextTitleBar();
	contextHSP = new HorizontalSplitPanel();
	String mainBorderColor = Kune.getInstance().c.getMainBorder();
	ContextBottomBar contextBottomBar = new ContextBottomBar();
	BorderDecorator contextToolBarBorderDec = new BorderDecorator(contextToolBar, BorderDecorator.TOPLEFT);
	contextVP.add(contextToolBarBorderDec);
	contextToolBarBorderDec.setColor(mainBorderColor);
	contextVP.add(contextTitleBar);
	contextVP.add(contextHSP);
	BorderDecorator borderDecorator = new BorderDecorator(contextBottomBar, BorderDecorator.BOTTOMLEFT);
	contextVP.add(borderDecorator);
	borderDecorator.setColor(mainBorderColor);

	// Set properties
	addStyleName("kune-WorkspacePanel");
	contextVP.addStyleName("ContextPanel");
	generalHP.addStyleName("GeneralHP");
    }

    public void addTab(final ToolTrigger trigger) {
	groupNavBar.addItem(trigger);
    }

    public void setLogo(final String groupName) {
	logoPanel.setLogo(groupName);
    }

    public void setLogo(final Image image) {
	logoPanel.setLogo(image);
    }

    public void setContextTitle(final String title) {
	contextTitleBar.setTitle(title);
    }

    public void setTool(final String toolName) {
	groupNavBar.selectItem(toolName);
    }

    public void setContent(final View content) {
	Widget left = contextHSP.getLeftWidget();
	if (left != null) {
	    contextHSP.remove(left);
	}
	contextHSP.setLeftWidget((Widget) content);
	((Widget) content).setWidth("99%");
    }

    public void setContext(final View contextMenu) {
	Widget right = contextHSP.getRightWidget();
	if (right != null) {
	    contextHSP.remove(right);
	}
	contextHSP.setRightWidget((Widget) contextMenu);
    }

    public void adjustSize(final int windowWidth, final int windowHeight) {
	int contextWidth = windowWidth - 163;
	int contextHeight = windowHeight - 204;

	contextHSP.setSize("" + contextWidth + "px", "" + contextHeight + "px");
	contextHSP.setSplitPosition("" + (contextWidth - 125) + "px");
    }

}
