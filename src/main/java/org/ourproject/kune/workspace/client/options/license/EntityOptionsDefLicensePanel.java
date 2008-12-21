package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultFormUtils;
import org.ourproject.kune.workspace.client.options.EntityOptionsView;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
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
        super.setIconCls("k-copyleft-icon");
        super.setHeight(EntityOptionsView.HEIGHT);
        super.setFrame(true);
        super.getFormPanel().setButtonAlign(Position.LEFT);
        Label intro = new Label();
        intro.setHtml(i18n.t("This is the default license for all the contents of this group (you can also select another different license per content):")
                + DefaultFormUtils.brbr());

        licenseImage = new Image();
        Panel imagePanel = new Panel();
        imagePanel.setBorder(false);
        imagePanel.add(licenseImage);
        licenseImage.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                presenter.onLicenseClick();
            }
        });
        licenseImage.addStyleName("kune-pointer");

        Button change = new Button(i18n.t("Change"));
        change.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onChange();
            }
        });

        add(intro);
        add(new PaddedPanel(imagePanel, 0, 0, 0, 5));
        addButton(change);
    }

    public void openWindow(final String url) {

    }

    public void setLicense(LicenseDTO defaultLicense) {
        licenseImage.setUrl(defaultLicense.getImageUrl());
        KuneUiUtils.setQuickTip(licenseImage, defaultLicense.getLongName());
    }
}
