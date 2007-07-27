package org.ourproject.kune.platf.client.workspace.ui;


import org.ourproject.kune.platf.client.workspace.WorkspaceListener;
import org.ourproject.kune.platf.client.workspace.WorkspaceView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private LogoPanel logoPanel;
    private ContextTitleBar contextTitle;
    private final WorkspaceListener listener;

    public WorkspacePanel(WorkspaceListener listener) {
        this.listener = listener;
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
        
        LocalNavBar localNavBar = new LocalNavBar();
        SummaryPanel summaryPanel = new SummaryPanel();
        localNavVP.add(localNavBar);
        localNavVP.add(summaryPanel);
        
        ContextToolBar contextToolBar = new ContextToolBar();
        contextTitle = new ContextTitleBar();
        HorizontalSplitPanel contextHSP = new HorizontalSplitPanel();
        ContextBottomBar contextBottomBar = new ContextBottomBar();
        contextVP.add(contextToolBar);
        contextVP.add(contextTitle);
        contextVP.add(contextHSP);
        contextVP.add(contextBottomBar);
        
        ContextContents contextContents = new ContextContents();
        contextHSP.setLeftWidget(contextContents);

        ContextNavBar contextNavBar = new ContextNavBar();
        contextHSP.setRightWidget(contextNavBar);
//        SiteMessageDialog contextMessagesBar = new SiteMessageDialog();

        // Set properties
        addStyleName("kune-WorkspacePanel");
        contextVP.addStyleName("ContextPanel");
        generalHP.addStyleName("NoSeLoQueEs");
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
