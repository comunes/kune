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

package org.ourproject.kune.platf.client.newgroup.ui;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.newgroup.NewGroupFormPresenter;
import org.ourproject.kune.platf.client.newgroup.NewGroupFormView;
import org.ourproject.kune.platf.client.ui.dialogs.WizardDialog;
import org.ourproject.kune.sitebar.client.bar.SiteBarTrans;
import org.ourproject.kune.sitebar.client.services.Translate;
import org.ourproject.kune.workspace.client.ui.form.WizardListener;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.CheckboxConfig;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.ComboBoxConfig;
import com.gwtext.client.widgets.form.FieldSetConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextAreaConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;
import com.gwtext.client.widgets.form.event.CheckboxListener;

public class NewGroupFormPanel extends WizardDialog implements NewGroupFormView {
    private static final Translate t = SiteBarTrans.getInstance().t;
    private static final String CC_ALLOW_MODIF_FIELDST = "cc-allow-modif";
    private static final String CC_COMERCIAL_FIELDSET = "cc-comercial";
    private static final String SHORTNAME_FIELD = "short_name";
    private static final String LONGNAME_FIELD = "long_name";
    private static final String PUBLICDESC_FIELD = "public_desc";
    private static final String TYPEOFGROUP_FIELD = "type_of_group";
    private static final String TYPE_OF_LIC_FIELD = "type_of_lic";
    private static final String TYPE_CC_COMER_FIELD = "type_cc_comer";
    private static final String TYPE_ALLOW_M_FIELD = "type_allow_m";
    private final Form newGroupInitialDataForm;
    private Radio projectRadio;
    private Radio orgRadio;
    private Radio communityRadio;

    private TextField shortNameField;
    private TextField longNameField;
    private TextArea publicDescField;
    private final Form chooseLicenseForm;
    private final DeckPanel deck;
    private Checkbox ccLicenses;
    private Checkbox otherLicenses;
    private ComboBox nonCCoptionsCombo;
    private final NewGroupFormPresenter presenter;
    private final List licensesNonCC;
    private Radio ccComercial;
    private Radio ccNonComercial;
    private Radio ccAllowModif;
    private Radio ccNoAllowModif;
    private Radio ccAllowModifShareAlike;

    public NewGroupFormPanel(final NewGroupFormPresenter presenterOrig, final List licensesNonCC) {
	super(t.NewGroup(), true, false, 470, 330, new WizardListener() {
	    public void onBack() {
		presenterOrig.onBack();
	    }

	    public void onCancel() {
		presenterOrig.onCancel();
	    }

	    public void onFinish() {
		presenterOrig.onFinish();
	    }

	    public void onNext() {
		presenterOrig.onNext();
	    }
	});
	this.presenter = presenterOrig;
	this.licensesNonCC = licensesNonCC;
	deck = new DeckPanel();
	newGroupInitialDataForm = createNewGroupInitialDataForm();
	chooseLicenseForm = createChooseLicenseForm();
	VerticalPanel newGroupInitialDataVP = new VerticalPanel();
	VerticalPanel chooseLicenseVP = new VerticalPanel();
	newGroupInitialDataVP.add(newGroupInitialDataForm);
	chooseLicenseVP.add(chooseLicenseForm);
	deck.add(newGroupInitialDataVP);
	deck.add(chooseLicenseVP);
	super.add(deck);
	deck.showWidget(0);
	initBottomButtons();
	newGroupInitialDataVP.addStyleName("kune-Default-Form");
	chooseLicenseVP.addStyleName("kune-Default-Form");
    }

    private void initBottomButtons() {
	super.setEnabledBackButton(false);
	super.setEnabledFinishButton(false);
    }

    public void clearData() {
	newGroupInitialDataForm.reset();
	chooseLicenseForm.reset();
	showNewGroupInitialDataForm();
	initBottomButtons();
    }

    public String getShortName() {
	return shortNameField.getValueAsString();
    }

    public String getLongName() {
	return longNameField.getValueAsString();
    }

    public String getPublicDesc() {
	return publicDescField.getValueAsString();
    }

    public boolean isProject() {
	return projectRadio.getValue();
    }

    public boolean isOrganization() {
	return orgRadio.getValue();
    }

    public boolean isCommunity() {
	return communityRadio.getValue();
    }

    public void showNewGroupInitialDataForm() {
	deck.showWidget(0);
    }

    public void showLicenseForm() {
	deck.showWidget(1);
    }

    private Form createNewGroupInitialDataForm() {
	Form form = new Form(new FormConfig() {
	    {
		setWidth(400);
		setLabelWidth(100);
		setLabelAlign("right");
		setButtonAlign("right");
	    }
	});

	shortNameField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.ShortName());
		setName(SHORTNAME_FIELD);
		setWidth(175);
		setAllowBlank(false);
		setMsgTarget("side");
		setRegex("^[a-zA-Z0-9_]+$");
		// i18n
		setMinLengthText("Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes");
		setMaxLengthText("Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes");
		setRegexText("Must be between 3 and 15 lowercase characters. Can only contain characters, numbers, and dashes");
	    }
	});
	form.add(shortNameField);

	longNameField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.LongName());
		setName(LONGNAME_FIELD);
		setWidth(300);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	});
	form.add(longNameField);

	publicDescField = new TextArea(new TextAreaConfig() {
	    {
		setFieldLabel(t.PublicDescription());
		setName(PUBLICDESC_FIELD);
		setWidth(300);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	});
	form.add(publicDescField);

	form.fieldset(new FieldSetConfig() {
	    {
		setLegend(t.TypeOfGroup());
		setHideLabels(true);
		// setStyle("margin-left: 105px");

	    }
	});

	projectRadio = new Radio(new CheckboxConfig() {
	    {
		setName(TYPEOFGROUP_FIELD);
		setBoxLabel(t.Project());
		setAutoCreate(true);
		setChecked(true);
	    }
	});
	form.add(projectRadio);

	orgRadio = new Radio(new CheckboxConfig() {
	    {
		setName(TYPEOFGROUP_FIELD);
		setBoxLabel(t.Organization());
		setAutoCreate(true);
	    }
	});
	form.add(orgRadio);

	communityRadio = new Radio(new CheckboxConfig() {
	    {
		setName(TYPEOFGROUP_FIELD);
		setBoxLabel(t.Community());
		setAutoCreate(true);
	    }
	});
	form.add(communityRadio);
	form.end();

	form.end();
	form.render();

	return form;
    }

    private Form createChooseLicenseForm() {

	Form form = new Form(new FormConfig() {
	    {

		setLabelWidth(100);
		setLabelAlign("right");
		setButtonAlign("left");
	    }
	});

	form.fieldset(new FieldSetConfig() {
	    {
		// i18n: type of license
		setLegend("Type of License");
		setHideLabels(true);
	    }
	});

	ccLicenses = new Radio(new CheckboxConfig() {
	    {
		setName(TYPE_OF_LIC_FIELD);
		setBoxLabel(t.CreativeCommons());
		setAutoCreate(true);
		setChecked(true);
	    }
	});
	form.add(ccLicenses);

	otherLicenses = new Radio(new CheckboxConfig() {
	    {
		setName(TYPE_OF_LIC_FIELD);
		setBoxLabel(t.OtherLicenses());
		setAutoCreate(true);
	    }
	});
	form.add(otherLicenses);

	form.fieldset(new FieldSetConfig() {
	    {
		setLegend(t.Options());
	    }
	});

	/* CC options */

	form.fieldset(new FieldSetConfig() {
	    {
		setId(CC_COMERCIAL_FIELDSET);
		setLegend(t.CCAllowComercial());
		setHideLabels(true);
	    }
	});

	ccComercial = new Radio(new CheckboxConfig() {
	    {
		setName(TYPE_CC_COMER_FIELD);
		setBoxLabel(t.Yes());
		setAutoCreate(true);
		setChecked(true);
	    }
	});
	form.add(ccComercial);

	ccNonComercial = new Radio(new CheckboxConfig() {
	    {
		setName(TYPE_CC_COMER_FIELD);
		setBoxLabel(t.No());
		setAutoCreate(true);
	    }
	});
	form.add(ccNonComercial);
	form.end();

	form.fieldset(new FieldSetConfig() {
	    {
		setId(CC_ALLOW_MODIF_FIELDST);
		setLegend(t.CCAllowModifications());
		setHideLabels(true);
	    }
	});

	ccAllowModif = new Radio(new CheckboxConfig() {
	    {
		setName(TYPE_ALLOW_M_FIELD);
		setBoxLabel(t.Yes());
		setAutoCreate(true);
	    }
	});
	form.add(ccAllowModif);

	ccAllowModifShareAlike = new Radio(new CheckboxConfig() {
	    {
		setName(TYPE_ALLOW_M_FIELD);
		setBoxLabel(t.CCShareAlike());
		setAutoCreate(true);
		setChecked(true);
	    }
	});
	form.add(ccAllowModifShareAlike);

	ccNoAllowModif = new Radio(new CheckboxConfig() {
	    {
		setName(TYPE_ALLOW_M_FIELD);
		setBoxLabel(t.No());
		setAutoCreate(true);
	    }
	});
	form.add(ccNoAllowModif);
	form.end();

	/* Non CC options */

	final Store store = new SimpleStore(new String[] { "abbr", "licenses" }, getNonCCLicenses());

	nonCCoptionsCombo = new ComboBox(new ComboBoxConfig() {
	    {
		setMinChars(1);
		// i18n
		setFieldLabel("Choose a license");
		setStore(store);
		setDisplayField("licenses");
		setMode("local");
		setTriggerAction("all");
		setEmptyText("Choose a license");
		setLoadingText("Searching...");
		setTypeAhead(true);
		setSelectOnFocus(true);
		setWidth(220);
		setForceSelection(true);
	    }
	});
	form.add(nonCCoptionsCombo);

	form.end();

	form.end();

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

	form.render();

	form.end();

	return form;
    }

    private Object[][] getNonCCLicenses() {
	Object[][] result = new Object[licensesNonCC.size()][2];
	final Iterator iter = licensesNonCC.iterator();
	int i = 0;
	while (iter.hasNext()) {
	    final LicenseDTO license = (LicenseDTO) iter.next();
	    result[i][0] = license.getShortName();
	    result[i][1] = license.getLongName();
	    i++;
	}
	return result;
    }

    public void setCCoptionsVisible(final boolean visible) {
	DOM
		.setStyleAttribute(DOM.getElementById(CC_COMERCIAL_FIELDSET), "visibility", (visible ? "visible"
			: "hidden"));
	DOM.setStyleAttribute(DOM.getElementById(CC_ALLOW_MODIF_FIELDST), "visibility",
		(visible ? "visible" : "hidden"));

    }

    public void setNonCCoptionsVisible(final boolean visible) {

	// nonCCoptionsCombo.setVisible(visible);

    }
}
