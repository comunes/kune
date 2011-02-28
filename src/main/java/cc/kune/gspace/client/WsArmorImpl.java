/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.gspace.client;

import cc.kune.pspace.client.PSpacePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
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
    FlowPanel userSpace;

    @Inject
    public WsArmorImpl(final PSpacePresenter pspace) {
        initWidget(uiBinder.createAndBindUi(this));
        groupSpace.setWidgetMinSize(splitEast, 150);
        tabs.setStyleName("k-spaces");
        homeSpace.add(RootPanel.get("k-home-wrapper"));
        publicSpace.add(pspace.getWidget());
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

    public ForIsWidget getUserSpace() {
        return userSpace;
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
