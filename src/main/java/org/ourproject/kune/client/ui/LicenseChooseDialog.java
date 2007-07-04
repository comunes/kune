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

import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.model.License;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LicenseChooseDialog extends DialogBox implements ClickListener {
	private License license = null;
	private VerticalPanel generalVP = null;
	
	private VerticalPanel licenseTypesVP = null;
	private RadioButton ccRB = null; 
	private RadioButton notCcRB = null;
	
	private GroupBox optionsGroupBox = null;
	private DeckPanel options = null;
	//private HTML ccIntro = null;
	private CustomPushButton selectBT = null;
	private CustomPushButton cancelBT = null;
	private HorizontalPanel buttonsHP = null;
	private ListBox otherLicenses = null;
	
	private VerticalPanel ccOptionsVP = null;
	
	private Label comercialLabel = null;
	private RadioButton commercialRB = null;
	private RadioButton nonCommercialRB = null;
	
	private Label allowModifLabel = null;
	private RadioButton allowModifRB = null;
	private RadioButton allowModifShareAlikeRB = null;
	private RadioButton noModifRB = null;
	
	private License[] notCClicenses;
	
	// Default CC license (Copyleft)
	boolean allowCommercialUse = true;
	int allowModif = License.ALLOWMODIFSHAREALIKE;
	
	public LicenseChooseDialog(License license) {
		super(false, true);
    	this.license = license;
		initialize();
		layout();
		setProperties();
		restoreValues();
	}

	private void initialize() {
        generalVP = new VerticalPanel();
        licenseTypesVP = new VerticalPanel();
        ccRB = new RadioButton("ccOrNot", Trans.constants().CreativeCommons());
        notCcRB = new RadioButton("ccOrNot", Trans.constants().OtherLicenses());
        optionsGroupBox = new GroupBox();
        options = new DeckPanel();
        //ccIntro = new HTML("<p>" + Trans.constants().CCExplainMessage() + "</p>", true);
        selectBT = new CustomPushButton(Trans.constants().SelectThisLicense());
        cancelBT = new CustomPushButton(Trans.constants().Cancel());
        buttonsHP = new HorizontalPanel();
        otherLicenses = new ListBox();
        ccOptionsVP = new VerticalPanel();
        
        comercialLabel = new Label(Trans.constants().CCAllowComercial());
        commercialRB = new RadioButton("comercial", Trans.constants().Yes());
        nonCommercialRB = new RadioButton("comercial", Trans.constants().No());
        
        allowModifLabel = new Label(Trans.constants().CCAllowModifications());
        allowModifRB = new RadioButton("allowModif", Trans.constants().Yes());
        allowModifShareAlikeRB = new RadioButton("allowModif", Trans.constants().CCShareAlike());
        noModifRB = new RadioButton("allowModif", Trans.constants().No());    
	}
	
	private void layout() {
		generalVP.add(licenseTypesVP);
		licenseTypesVP.add(ccRB);
		licenseTypesVP.add(notCcRB);
		generalVP.setCellHorizontalAlignment(licenseTypesVP, HasHorizontalAlignment.ALIGN_CENTER);
		generalVP.add(new HorizontalLine());

		// TODO: Fix wrap problems
		// generalVP.add(ccIntro);
		
		generalVP.add(optionsGroupBox);
		optionsGroupBox.add(options);
		
		// Options
		ccOptionsVP.add(comercialLabel);
		ccOptionsVP.add(new BorderPanel(commercialRB, 0, 0, 0, 18));
		ccOptionsVP.add(new BorderPanel(nonCommercialRB, 0, 0, 0, 18));
		ccOptionsVP.add(new HorizontalLine());
		ccOptionsVP.add(allowModifLabel);
		ccOptionsVP.add(new BorderPanel(allowModifRB, 0, 0, 0, 18));
		ccOptionsVP.add(new BorderPanel(allowModifShareAlikeRB, 0, 0, 0, 18));
		ccOptionsVP.add(new BorderPanel(noModifRB, 0, 0, 0, 18));
		
		options.add(ccOptionsVP);
		options.add(otherLicenses);
		options.showWidget(0);
		
		generalVP.add(new HorizontalLine());
		buttonsHP.add(new BorderPanel(selectBT, CustomPushButton.VERSPACELARGE, 0, CustomPushButton.VERSPACELARGE, CustomPushButton.HORSPACELARGE));
		buttonsHP.add(new BorderPanel(cancelBT, CustomPushButton.VERSPACELARGE, 0, CustomPushButton.VERSPACELARGE, CustomPushButton.HORSPACELARGE));
		generalVP.add(buttonsHP);
        setWidget(generalVP);
	}
	
	public void onClick(Widget sender) { 
        if (sender == selectBT) {
    		if (ccRB.isChecked()) {
    			license.setCC(allowCommercialUse, allowModif);
    		} else {
                for (int i = 0; i < notCClicenses.length; i++) {
    	    		if (otherLicenses.isItemSelected(i)) {
    	    			license.setLicense(notCClicenses[i]);
    	    		}
    	    	}
    		}
            this.hide();
    	}
    	else if (sender == cancelBT) {
            this.hide();
    	} else if (sender == ccRB & ccRB.isChecked()) {
            options.showWidget(0);
            //ccIntro.setVisible(true);
        }
    	else if (sender == notCcRB & notCcRB.isChecked()) {
    		options.showWidget(1);
    		//ccIntro.setVisible(false);
        }
        else if (sender == commercialRB & commercialRB.isChecked()) {
    		allowCommercialUse = true;
    	}
    	else if (sender == nonCommercialRB & nonCommercialRB.isChecked()) {
    		allowCommercialUse = false;
    	}
    	else if (sender == allowModifRB & allowModifRB.isChecked()) {
    		allowModif = License.ALLOWMODIF;
    	}
    	else if (sender == allowModifShareAlikeRB & allowModifShareAlikeRB.isChecked()) {
    		allowModif = License.ALLOWMODIFSHAREALIKE;
    	}
    	else if (sender == noModifRB & noModifRB.isChecked()) {
    		allowModif = License.NOMODIF;
    	}
    }

    public boolean onKeyDownPreview(char key, int modifiers) {
        // Use the popup's key preview hooks to close the dialog when either
        // enter or escape is pressed.
        switch (key) {
          case KeyboardListener.KEY_ENTER:
          case KeyboardListener.KEY_ESCAPE:
            hide();
            break;
        }
        return true;
    }
    
    private void setProperties() {
		generalVP.setBorderWidth(0);
		generalVP.setSpacing(0);
        
        //ccIntro.setWordWrap(true);
        //ccIntro.setWidth("500");
        
		optionsGroupBox.setTitle(Trans.constants().Options());
        ccOptionsVP.setBorderWidth(0);
        ccOptionsVP.setSpacing(0);
        
        buttonsHP.setBorderWidth(0);
        buttonsHP.setSpacing(0);
        
        ccRB.addClickListener(this);
        notCcRB.addClickListener(this);
    	commercialRB.addClickListener(this);
    	nonCommercialRB.addClickListener(this);
    	allowModifRB.addClickListener(this);
    	allowModifShareAlikeRB.addClickListener(this);
    	noModifRB.addClickListener(this);
    	selectBT.addClickListener(this);
    	cancelBT.addClickListener(this);
    	cancelBT.setWidth("" + selectBT.getOffsetWidth());
    	
    	notCClicenses = License.getLicensesNotCC();
    	for (int i = 0; i < notCClicenses.length; i++) {
            otherLicenses.addItem(notCClicenses[i].getLongName());
    	}
    	otherLicenses.setItemSelected(0, true);
    	otherLicenses.setVisibleItemCount(1);
    	
        setText(Trans.constants().ChooseLicense());
        //otherLicenses.setWidth("" + ccOptionsVP.getOffsetWidth());
        //this.setSize("400", "300");
    }

    private void restoreValues() {	
    	if (license.isNone()) {
    		// Default CC license (copyleft)
    		options.showWidget(0);
    		ccRB.setChecked(true);
    		commercialRB.setChecked(true);
            allowModifShareAlikeRB.setChecked(true);
    	}
    	else {
    		// TODO Deprecated licenses
    		if (license.isCC()) {
    			ccRB.setChecked(true);
    			options.showWidget(0);
    			allowCommercialUse = license.allowCommercialUse();
    			allowModif = license.allowModif(); 
    			if (allowCommercialUse) {
    				commercialRB.setChecked(true);
    			} else {
    				nonCommercialRB.setChecked(true);
    			}
    			if (allowModif == License.ALLOWMODIF) { allowModifRB.setChecked(true); }
    			if (allowModif == License.ALLOWMODIFSHAREALIKE) { allowModifShareAlikeRB.setChecked(true); }
    			if (allowModif == License.NOMODIF) { noModifRB.setChecked(true); }
    		}
    		else {
    			// Other licenses
    			notCcRB.setChecked(true);
    			options.showWidget(1);
            	for (int i = 0; i < notCClicenses.length; i++) {
                    if (license.getLongName() == notCClicenses[i].getLongName()) {
                    	otherLicenses.setItemSelected(i, true);
                    }
                    else {
                    	otherLicenses.setItemSelected(i, false);
                    }
            	}
            	// CC default anyway;
        		commercialRB.setChecked(true);
                allowModifShareAlikeRB.setChecked(true);
    		}
    	}
        
	}

    public void show() {
    	super.show();
    	super.center();
    } 

}