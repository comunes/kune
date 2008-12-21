package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.app.EntityOptionsGroup;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class EntityOptionsPanel extends AbstractOptionsPanel implements EntityOptionsView {

    class EntityOptionPushButton extends IconLabel implements View {
        public EntityOptionPushButton(String text, AbstractImagePrototype icon) {
            super(text, icon, false);
        }
    }
    public static final String ENTITY_OP_PANEL_ID = "k-eop-diag";
    public static final String GROUP_OPTIONS_ERROR_ID = "k-eop-err-mess";
    public static final String GROUP_OPTIONS_ICON = "k-eop-icon";
    private final I18nTranslationService i18n;
    private final EntityHeader entityHeader;
    private EntityOptionPushButton optionsButton;
    private final Images images;

    private final EntityOptionsGroup entityPreferencesGroup;

    public EntityOptionsPanel(final EntityOptions presenter, final EntityHeader entityHeader,
            I18nTranslationService i18n, Images images, EntityOptionsGroup entityOptionsGroup) {
        super(ENTITY_OP_PANEL_ID, "", 400, HEIGHT + 80, 400, HEIGHT + 80, false, images, GROUP_OPTIONS_ERROR_ID);
        this.entityHeader = entityHeader;
        this.i18n = i18n;
        this.images = images;
        this.entityPreferencesGroup = entityOptionsGroup;
        super.setIconCls("k-options-icon");
        createOptionsButton();
    }

    @Override
    public void createAndShow() {
        entityPreferencesGroup.createAll();
        super.createAndShow();
    }

    public void setButtonVisible(boolean visible) {
        optionsButton.setVisible(visible);
    }

    public void setGroupTitle() {
        super.setTitle(i18n.t(PlatfMessages.ENT_OPTIONS_GROUP_TITLE));
        optionsButton.setText(i18n.t(PlatfMessages.ENT_OPTIONS_GROUP_TITLE));
    }

    public void setPersonalTitle() {
        super.setTitle(i18n.t(PlatfMessages.ENT_OPTIONS_USER_TITLE));
        optionsButton.setText(i18n.t(PlatfMessages.ENT_OPTIONS_USER_TITLE));
    }

    private void createOptionsButton() {
        optionsButton = new EntityOptionPushButton("", images.emblemSystem());
        optionsButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                createAndShow();
            }
        });
        optionsButton.ensureDebugId(GROUP_OPTIONS_ICON);
        optionsButton.addStyleName("kune-Margin-Medium-t");
        optionsButton.addStyleName("kune-pointer");

        entityHeader.addWidget(optionsButton);
    }
}
