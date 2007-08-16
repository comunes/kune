package org.ourproject.kune.platf.client.group;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChangeListener;
import org.ourproject.kune.platf.client.license.LicenseChooseForm;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.services.Translate;
import org.ourproject.kune.platf.client.ui.dialogs.TwoButtonsDialog;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

import com.google.gwt.user.client.ui.Button;
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

    public NewGroupFormPanel() {

	// Intialize
	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);

	Translate t = Kune.getInstance().t;

	Button chooseLicense = new Button(t.ChooseLicense());

	newGroupForm = createNewGroupForm();
	generalVP.add(newGroupForm);

	// Set Properties
	chooseLicense.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		showlicenseDialog();
	    }
	});
	clearData();

    }

    public void setLicense(final String longName) {
	// TODO Auto-generated method stub
    }

    public void clearData() {
	// TODO
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

    private void showlicenseDialog() {
	final LicenseChooseForm license = SiteBarFactory.createLicenseChoose(new LicenseChangeListener() {
	    public void onCancel() {
		// FIXME
	    }

	    public void onLicenseChange(LicenseDTO licenseDTO) {
		// FIXME
	    }
	});
	licenseDialog = new TwoButtonsDialog(t.SelectLicense(), t.ChooseLicense(), t.Cancel(), true, false, 350, 200,
		350, 200, new FormListener() {
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
		setWidth(300);
		setLabelWidth(90);
	    }
	});

	// form.fieldset(t.SignIn());

	form.add(new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.ShortNameGroup());
		setName(SHORTNAME_FIELD);
		setWidth(175);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	}));

	form.add(new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.LongNameGroup());
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
		// setHideLabels(true);
		setLabelSeparator("");
	    }
	});

	form.add(new Radio(new CheckboxConfig() {
	    {
		setName(TYPEOFGROUP_FIELD);
		// setFieldLabel(t.TypeOfGroup());

		setBoxLabel(t.Organization());
		setAutoCreate(true);
	    }
	}));

	form.add(new Radio(new CheckboxConfig() {
	    {
		setName(TYPEOFGROUP_FIELD);
		setBoxLabel(t.Community());
		setAutoCreate(true);
	    }
	}));

	form.add(new Radio(new CheckboxConfig() {
	    {
		setName(TYPEOFGROUP_FIELD);
		setBoxLabel(t.Project());
		setAutoCreate(true);
	    }
	}));

	form.end();

	// form.add(new Field(new FieldConfig() {
	// {
	// setName(LICENSE_FIELD);
	// setFieldLabel(t.DefaultLicense());
	// }
	// }));

	form.end();
	form.render();
	return form;
    }
}
