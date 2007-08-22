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
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.services.Translate;
import org.ourproject.kune.platf.client.ui.HorizontalLine;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.CheckboxConfig;
import com.gwtext.client.widgets.form.FieldSetConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.event.CheckboxListener;

public class LicenseChooseFormPanel extends Composite implements LicenseChooseFormView {

    protected static final String TYPEOFGROUP_FIELD = null;
    protected static final String OTHER_LIC_FIELD = null;
    private final LicenseChooseFormPresenter presenter;
    private final RadioButton ccRB;
    private final RadioButton allowModifRB;
    private final RadioButton commercialRB;
    private final RadioButton allowModifShareAlikeRB;
    private final DeckPanel options;
    private final Translate t;
    private Radio ccLicenses;
    private Radio otherLicenses;

    public LicenseChooseFormPanel(final LicenseChooseFormPresenter initPresenter, final List nonCCLicenses) {
	this.presenter = initPresenter;

	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);
	generalVP.addStyleName("kune-Default-Form");

	VerticalPanel licenseTypesVP = new VerticalPanel();
	t = Kune.getInstance().t;
	ccRB = new RadioButton("ccOrNot", t.CreativeCommons());
	RadioButton notCcRB = new RadioButton("ccOrNot", t.OtherLicenses());
	options = new DeckPanel();
	// ccIntro = new HTML("<p>" + t.CCExplainMessage() + "</p>", true);

	ListBox otherLicenses = new ListBox();
	VerticalPanel ccOptionsVP = new VerticalPanel();

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
	generalVP.setCellHorizontalAlignment(licenseTypesVP, HasHorizontalAlignment.ALIGN_CENTER);
	generalVP.add(new HorizontalLine());

	// generalVP.add(ccIntro);

	generalVP.add(options);
	// generalVP.add(optionsGroupBox);
	// optionsGroupBox.add(options);

	// Options
	ccOptionsVP.add(comercialLabel);
	// ccOptionsVP.add(new BorderPanel(nonCommercialRB, 0, 0, 0, 18));
	ccOptionsVP.add(commercialRB);
	ccOptionsVP.add(nonCommercialRB);
	ccOptionsVP.add(new HorizontalLine());
	ccOptionsVP.add(allowModifLabel);
	// ccOptionsVP.add(new BorderPanel(allowModifRB, 0, 0, 0, 18));
	ccOptionsVP.add(allowModifRB);
	// ccOptionsVP.add(new BorderPanel(allowModifShareAlikeRB, 0, 0, 0,
	// 18));
	ccOptionsVP.add(allowModifShareAlikeRB);
	// ccOptionsVP.add(new BorderPanel(noModifRB, 0, 0, 0, 18));
	ccOptionsVP.add(noModifRB);

	options.add(ccOptionsVP);
	options.add(otherLicenses);
	options.showWidget(0);

	generalVP.add(new HorizontalLine());

	// optionsGroupBox.setTitle(t.Options());

	ccRB.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.onCCselected();
	    }
	});

	notCcRB.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.onNotCCselected();
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

    }

    private Form chooseLicenseForm() {
	Form form = new Form(new FormConfig() {
	    {
		setWidth(350);
		setLabelWidth(300);
		setLabelAlign("right");
		setButtonAlign("left");
	    }
	});

	form.fieldset(new FieldSetConfig() {
	    {
		// setLegend(t.TypeOfGroup());
		setHideLabels(true);
		// setStyle("margin-left: 105px");
	    }
	});

	ccLicenses = new Radio(new CheckboxConfig() {
	    {
		setName(TYPEOFGROUP_FIELD);
		setBoxLabel(t.CreativeCommons());
		setAutoCreate(true);
		setChecked(true);
	    }
	});
	form.add(ccLicenses);

	otherLicenses = new Radio(new CheckboxConfig() {
	    {
		setName(OTHER_LIC_FIELD);
		setBoxLabel(t.OtherLicenses());
		setAutoCreate(true);
	    }
	});
	form.add(otherLicenses);

	ccLicenses.addCheckboxListener(new CheckboxListener() {
	    public void onCheck(final Checkbox field, final boolean checked) {
		presenter.onCCselected();
	    }
	});

	otherLicenses.addCheckboxListener(new CheckboxListener() {
	    public void onCheck(final Checkbox field, final boolean checked) {
		presenter.onNotCCselected();
	    }
	});

	form.end();

	form.fieldset(new FieldSetConfig() {
	    {
		setLegend(t.Options());
		// setHideLabels(true);
		// setStyle("margin-left: 105px");
	    }
	});

	// TODO: Continue this

	form.end();

	form.end();
	form.render();
	return form;
    }

    public int getSelectedNonCCLicenseIndex() {
	return 0; // otherLicenses.getSelectedIndex();
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

    public void showNotCCoptiones() {
	options.showWidget(1);
    }
}
