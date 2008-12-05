package org.ourproject.kune.workspace.client.cnt;

public abstract class AbstractContentPresenter {

    protected AbstractContentView view;

    public AbstractContentPresenter() {
    }

    public void attach() {
        view.attach();
    }

    public void detach() {
        view.detach();
    }

    public void init(final AbstractContentView view) {
        this.view = view;
    }

}
