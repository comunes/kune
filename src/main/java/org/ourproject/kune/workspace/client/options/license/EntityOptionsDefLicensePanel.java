package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Image;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Label;

public class EntityOptionsDefLicensePanel extends DefaultForm implements EntityOptionsDefLicenseView {

    private final Image licenseImage;

    public EntityOptionsDefLicensePanel(final EntityOptionsDefLicensePresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n) {
        super(i18n.t("License"));
        super.setAutoWidth(true);
        super.setAutoHeight(true);
        Label intro = new Label();
        intro.setHtml(i18n.t("This is the default license of all the contents of this group (you can also select another different license per content).")
                + DefaultFormUtils.brbr());

        licenseImage = new Image();
        Panel imagePanel = new Panel();
        imagePanel.setBorder(false);
        imagePanel.add(licenseImage);

        Button change = new Button(i18n.t("Change"));
        change.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onChange();
            }
        });

        add(intro);
        add(new PaddedPanel(imagePanel, 0, 110, 0, 5));
        addButton(change);
    }

    public void setLicense(LicenseDTO defaultLicense) {
        licenseImage.setUrl(defaultLicense.getImageUrl());
        KuneUiUtils.setQuickTip(licenseImage, defaultLicense.getLongName());
    }
}
