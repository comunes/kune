package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.site.Site;

public class EntityLogoSelectorPresenter implements EntityLogoSelector {

    private EntityLogoSelectorView view;
    private final Session session;
    private final EntityLogo entityLogo;

    public EntityLogoSelectorPresenter(Session session, EntityLogo entityLogo) {
        this.session = session;
        this.entityLogo = entityLogo;
    }

    public View getView() {
        return view;
    }

    public void init(EntityLogoSelectorView view) {
        this.view = view;
    }

    public void onCancel() {
        view.hide();
    }

    public void onSubmitComplete(int httpStatus, String responseText) {
        view.hide();
        entityLogo.reloadGroupLogo();
    }

    public void onSubmitFailed(int httpStatus, String responseText) {
        Site.error("Error setting the group logo");
    }

    public void show() {
        view.setUploadParams(session.getUserHash(), session.getCurrentStateToken().toString());
        view.show();
    }
}
