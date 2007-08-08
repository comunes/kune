package org.ourproject.kune.platf.client.group;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.services.Translate;

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

public class NewGroupPanel extends Composite implements NewGroupView {
    final private NewGroupPresenter presenter;
    private final TextBox shortNameGroup;
    private final TextBox longNameGroup;
    private final TextArea publicDesc;
    private final RadioButton typeOrg;
    private final RadioButton typeCommunity;
    private final RadioButton typeProject;
    private final Label licenseLabel;

    public NewGroupPanel(final NewGroupPresenter newGroupPresenter) {
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
	HorizontalPanel buttonsHP = new HorizontalPanel();
	Button create = new Button(t.Create());
	Button cancel = new Button(t.Cancel());

	// Layout
	generalVP.add(fieldGrid);
	licenseHP.add(chooseLicense);
	licenseHP.add(licenseLabel);
	typesVP.add(typeOrg);
	typesVP.add(typeCommunity);
	typesVP.add(typeProject);
	buttonsHP.add(create);
	buttonsHP.add(cancel);
	generalVP.add(buttonsHP);
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
		presenter.doChooseLicense();
	    }
	});
	clearData();
	create.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		// TODO group types, licenses
		presenter.doCreateNewGroup(shortNameGroup.getText(), longNameGroup.getText(), publicDesc.getText());
	    }
	});

	cancel.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.doCancel();
	    }
	});

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
}
