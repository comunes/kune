package org.ourproject.kune.workspace.client.licensechoose;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.HorizontalLine;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.TitledPanel;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LicenseChoosePanel extends Composite implements LicenseChooseView {

    protected static final String CC_LIC_FIELD = "cc-lic";
    protected static final String OTHER_LIC_FIELD = "other-lic";

    private final RadioButton ccRB;
    private final RadioButton allowModifRB;
    private final RadioButton commercialRB;
    private final RadioButton allowModifShareAlikeRB;
    private final DeckPanel options;
    private final Label ccIntro;
    private final ListBox otherLicenses;
    private final TitledPanel optionsBox;
    private final IconLabel copyleft;
    private final IconLabel nonCopyleft;
    private final RadioButton noModifRB;
    private final RadioButton nonCommercialRB;

    public LicenseChoosePanel(final List<LicenseDTO> nonCCLicenses, final LicenseChoosePresenter presenter) {
        Images img = Images.App.getInstance();

        VerticalPanel generalVP = new VerticalPanel();
        initWidget(generalVP);

        VerticalPanel licenseTypesVP = new VerticalPanel();
        ccRB = new RadioButton("ccOrNot", Kune.I18N.t("Creative Commons"));
        RadioButton notCcRB = new RadioButton("ccOrNot", Kune.I18N.t("Others licenses"));
        options = new DeckPanel();
        ccIntro = new Label(
                Kune.I18N
                        .t("With a Creative Commons license, you keep your copyright but allow people to copy and distribute your work provided they give you credit — and only on the conditions you specify here. What do you want to do?"));

        otherLicenses = new ListBox();
        VerticalPanel ccOptionsVP = new VerticalPanel();
        VerticalPanel nonCcOptionsVP = new VerticalPanel();

        Label comercialLabel = new Label(Kune.I18N.t("Allow commercial uses of your work?"));
        commercialRB = new RadioButton("comercial", Kune.I18N.t("Yes"));
        nonCommercialRB = new RadioButton("comercial", Kune.I18N.t("No"));
        Label allowModifLabel = new Label(Kune.I18N.t("Allow modifications of your work?"));
        allowModifRB = new RadioButton("allowModif", Kune.I18N.t("Yes"));
        allowModifShareAlikeRB = new RadioButton("allowModif", Kune.I18N.t("Yes, as long as others share alike"));
        noModifRB = new RadioButton("allowModif", Kune.I18N.t("No"));
        generalVP.add(licenseTypesVP);
        licenseTypesVP.add(ccRB);
        licenseTypesVP.add(notCcRB);

        copyleft = new IconLabel(img.copyleft(), Kune.I18N.t("This is a copyleft license"), false);
        nonCopyleft = new IconLabel(img.noCopyleft(), Kune.I18N.t("This is not a copyleft license"), false);
        nonCopyleft.setVisible(false);

        generalVP.add(options);

        // Options

        optionsBox = new TitledPanel(Kune.I18N.t("Options"), options);
        generalVP.add(optionsBox);
        generalVP.add(nonCopyleft);
        generalVP.add(copyleft);

        ccOptionsVP.add(ccIntro);
        ccOptionsVP.add(comercialLabel);
        ccOptionsVP.add(commercialRB);
        ccOptionsVP.add(nonCommercialRB);
        ccOptionsVP.add(new HorizontalLine());
        ccOptionsVP.add(allowModifLabel);
        ccOptionsVP.add(allowModifRB);
        ccOptionsVP.add(allowModifShareAlikeRB);
        ccOptionsVP.add(noModifRB);

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
        optionsBox.addStyleName("kune-Margin-Medium-t");
        otherLicenses.addStyleName("kune-Margin-Large-trbl");
        generalVP.addStyleName("kune-Default-Form");
        copyleft.addStyleName("kune-Margin-Medium-t");
        nonCopyleft.addStyleName("kune-Margin-Medium-t");

        ccRB.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                showCCoptions();
                presenter.onChange();
            }
        });

        notCcRB.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                showNotCCoptions();
                presenter.onChange();
            }
        });

        for (int i = 0; i < nonCCLicenses.size(); i++) {
            String licenseDescrip = nonCCLicenses.get(i).getLongName();
            otherLicenses.addItem(licenseDescrip);
        }
        if (nonCCLicenses.size() > 0) {
            otherLicenses.setItemSelected(0, true);
            otherLicenses.setVisibleItemCount(1);
        }

        configureListeners(presenter);
    }

    private void configureListeners(final LicenseChoosePresenter presenter) {
        ChangeListener changeListener = new ChangeListener() {
            public void onChange(final Widget arg0) {
                presenter.onChange();
            }
        };

        ClickListener clickListener = new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.onChange();
            }
        };
        otherLicenses.addChangeListener(changeListener);
        ccRB.addClickListener(clickListener);
        allowModifRB.addClickListener(clickListener);
        commercialRB.addClickListener(clickListener);
        nonCommercialRB.addClickListener(clickListener);
        allowModifShareAlikeRB.addClickListener(clickListener);
        noModifRB.addClickListener(clickListener);
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
        optionsBox.setTitle(Kune.I18N.t("Options"));

    }

    public void showNotCCoptions() {
        options.showWidget(1);
        optionsBox.setTitle(Kune.I18N.t("Select one of these licenses:"));
    }

    public void showIsCopyleft() {
        nonCopyleft.setVisible(false);
        copyleft.setVisible(true);
    }

    public void showIsNotCopyleft() {
        nonCopyleft.setVisible(true);
        copyleft.setVisible(false);
    }
}
