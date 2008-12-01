package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.app.EntityOptionsGroup;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.workspace.client.skel.SimpleToolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class EntityOptionsPanel extends AbstractOptionsPanel implements EntityOptionsView {

    public static final String GROUP_OPTIONS_ERROR_ID = "gop-err-mess";
    private final I18nTranslationService i18n;
    private final WorkspaceSkeleton ws;
    private PushButton optionsButton;
    private final Images images;
    private final EntityOptionsGroup entityPreferencesGroup;

    public EntityOptionsPanel(final EntityOptionsPresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n, Images images, EntityOptionsGroup entityOptionsGroup) {
        super(i18n.t("Group options"), 400, 250, 400, 250, images, GROUP_OPTIONS_ERROR_ID);
        this.ws = ws;
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

    public void setButtonVisible(boolean visible) {
        optionsButton.setVisible(visible);
    }

    private void createOptionsButton() {
        optionsButton = new PushButton(images.emblemSystem().createImage(), new ClickListener() {
            public void onClick(Widget arg0) {
                if (isVisible()) {
                    hide();
                } else {
                    entityPreferencesGroup.createAll();
                    show();
                    setFirstTabActive();
                }
            }
        });
        KuneUiUtils.setQuickTip(optionsButton, i18n.t("Show/hide the group options dialog"));

        optionsButton.addStyleName("kune-Margin-Medium-r");
        final SimpleToolbar wsTitle = ws.getEntityWorkspace().getTitleComponent();
        wsTitle.add(optionsButton);
    }
}
