/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.client.ui.desktop;

import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.model.Group;
import org.ourproject.kune.client.model.License;
import org.ourproject.kune.client.model.LicenseListener;
import org.ourproject.kune.client.ui.BorderPanel;
import org.ourproject.kune.client.ui.LicenseWidget;
import org.ourproject.kune.client.ui.RoundedPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class ContextBottomBar extends Composite {
	private HorizontalPanel bottomHP = null;
    private RoundedPanel rn = null;
    private Group group = null;
    private Label groupLabel = null;
    private LicenseWidget licenseWidget = null;
    private Label licenseLabel = null;
    private HTML spaceExpandCell = null;
    LicenseListener listener = new LicenseListener() {
        public void onLicenseChange(License license) {
            setLicenseLabel(license);
        }};
        
	public ContextBottomBar() {
		super();
		initialize();
		layout();
		setProperties();
	}
	
	public ContextBottomBar(Group group, License license) {
		this();
		setGroup(group);
		setLicense(license);
	}
	
	public void setGroup(Group group) {
		this.group = group;
		this.groupLabel.setText("© " + this.group.getLongName());
	}
	
	public void setLicense(License license) {
		this.licenseWidget.setLicense(license);
		license.addLicenseListener(listener);
		setLicenseLabel(license);
	}
	
	private void setLicenseLabel(License license) {
		if (!license.isNone()) {
            this.licenseLabel.setText(Trans.constants().theseContentsUnderLicense());
		}
	}

	private void initialize() {
		bottomHP = new HorizontalPanel();
		groupLabel = new Label();
		licenseWidget = new LicenseWidget();
		licenseLabel = new Label();
        bottomHP.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		rn = new RoundedPanel(RoundedPanel.BOTTOMLEFT);
		spaceExpandCell = new HTML("<b></b>");
	}

	private void layout() {
		bottomHP.add(groupLabel);
		bottomHP.add(new BorderPanel(licenseLabel, 0, 5, 0, 0));
		bottomHP.add(licenseWidget);
		rn.add(new BorderPanel(bottomHP, 0, 9));
		bottomHP.add(spaceExpandCell);
		this.initWidget(rn);
	}

	private void setProperties() {
		this.setWidth("100%");
		rn.setWidth("100%");
        bottomHP.setBorderWidth(0);
		bottomHP.setSpacing(0);
		bottomHP.setWidth("100%");		
		bottomHP.setStyleName("context-bottombar");
		bottomHP.setHeight("24");
		bottomHP.setCellWidth(spaceExpandCell, "100%");
		licenseWidget.setView(false, true, false);
		licenseWidget.setStyleOfLicenseLabel("context-bottombar");
        spaceExpandCell.setWidth("100%");
        rn.setCornerStyleName("context-bottombar-outer");
        //licenseLabel.removeStyleName("gwt-Label");
        //groupLabel.removeStyleName("gwt-Label");
    }

	public void addWidget(Widget widget) {
        bottomHP.insert(widget, bottomHP.getWidgetCount()-1);
	}
}
