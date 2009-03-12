package org.ourproject.kune.workspace.client.entityheader.maxmin;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderButton;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class MaxMinWorkspacePanel implements MaxMinWorkspaceView {

    public static final String MIN_ICON = "mmwp-min_bt";
    public static final String MAX_ICON = "mmwp-max_bt";
    private final WorkspaceSkeleton ws;
    private final EntityHeaderButton maxBtn;
    private final EntityHeaderButton minBtn;

    public MaxMinWorkspacePanel(final MaxMinWorkspacePresenter presenter, WorkspaceSkeleton ws, Images images,
            final EntityHeader entityHeader, I18nTranslationService i18n) {
        this.ws = ws;
        maxBtn = new EntityHeaderButton("", images.arrowOut());
        minBtn = new EntityHeaderButton("", images.arrowIn());
        maxBtn.setTitle(i18n.t("Maximize the workspace"));
        minBtn.setTitle(i18n.t("Minimize the workspace"));
        maxBtn.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                presenter.onMaximize();
            }
        });
        minBtn.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                presenter.onMinimize();
            }
        });
        minBtn.setVisible(false);
        minBtn.ensureDebugId(MIN_ICON);
        maxBtn.ensureDebugId(MAX_ICON);
        entityHeader.addWidget(maxBtn);
        entityHeader.addWidget(minBtn);
    }

    public void setMaximized(boolean maximized) {
        ws.setMaximized(maximized);
    }

    public void showMaxButton(boolean showMax) {
        maxBtn.setVisible(showMax);
        minBtn.setVisible(!showMax);
    }

}
