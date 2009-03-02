package org.ourproject.kune.workspace.client.socialnet.other;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class AddAsBuddiePanel implements AddAsBuddieView {

    public static final String ADD_BUDDIE_BUTTON = "k-aabp-bt";
    private final EntityHeaderButton button;

    public AddAsBuddiePanel(final AddAsBuddiePresenter presenter, final EntityHeader entityHeader, Images images,
            I18nTranslationService i18n) {
        button = new EntityHeaderButton(images.addGreen(), i18n.t("Add as a buddie"));
        button.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                presenter.onAdd();
            }
        });
        button.ensureDebugId(ADD_BUDDIE_BUTTON);
        button.addStyleName("kune-Margin-Medium-t");
        button.addStyleName("kune-pointer");
        entityHeader.addWidget(button);
    }

    public void setVisible(boolean visible) {
        button.setVisible(visible);
    }
}
