package org.ourproject.kune.workspace.client.nohomepage;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.EntityWorkspace;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Label;

public class NoHomePagePanel implements NoHomePageView {

    private final Label noHomepageCtnLabel;
    private final Label noHomepageCtxLabel;
    private final WorkspaceSkeleton ws;

    public NoHomePagePanel(final NoHomePagePresenter presenter, final WorkspaceSkeleton ws, I18nTranslationService i18n) {
        this.ws = ws;
        noHomepageCtnLabel = new Label(i18n.t("This user don't have a homepage"));
        noHomepageCtnLabel.setStyleName("kune-Content-Main");
        noHomepageCtnLabel.addStyleName("kune-Margin-7-trbl");
        noHomepageCtxLabel = new Label("");
    }

    public void clearWs() {
        EntityWorkspace ew = ws.getEntityWorkspace();
        ew.setContent(noHomepageCtnLabel);
        ew.setContext(noHomepageCtxLabel);
        ew.getContentTopBar().removeAll();
        ew.getContextTopBar().removeAll();
        ew.getContentBottomBar().removeAll();
        ew.getContextBottomBar().removeAll();
    }
}
