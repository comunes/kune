package cc.kune.wspace.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
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
    SplitLayoutPanel split;
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

    public WsArmorImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        split.setWidgetMinSize(splitEast, 150);
        // split.setWidgetMinSize(splitCenter, 200);
    }

    @Override
    public HasWidgets getDocFooter() {
        return docFooter;
    }

    @Override
    public HasWidgets getDocHeader() {
        return docHeader;
    }

    @Override
    public HasWidgets getDocSubheader() {
        return docSubheader;
    }

    @Override
    public HasWidgets getEntityFooter() {
        return entityFooter;
    }

    @Override
    public HasWidgets getEntityHeader() {
        return entityHeader;
    }

    @Override
    public HasWidgets getEntityToolsCenter() {
        return entityToolsCenter;
    }

    @Override
    public HasWidgets getEntityToolsNorth() {
        return entityToolsNorth;
    }

    @Override
    public HasWidgets getEntityToolsSouth() {
        return entityToolsSouth;
    }

    @Override
    public HasWidgets getSitebar() {
        return sitebar;
    }
}
