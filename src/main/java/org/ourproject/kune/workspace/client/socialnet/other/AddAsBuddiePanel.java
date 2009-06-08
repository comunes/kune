package org.ourproject.kune.workspace.client.socialnet.other;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class AddAsBuddiePanel implements AddAsBuddieView {

    public static final String ADD_BUDDIE_BUTTON = "k-aabp-bt";
    private final EntityHeaderButton button;

    public AddAsBuddiePanel(final AddAsBuddiePresenter presenter, final EntityHeader entityHeader, final Images images,
            final I18nTranslationService i18n) {
        button = new EntityHeaderButton(images.addGreen(), i18n.t("Add as a buddie"));
        button.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                presenter.onAdd();
            }
        });
        button.ensureDebugId(ADD_BUDDIE_BUTTON);
        button.addStyleName("kune-Margin-Medium-t");
        button.addStyleName("kune-pointer");
        entityHeader.addWidget(button);
    }

    public void setVisible(final boolean visible) {
        button.setVisible(visible);
    }
}
