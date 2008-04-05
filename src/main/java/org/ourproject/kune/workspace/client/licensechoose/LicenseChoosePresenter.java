package org.ourproject.kune.workspace.client.licensechoose;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public class LicenseChoosePresenter implements LicenseChoose, View {

    private LicenseChooseView view;

    private List<LicenseDTO> licenses;

    private List<LicenseDTO> licensesNonCCList;

    public LicenseChoosePresenter() {
    }

    public void init(final LicenseChooseView view, final List<LicenseDTO> licenses,
            final List<LicenseDTO> licensesNonCCList) {
        this.view = view;
        this.licenses = licenses;
        this.licensesNonCCList = licensesNonCCList;
        this.view.reset();
    }

    public LicenseDTO getLicense() {
        String licenseShortName;

        if (view.isCCselected()) {
            if (view.permitComercial()) {
                licenseShortName = view.isAllowModif() ? "by" : view.isAllowModifShareAlike() ? "by-sa" : "by-nd";
            } else {
                licenseShortName = view.isAllowModif() ? "by-nc" : view.isAllowModifShareAlike() ? "by-nc-sa"
                        : "by-nc-nd";
            }
        } else {
            licenseShortName = (licensesNonCCList.get(view.getSelectedNonCCLicenseIndex())).getShortName();
        }
        return getLicenseFromShortName(licenseShortName);
    }

    private LicenseDTO getLicenseFromShortName(final String shortName) {
        for (int i = 0; i < licenses.size(); i++) {
            LicenseDTO licenseDTO = licenses.get(i);
            if (licenseDTO.getShortName() == shortName) {
                return licenseDTO;
            }
        }
        throw new IndexOutOfBoundsException("License not found");
    }

    public View getView() {
        return view;
    }

    public void onChange() {
        LicenseDTO licenseDTO = getLicense();
        if (licenseDTO.isCopyleft()) {
            view.showIsCopyleft();
        } else {
            view.showIsNotCopyleft();
        }
    }

}
