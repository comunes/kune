package cc.kune.wspace.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WsArmorImpl extends Composite implements WsArmor {

    interface WsArmorImplUiBinder extends UiBinder<Widget, WsArmorImpl> {
    }

    private static WsArmorImplUiBinder uiBinder = GWT.create(WsArmorImplUiBinder.class);

    @UiField
    DockLayoutPanel mainpanel;
    @UiField
    FlowPanel sitebar;
    @UiField
    SplitLayoutPanel groupSpace;
    @UiField
    DockLayoutPanel splitCenter;
    @UiField
    DockLayoutPanel splitEast;
    @UiField
    VerticalPanel entityToolsSouth;
    @UiField
    VerticalPanel entityToolsNorth;
    @UiField
    VerticalPanel entityToolsCenter;
    @UiField
    FlowPanel entityHeader;
    @UiField
    FlowPanel entityFooter;
    @UiField
    FlowPanel docHeader;
    @UiField
    FlowPanel docSubheader;
    @UiField
    FlowPanel docFooter;
    @UiField
    SimplePanel userSpace;
    @UiField
    Frame publicSpace;
    @UiField
    SimplePanel homeSpace;
    @UiField
    TabLayoutPanel tabs;

    public WsArmorImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        groupSpace.setWidgetMinSize(splitEast, 150);
        tabs.setStyleName("k-spaces");
        homeSpace.add(RootPanel.get("k-home-ini"));
        publicSpace.setUrl("http://www.google.com");
        userSpace.add(new Label("Wave client"));
    }

    @Override
    public ForIsWidget getDocFooter() {
        return docFooter;
    }

    @Override
    public ForIsWidget getDocHeader() {
        return docHeader;
    }

    @Override
    public ForIsWidget getDocSubheader() {
        return docSubheader;
    }

    @Override
    public ForIsWidget getEntityFooter() {
        return entityFooter;
    }

    @Override
    public ForIsWidget getEntityHeader() {
        return entityHeader;
    }

    @Override
    public ForIsWidget getEntityToolsCenter() {
        return entityToolsCenter;
    }

    @Override
    public ForIsWidget getEntityToolsNorth() {
        return entityToolsNorth;
    }

    @Override
    public ForIsWidget getEntityToolsSouth() {
        return entityToolsSouth;
    }

    @Override
    public ForIsWidget getSitebar() {
        return sitebar;
    }

    @Override
    public void selectHomeSpace() {
        tabs.selectTab(homeSpace);
    }

    @Override
    public void selectUserSpace() {
        tabs.selectTab(userSpace);
    }

    @Override
    public void selectGroupSpace() {
        tabs.selectTab(groupSpace);
    }

    @Override
    public void selectPublicSpace() {
        tabs.selectTab(publicSpace);
    }
}
