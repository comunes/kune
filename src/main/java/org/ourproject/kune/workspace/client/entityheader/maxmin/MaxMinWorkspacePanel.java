package org.ourproject.kune.workspace.client.entityheader.maxmin;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderButton;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class MaxMinWorkspacePanel implements MaxMinWorkspaceView {

    public static final String MIN_ICON = "mmwp-min_bt";
    public static final String MAX_ICON = "mmwp-max_bt";
    private final WorkspaceSkeleton ws;
    private final EntityHeaderButton maxBtn;
    private final EntityHeaderButton minBtn;

    public MaxMinWorkspacePanel(final MaxMinWorkspacePresenter presenter, final WorkspaceSkeleton ws,
            final Images images, final EntityHeader entityHeader, final I18nTranslationService i18n) {
        this.ws = ws;
        maxBtn = new EntityHeaderButton("", images.arrowOut());
        minBtn = new EntityHeaderButton("", images.arrowIn());
        maxBtn.setTitle(i18n.t("Maximize the workspace"));
        minBtn.setTitle(i18n.t("Minimize the workspace"));
        maxBtn.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                presenter.onMaximize();
            }
        });
        minBtn.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                presenter.onMinimize();
            }
        });
        minBtn.setVisible(false);
        minBtn.ensureDebugId(MIN_ICON);
        maxBtn.ensureDebugId(MAX_ICON);
        entityHeader.addWidget(maxBtn);
        entityHeader.addWidget(minBtn);
    }

    public void setMaximized(final boolean maximized) {
        ws.setMaximized(maximized);
    }

    public void showMaxButton(final boolean showMax) {
        maxBtn.setVisible(showMax);
        minBtn.setVisible(!showMax);
    }

}
