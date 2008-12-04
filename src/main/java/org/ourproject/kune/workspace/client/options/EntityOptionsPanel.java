package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.app.EntityOptionsGroup;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class EntityOptionsPanel extends AbstractOptionsPanel implements EntityOptionsView {

    class EntityOptionPushButton extends IconLabel implements View {
        public EntityOptionPushButton(String text, AbstractImagePrototype icon) {
            super(text, icon, false);
        }
    }
    public static final String GROUP_OPTIONS_ERROR_ID = "k-gop-err-mess";
    public static final String GROUP_OPTIONS_ID = "k-gop-panel";
    public static final String GROUP_OPTIONS_ICON = "k-gop-icon";
    private final I18nTranslationService i18n;
    private final EntityHeader entityHeader;
    private EntityOptionPushButton optionsButton;
    private final Images images;

    private final EntityOptionsGroup entityPreferencesGroup;

    public EntityOptionsPanel(final EntityOptionsPresenter presenter, final EntityHeader entityHeader,
            I18nTranslationService i18n, Images images, EntityOptionsGroup entityOptionsGroup) {
        super("", 400, 250, 400, 250, images, GROUP_OPTIONS_ID, GROUP_OPTIONS_ERROR_ID);
        this.entityHeader = entityHeader;
        this.i18n = i18n;
        this.images = images;
        this.entityPreferencesGroup = entityOptionsGroup;
        super.setIconCls("k-options-icon");
        createOptionsButton();
    }

    public void addOptionTab(View view) {
        if (view instanceof Panel) {
            super.addTab((Panel) view);
        } else if (view instanceof DefaultForm) {
            super.addTab(((DefaultForm) view).getForm());
        } else {
            Log.error("Programatic error: Unexpected element added to GroupOptions");
        }
        doLayoutIfNeeded();
    }

    public void createAndShow() {
        entityPreferencesGroup.createAll();
        show();
        setFirstTabActive();
    }

    public void setButtonVisible(boolean visible) {
        optionsButton.setVisible(visible);
    }

    public void setGroupTitle() {
        super.setTitle(i18n.t(PlatfMessages.ENT_OPTIONS_GROUP_TITLE));
        optionsButton.setText(i18n.t(PlatfMessages.ENT_OPTIONS_GROUP_TITLE));
        // optionsButton.setTitle(i18n.t("Configure this group preferences"));
    }

    public void setPersonalTitle() {
        super.setTitle(i18n.t(PlatfMessages.ENT_OPTIONS_USER_TITLE));
        optionsButton.setText(i18n.t(PlatfMessages.ENT_OPTIONS_USER_TITLE));
        // optionsButton.setTitle(i18n.t("Configure your preferences"));
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
