package org.ourproject.kune.workspace.client.options.pscape;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.options.EntityOptions;

public class EntityOptionsPublicSpaceConfPresenter implements EntityOptionsPublicSpaceConf {

    private EntityOptionsPublicSpaceConfView view;
    private final EntityOptions entityOptions;

    public EntityOptionsPublicSpaceConfPresenter(EntityOptions entityOptions) {
        this.entityOptions = entityOptions;
    }

    public View getView() {
        return view;
    }

    public void init(EntityOptionsPublicSpaceConfView view) {
        this.view = view;
        entityOptions.addOptionTab(view);
    }
}
