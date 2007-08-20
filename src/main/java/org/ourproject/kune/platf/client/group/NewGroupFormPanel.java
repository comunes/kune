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

    public NewGroupFormPanel() {
	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);

	newGroupForm = createNewGroupForm();
	generalVP.add(newGroupForm);
	generalVP.addStyleName("kune-Default-Form");

	clearData();
    }

    public void setLicense(final String longName) {
	// TODO Auto-generated method stub
    }

    public void clearData() {
	// TODO
	licenseField.reset();
    }

    public String getShortName() {
	return newGroupForm.findField(SHORTNAME_FIELD).getRawValue();
    }

    public String getLongName() {
	return newGroupForm.findField(LONGNAME_FIELD).getRawValue();
    }

    public String getPublicDesc() {
	return newGroupForm.findField(PUBLICDESC_FIELD).getRawValue();
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
		// Do nothing
	    }

	    public void onLicenseChange(LicenseDTO licenseDTO) {
		licenseField.setValue(licenseDTO.getLongName());
	    }
	});
	licenseDialog = new TwoButtonsDialog(t.SelectLicense(), t.ChooseLicense(), t.Cancel(), true, false, 350, 200,
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
		setButtonAlign("left");
	    }
	});

	form.add(new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.ShortName());
		setName(SHORTNAME_FIELD);
		setWidth(175);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	}));

	form.add(new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.LongName());
		setName(LONGNAME_FIELD);
		setWidth(300);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	}));

	form.add(new TextArea(new TextAreaConfig() {
	    {
		setFieldLabel(t.PublicDescription());
		setName(PUBLICDESC_FIELD);
		setWidth(300);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	}));

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
