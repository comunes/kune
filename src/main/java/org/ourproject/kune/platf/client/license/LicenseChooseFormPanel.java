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

public class LicenseChooseFormPanel extends Composite implements LicenseChooseFormView {

    private final LicenseChooseFormPresenter presenter;
    private final RadioButton ccRB;
    private final RadioButton allowModifRB;
    private final RadioButton commercialRB;
    private final RadioButton allowModifShareAlikeRB;
    private final ListBox otherLicenses;
    private final DeckPanel options;

    public LicenseChooseFormPanel(final LicenseChooseFormPresenter initPresenter, final List nonCCLicenses) {
	this.presenter = initPresenter;
	VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);
	VerticalPanel licenseTypesVP = new VerticalPanel();
	Translate t = Kune.getInstance().t;
	ccRB = new RadioButton("ccOrNot", t.CreativeCommons());
	RadioButton notCcRB = new RadioButton("ccOrNot", t.OtherLicenses());
	options = new DeckPanel();
	// ccIntro = new HTML("<p>" + t.CCExplainMessage() + "</p>", true);

	otherLicenses = new ListBox();
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

    public void showNotCCoptiones() {
	options.showWidget(1);
    }
}
