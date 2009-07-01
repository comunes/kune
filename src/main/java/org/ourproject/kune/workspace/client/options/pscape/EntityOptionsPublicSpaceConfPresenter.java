package org.ourproject.kune.workspace.client.options.pscape;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.options.EntityOptions;

public abstract class EntityOptionsPublicSpaceConfPresenter {
    private EntityOptionsPublicSpaceConfView view;
    private final EntityOptions entityOptions;

    public EntityOptionsPublicSpaceConfPresenter(final EntityOptions entityOptions) {
        this.entityOptions = entityOptions;
    }

    public View getView() {
        return view;
    }

    public void init(final EntityOptionsPublicSpaceConfView view) {
        this.view = view;
        entityOptions.addTab(view);
    }

}
