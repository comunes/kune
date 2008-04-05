package org.ourproject.kune.workspace.client.licensefoot.ui;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.licensefoot.LicensePresenter;
import org.ourproject.kune.workspace.client.licensefoot.LicenseView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LicensePanel extends HorizontalPanel implements LicenseView {
    private final Label copyright;
    private final Image image;
    private final Label license;

    public LicensePanel(final LicensePresenter presenter) {
        copyright = new Label();
        image = new Image();
        license = new Label();
        this.add(copyright);
        this.add(license);
        this.add(image);
        ClickListener clickListener = new ClickListener() {
            public void onClick(Widget arg0) {
                presenter.onLicenseClick();
            }
        };

        license.addClickListener(clickListener);
        image.addClickListener(clickListener);
        copyright.setVisible(false);
        license.setVisible(false);
        image.setVisible(false);

        copyright.addStyleName("kune-Margin-Large-l");
        license.setStyleName("kune-LicensePanel-licensetext");
    }

    public void showLicense(final String groupName, final LicenseDTO licenseDTO) {
        copyright.setText(Kune.I18N.t("© [%s], under license: ", groupName));
        license.setText(licenseDTO.getLongName());
        copyright.setVisible(true);
        license.setVisible(true);
        image.setVisible(true);
        image.setUrl(licenseDTO.getImageUrl());
    }

    public void openWindow(final String url) {
        Window.open(url, "_blank", "");
    }
}
