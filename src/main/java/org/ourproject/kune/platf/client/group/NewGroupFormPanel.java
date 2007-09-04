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

package org.ourproject.kune.platf.client.group;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChangeListener;
import org.ourproject.kune.platf.client.license.LicenseChooseForm;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.services.Translate;
import org.ourproject.kune.platf.client.ui.CustomButton;
import org.ourproject.kune.platf.client.ui.dialogs.TwoButtonsDialog;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.form.CheckboxConfig;
import com.gwtext.client.widgets.form.FieldSetConfig;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.Radio;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextAreaConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;

public class NewGroupFormPanel extends Composite implements NewGroupFormView {
    private static final Translate t = Kune.getInstance().t;
    private static final String SHORTNAME_FIELD = "short_name";
    private static final String LONGNAME_FIELD = "long_name";
    private static final String PUBLICDESC_FIELD = "public_desc";
    private static final String TYPEOFGROUP_FIELD = "type_of_group";
    private static final String LICENSE_FIELD = "license";

    private TwoButtonsDialog licenseDialog;
    private final Form newGroupForm;
    private Radio projectRadio;
    private Radio orgRadio;
    private Radio communityRadio;
    private TextField licenseField;
    private TextField shortNameField;
    private TextField longNameField;
    private TextArea publicDescField;

    public NewGroupFormPanel() {
	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);

	newGroupForm = createNewGroupForm();
	generalVP.add(newGroupForm);
	generalVP.addStyleName("kune-Default-Form");
    }

    public void setLicense(final String longName) {
	// TODO Auto-generated method stub
    }

    public void clearData() {
	licenseField.reset();
	newGroupForm.reset();
	// TODO: licenseDialog reset
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

    private void showlicenseDialog() {
	final LicenseChooseForm license = SiteBarFactory.createLicenseChoose(new LicenseChangeListener() {
	    public void onCancel() {
		licenseDialog.hide();
	    }

	    public void onLicenseChange(LicenseDTO licenseDTO) {
		licenseField.setValue(licenseDTO.getLongName());
		licenseDialog.hide();
	    }
	});
	licenseDialog = new TwoButtonsDialog(t.SelectLicense(), t.ChooseLicense(), t.Cancel(), true, false, 350, 300,
		new FormListener() {
		    public void onAccept() {
			license.onSelect();
		    }

		    public void onCancel() {
			license.onCancel();
		    }
		});
	licenseDialog.add((Widget) license.getView());
	licenseDialog.hide();
	licenseDialog.center();
    }

    private Form createNewGroupForm() {
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
		setStyle("margin-left: 105px");
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

	licenseField = new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.DefaultLicense());
		setName(LICENSE_FIELD);
		setWidth(175);
		setReadOnly(true);
		setMsgTarget("side");
		setSelectOnFocus(false);
	    }
	});

	form.add(licenseField);

	form.addButton(new CustomButton(t.ChooseLicense(), new ClickListener() {
	    public void onClick(final Widget arg0) {
		showlicenseDialog();
	    }
	}).getButton());

	form.end();
	form.render();
	return form;
    }
}
