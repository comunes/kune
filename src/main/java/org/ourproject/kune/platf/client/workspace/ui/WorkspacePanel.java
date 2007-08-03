package org.ourproject.kune.platf.client.workspace.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.BorderDecorator;
import org.ourproject.kune.platf.client.workspace.WorkspaceView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePanel extends Composite implements WorkspaceView {
    private LogoPanel logoPanel;
    private ContextTitleBar contextTitleBar;
    private GroupNavBar groupNavBar;
    private HorizontalSplitPanel contextHSP;

    public WorkspacePanel() {
        VerticalPanel generalVP = new VerticalPanel();
        initWidget(generalVP);

        logoPanel = new LogoPanel();
        HorizontalPanel generalHP = new HorizontalPanel();

        SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
        SiteMessagePanel siteMessagePanel = new SiteMessagePanel(siteMessagePresenter);
        siteMessagePresenter.init(siteMessagePanel);
        SiteMessage siteMessage = (SiteMessage) siteMessagePresenter;

        siteMessage.info("lalalala");
        siteMessage.important("bla bla bla");
        siteMessage.adjustWidth(800);

        generalVP.add(siteMessagePanel);
        generalVP.add(logoPanel);
        generalVP.add(generalHP);

        VerticalPanel contextVP = new VerticalPanel();
        VerticalPanel localNavVP = new VerticalPanel();
        generalHP.add(contextVP);
        generalHP.add(localNavVP);

        groupNavBar = new GroupNavBar();
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

    public void addTab(String name, String caption) {
        groupNavBar.addItem(name, caption);
    }

    public void setLogo(String groupName) {
        logoPanel.setLogo(groupName);
    }

    public void setLogo(Image image) {
        logoPanel.setLogo(image);
    }

    public void setContextTitle(String title) {
        contextTitleBar.setTitle(title);
    }

    public void setTool(String toolName) {
        groupNavBar.selectItem(toolName);
    }

    public void setContent(View content) {
        Widget left = contextHSP.getLeftWidget();
        if (left != null)
            contextHSP.remove(left);
        contextHSP.setLeftWidget((Widget) content);
    }

    public void setContext(View contextMenu) {
        Widget right = contextHSP.getRightWidget();
        if (right != null)
            contextHSP.remove(right);
        contextHSP.setRightWidget((Widget) contextMenu);
    }

    public void adjustSize(int windowWidth, int windowHeight) {
        int contextWidth = windowWidth - 163;
        int contextHeight = windowHeight - 204;

        contextHSP.setSize("" + contextWidth + "px", "" + contextHeight + "px");
        contextHSP.setSplitPosition("" + (contextWidth - 125) + "px");
    }

}
