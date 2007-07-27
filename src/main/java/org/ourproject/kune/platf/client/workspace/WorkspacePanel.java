package org.ourproject.kune.platf.client.workspace;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkspacePanel extends Composite implements WorkspaceView {

    private LogoPanel logoPanel;
    private SiteMessageDialog contextMessagesBar;
    private LocalNavBar localNavBar;
    private SummaryPanel summaryPanel;
    private ContextNavBar contextNavBar;
    private ContextToolBar contextToolBar;
    private ContextTitle contextTitle;
    private ContextContents contextContents;
    private ContextBottomBar contextBottomBar;
    private HorizontalPanel generalHP;
    private VerticalPanel localNavVP;
    private VerticalPanel contextVP;
    private HorizontalSplitPanel contextHSP;
    private VerticalPanel generalVP;

    public WorkspacePanel() {

        // Initialization
        generalVP = new VerticalPanel();
        generalHP = new HorizontalPanel();
        localNavVP = new VerticalPanel();
        contextVP = new VerticalPanel();
        contextHSP = new HorizontalSplitPanel();
        initWidget(generalVP);
        logoPanel = new LogoPanel();
        contextMessagesBar = new SiteMessageDialog();
        localNavBar = new LocalNavBar();
        summaryPanel = new SummaryPanel();
        contextNavBar = new ContextNavBar();
        contextToolBar = new ContextToolBar();
        contextTitle = new ContextTitle();
        contextContents = new ContextContents();
        contextBottomBar = new ContextBottomBar();

        // Layout
        generalVP.add(logoPanel);
        generalHP.add(generalHP);
        generalHP.add(contextVP);
        generalHP.add(localNavVP);
        localNavVP.add(localNavBar);
        localNavVP.add(summaryPanel);
        contextVP.add(contextToolBar);
        contextVP.add(contextTitle);
        contextVP.add(contextHSP);
        contextVP.add(contextBottomBar);
        contextHSP.setLeftWidget(contextContents);
        contextHSP.setRightWidget(contextNavBar);

        // Set properties
        addStyleName(".kune-WorkspacePanel");
        contextVP.addStyleName(".kune-WorkspacePanel-contextVP");
        generalHP.addStyleName(".kune-WorkspacePanel-generalHP");
    }

    public void addTab(String name) {
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

}
