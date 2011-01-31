package cc.kune.gspace.client;

import cc.kune.pspace.client.PSpacePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class WsArmorImpl extends Composite implements WsArmor {

    interface WsArmorImplUiBinder extends UiBinder<Widget, WsArmorImpl> {
    }

    private static WsArmorImplUiBinder uiBinder = GWT.create(WsArmorImplUiBinder.class);

    @UiField
    VerticalPanel docContainer;
    @UiField
    FlowPanel docFooter;
    @UiField
    FlowPanel docHeader;
    @UiField
    FlowPanel docSubheader;
    @UiField
    FlowPanel entityFooter;
    @UiField
    FlowPanel entityHeader;
    @UiField
    VerticalPanel entityToolsCenter;
    @UiField
    VerticalPanel entityToolsNorth;
    @UiField
    VerticalPanel entityToolsSouth;
    @UiField
    SplitLayoutPanel groupSpace;
    @UiField
    SimplePanel homeSpace;
    @UiField
    DockLayoutPanel mainpanel;
    @UiField
    SimplePanel publicSpace;
    @UiField
    FlowPanel sitebar;
    @UiField
    DockLayoutPanel splitCenter;
    @UiField
    DockLayoutPanel splitEast;

    @UiField
    TabLayoutPanel tabs;

    @UiField
    SimplePanel userSpace;

    @Inject
    public WsArmorImpl(final PSpacePresenter pspace) {
        initWidget(uiBinder.createAndBindUi(this));
        groupSpace.setWidgetMinSize(splitEast, 150);
        tabs.setStyleName("k-spaces");
        homeSpace.add(RootPanel.get("k-home-ini"));
        publicSpace.add(pspace.getWidget());
        userSpace.add(new Label("User space"));
        // userSpace.add(new WebClient());
    }

    @Override
    public ForIsWidget getDocContainer() {
        return docContainer;
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
    public void selectGroupSpace() {
        tabs.selectTab(groupSpace);
    }

    @Override
    public void selectHomeSpace() {
        tabs.selectTab(homeSpace);
    }

    @Override
    public void selectPublicSpace() {
        tabs.selectTab(publicSpace);
    }

    @Override
    public void selectUserSpace() {
        tabs.selectTab(userSpace);
    }
}
