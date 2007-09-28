/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.license;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.ui.HorizontalLine;
import org.ourproject.kune.platf.client.ui.TitledPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarTrans;
import org.ourproject.kune.sitebar.client.services.Translate;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LicenseChooseFormPanel extends Composite implements LicenseChooseFormView {

    protected static final String CC_LIC_FIELD = "cc-lic";
    protected static final String OTHER_LIC_FIELD = "other-lic";

    private final RadioButton ccRB;
    private final RadioButton allowModifRB;
    private final RadioButton commercialRB;
    private final RadioButton allowModifShareAlikeRB;
    private final DeckPanel options;
    private final Translate t;
    private final Label ccIntro;
    private final ListBox otherLicenses;

    public LicenseChooseFormPanel(final List nonCCLicenses) {
	t = SiteBarTrans.getInstance().t;

	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);
	generalVP.addStyleName("kune-Default-Form");

	VerticalPanel licenseTypesVP = new VerticalPanel();
	ccRB = new RadioButton("ccOrNot", t.CreativeCommons());
	RadioButton notCcRB = new RadioButton("ccOrNot", t.OtherLicenses());
	options = new DeckPanel();
	ccIntro = new Label(t.CCExplainMessage());

	otherLicenses = new ListBox();
	VerticalPanel ccOptionsVP = new VerticalPanel();
	VerticalPanel nonCcOptionsVP = new VerticalPanel();

	Label comercialLabel = new Label(t.CCAllowComercial());
	commercialRB = new RadioButton("comercial", t.Yes());
	RadioButton nonCommercialRB = new RadioButton("comercial", t.No());

	Label allowModifLabel = new Label(t.CCAllowModifications());
	allowModifRB = new RadioButton("allowModif", t.Yes());
	allowModifShareAlikeRB = new RadioButton("allowModif", t.CCShareAlike());
	RadioButton noModifRB = new RadioButton("allowModif", t.No());

	generalVP.add(licenseTypesVP);
	licenseTypesVP.add(ccRB);
	licenseTypesVP.add(notCcRB);
	// generalVP.setCellHorizontalAlignment(licenseTypesVP,
	// HasHorizontalAlignment.ALIGN_CENTER);

	generalVP.add(options);

	// Options

	generalVP.add(new TitledPanel(t.Options(), options));

	ccOptionsVP.add(ccIntro);
	ccOptionsVP.add(comercialLabel);
	ccOptionsVP.add(commercialRB);
	ccOptionsVP.add(nonCommercialRB);
	ccOptionsVP.add(new HorizontalLine());
	ccOptionsVP.add(allowModifLabel);
	ccOptionsVP.add(allowModifRB);
	ccOptionsVP.add(allowModifShareAlikeRB);
	ccOptionsVP.add(noModifRB);

	// i18n
	nonCcOptionsVP.add(new Label("Select one of these licenses:"));
	nonCcOptionsVP.add(otherLicenses);

	options.add(ccOptionsVP);
	options.add(nonCcOptionsVP);
	options.showWidget(0);

	comercialLabel.addStyleName("kune-License-CC-Header");
	allowModifLabel.addStyleName("kune-License-CC-Header");
	nonCommercialRB.addStyleName("kune-Margin-Large-lr");
	commercialRB.addStyleName("kune-Margin-Large-lr");
	allowModifRB.addStyleName("kune-Margin-Large-lr");
	allowModifShareAlikeRB.addStyleName("kune-Margin-Large-lr");
	noModifRB.addStyleName("kune-Margin-Large-lr");

	ccRB.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		showCCoptions();
	    }
	});

	notCcRB.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		showNotCCoptions();
	    }
	});

	for (int i = 0; i < nonCCLicenses.size(); i++) {
	    String licenseDescrip = ((LicenseDTO) nonCCLicenses.get(i)).getLongName();
	    otherLicenses.addItem(licenseDescrip);
	}
	if (nonCCLicenses.size() > 0) {
	    otherLicenses.setItemSelected(0, true);
	    otherLicenses.setVisibleItemCount(1);
	}

	generalVP.addStyleName("kune-Default-Form");

    }

    public int getSelectedNonCCLicenseIndex() {
	return otherLicenses.getSelectedIndex();
    }

    public boolean isAllowModif() {
	return allowModifRB.isChecked();
    }

    public boolean isAllowModifShareAlike() {
	return allowModifShareAlikeRB.isChecked();
    }

    public boolean isCCselected() {
	return ccRB.isChecked();
    }

    public boolean permitComercial() {
	return commercialRB.isChecked();
    }

    public void reset() {
	options.showWidget(0);
	ccRB.setChecked(true);
	commercialRB.setChecked(true);
	allowModifShareAlikeRB.setChecked(true);
    }

    public void showCCoptions() {
	options.showWidget(0);
    }

    public void showNotCCoptions() {
	options.showWidget(1);
    }
}
