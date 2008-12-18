package org.ourproject.kune.workspace.client.licensewizard.pages;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.WindowUtils;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class LicenseWizardFlags extends Panel {
    private final IconLabel copyleft;
    private final IconLabel nonCopyleft;
    private final IconLabel culturalWorks;
    private final IconLabel nonCulturalWorks;
    private final IconLabel nonCommercialReasons;

    public LicenseWizardFlags(Images images, I18nTranslationService i18n) {
        copyleft = new IconLabel(images.copyleft(), i18n.t("This is a copyleft license."));
        nonCopyleft = new IconLabel(images.noCopyleft(), i18n.t("This is not a copyleft license."));
        culturalWorks = new IconLabel(images.info(), i18n.t("This is appropriate for free cultural works."));
        nonCulturalWorks = new IconLabel(images.alert(), i18n.t("This is not appropriate for free cultural works."));
        nonCommercialReasons = new IconLabel(images.alert(), i18n.t("Reasons not to use a non commercial license."));
        addLink(copyleft, "http://en.wikipedia.org/wiki/Copyleft");
        addLink(nonCopyleft, "http://en.wikipedia.org/wiki/Copyleft");
        addLink(culturalWorks, "http://freedomdefined.org/");
        addLink(nonCulturalWorks, "http://freedomdefined.org/");
        addLink(nonCommercialReasons, "http://freedomdefined.org/Licenses/NC");
        add(copyleft);
        add(nonCopyleft);
        add(culturalWorks);
        add(nonCulturalWorks);
        add(nonCommercialReasons);
    }

    public void setCopyleft(boolean isCopyleft) {
        copyleft.setVisible(isCopyleft);
        nonCopyleft.setVisible(!isCopyleft);
    }

    public void setCulturalWorks(boolean isAppropiateForCulturalWorks) {
        culturalWorks.setVisible(isAppropiateForCulturalWorks);
        nonCulturalWorks.setVisible(!isAppropiateForCulturalWorks);
    }

    public void setNonComercial(boolean isNonComercial) {
        nonCommercialReasons.setVisible(isNonComercial);
    }

    public void setVisible(boolean isCopyleft, boolean isAppropiateForCulturalWorks, boolean isNonComercial) {
        setCopyleft(isCopyleft);
        setCulturalWorks(isAppropiateForCulturalWorks);
        setNonComercial(isNonComercial);
    }

    private void addLink(IconLabel label, final String url) {
        label.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                openWindow(url);
            }

            private void openWindow(String url) {
                WindowUtils.open(url);
            }
        });
        label.setStyleNameToText("k-info-links");
    }
}
