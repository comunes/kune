package org.ourproject.kune.platf.client.group;

import org.ourproject.kune.platf.client.dto.GroupDTO;
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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NewGroupFormPanel extends Composite implements NewGroupFormView {
    private static final Translate t = Kune.getInstance().t;
    final private NewGroupFormPresenter presenter;
    private final TextBox shortNameGroup;
    private final TextBox longNameGroup;
    private final TextArea publicDesc;
    private final RadioButton typeOrg;
    private final RadioButton typeCommunity;
    private final RadioButton typeProject;
    private final Label licenseLabel;
    private TwoButtonsDialog licenseDialog;

    public NewGroupFormPanel(final NewGroupFormPresenter newGroupPresenter) {

	// Intialize
	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);
	this.presenter = newGroupPresenter;
	Translate t = Kune.getInstance().t;
	Grid fieldGrid = new Grid(5, 2);
	shortNameGroup = new TextBox();
	longNameGroup = new TextBox();
	publicDesc = new TextArea();
	HorizontalPanel licenseHP = new HorizontalPanel();
	licenseLabel = new Label();
	Button chooseLicense = new Button(t.ChooseLicense());
	VerticalPanel typesVP = new VerticalPanel();
	typeOrg = new RadioButton("typeGroup", t.Organization());
	typeCommunity = new RadioButton("typeGroup", t.Community());
	typeProject = new RadioButton("typeGroup", t.Project());

	// Layout
	generalVP.add(fieldGrid);
	licenseHP.add(chooseLicense);
	licenseHP.add(licenseLabel);
	typesVP.add(typeOrg);
	typesVP.add(typeCommunity);
	typesVP.add(typeProject);
	fieldGrid.setWidget(0, 0, new Label(t.ShortNameGroup()));
	fieldGrid.setWidget(1, 0, new Label(t.LongNameGroup()));
	fieldGrid.setWidget(2, 0, new Label(t.PublicDescription()));
	fieldGrid.setWidget(3, 0, new Label(t.DefaultLicense()));
	fieldGrid.setWidget(4, 0, new Label(t.TypeOfGroup()));
	fieldGrid.setWidget(0, 1, shortNameGroup);
	fieldGrid.setWidget(1, 1, longNameGroup);
	fieldGrid.setWidget(2, 1, publicDesc);
	fieldGrid.setWidget(3, 1, licenseHP);
	fieldGrid.setWidget(4, 1, typesVP);

	// Set Properties
	chooseLicense.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		showlicenseDialog();
	    }
	});
	clearData();

	typeOrg.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.selectType(GroupDTO.TYPE_ORGANIZATION);
	    }
	});

	typeCommunity.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.selectType(GroupDTO.TYPE_COMNUNITY);
	    }
	});

	typeProject.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.selectType(GroupDTO.TYPE_PROJECT);
	    }
	});
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

    public void clearData() {
	shortNameGroup.setText("");
	longNameGroup.setText("");
	publicDesc.setText("");
	licenseLabel.setText("");
	typeOrg.setChecked(true);
    }

    public void setLicense(final String longName) {
	licenseLabel.setText(longName);
    }

    public String getLongName() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getPublicDesc() {
	// TODO Auto-generated method stub
	return null;
    }

    public String getShortName() {
	// TODO Auto-generated method stub
	return null;
    }
}
