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
package org.ourproject.kune.client.ui;

import org.ourproject.kune.client.Img;
import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.model.License;
import org.ourproject.kune.client.model.LicenseListener;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class LicenseWidget extends Composite implements ClickListener {
	private Label licenseLabel;
	private Image licenseIcon;
	private License license;
	private HorizontalPanel licenseHP;
	private CustomPushButton chooseLicenseButton;
	private BorderPanel licenseLabelBP;
	private BorderPanel licenseIconBP;
	private BorderPanel chooseLicenseButtonBP;
	
    LicenseListener listener = new LicenseListener() {
        public void onLicenseChange(License license) {
            setLicenseLabel(license.getLongName());
        	setLicenseIcon(license.getImage());
        	chooseLicenseButton.setText(Trans.constants().Change());
        }};
        
    public LicenseWidget() {
    	initialize();
    	layout();
    	setProperties();
    }
    
    public LicenseWidget(License license) {
    	this();
    	setLicense(license);
    }
    
    public void setLicense(License license) {
    	this.license = license;
    	this.license.addLicenseListener(listener);
    	if (!license.isNone()) {
            setLicenseLabel(license.getLongName());
        	setLicenseIcon(license.getImage());
        	chooseLicenseButton.setText(Trans.constants().Change());
    	}
    	else {
    		setLicenseIcon(Img.ref().clear());
    	}
    }
   
    private void initialize() {
        licenseHP = new HorizontalPanel();
        licenseHP.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        licenseLabel = new Label();
        licenseIcon = new Image();
        chooseLicenseButton = new CustomPushButton(Trans.constants().ChooseLicense(), CustomPushButton.LARGE, this);
        licenseLabelBP = new BorderPanel(licenseLabel);
    	licenseIconBP = new BorderPanel(licenseIcon);
    	chooseLicenseButtonBP = new BorderPanel(chooseLicenseButton);
        this.initWidget(licenseHP);
    }
    
    public void setStyleOfLicenseLabel(String style) {
        licenseLabel.setStyleName(style);
    }

    public void addStyleOfLicenseLabel(String style) {
        licenseLabel.addStyleName(style);
    }
    
    private void layout() {
    	licenseHP.add(licenseLabelBP);
    	licenseHP.add(licenseIconBP);
    	licenseHP.add(chooseLicenseButtonBP);
    }
    
    private void setProperties() {
    	licenseHP.setBorderWidth(0);
    	licenseHP.setSpacing(0);
    }

	/**
	 * @param licenseIcon the licenseIcon to set
	 */
	public void setLicenseIcon(AbstractImagePrototype licenseIcon) {
		licenseIcon.applyTo(this.licenseIcon);
	}

	/**
	 * @return the licenseLabel
	 */
	public Label getLicenseLabel() {
		return licenseLabel;
	}

	/**
	 * @param licenseLabel the licenseLabel to set
	 */
	public void setLicenseLabel(String licenseLabel) {
		this.licenseLabel.setText(licenseLabel);
	}
    
    public void onClick(Widget sender) { 
        if (sender == chooseLicenseButton) {
        	LicenseChooseDialog licenseDialog = new LicenseChooseDialog(this.license);
        	licenseDialog.show();
        }
    }
    
    public void setView(boolean showLabel, boolean showIcon, boolean showButton) {
    	this.licenseLabel.setVisible(showLabel);
    	this.licenseIcon.setVisible(showIcon);
        this.chooseLicenseButton.setVisible(showButton);
        if (showLabel & showIcon) {
        	this.licenseLabelBP.setMargins(0, 7, 0, 0);
        }
        if (showButton) {
        	this.chooseLicenseButtonBP.setMargins(0, 0, 0, CustomPushButton.HORSPACELARGE);
        }        
    }
}