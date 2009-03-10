package org.ourproject.kune.platf.client.ui.rte.edithtml;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.edithtml.editor.EditHtmlAgent;
import org.ourproject.kune.workspace.client.options.AbstractOptionsPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class EditHtmlPresenter extends AbstractOptionsPresenter implements EditHtml {

    private EditHtmlView view;
    private Listener<String> updateListener;
    private Listener0 cancelListener;
    private EditHtmlAgent agent;

    public EditHtmlPresenter() {
    }

    public String getHtml() {
        return agent.getHtml();
    }

    @Override
    public View getView() {
        return view;
    }

    public void init(EditHtmlView view) {
        super.init(view);
        this.view = view;
    }

    public void onCancel() {
        if (cancelListener != null) {
            cancelListener.onEvent();
        }
        view.hide();
    }

    public void onUpdate() {
        if (updateListener != null) {
            // FIXME
            updateListener.onEvent(agent.getHtml());
        }
        view.hide();
    }

    public void setAgent(EditHtmlAgent agent) {
        this.agent = agent;
    }

    public void setCancelListener(Listener0 cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setHtml(String html) {
        agent.setHtml(html);
    }

    public void setUpdateListener(Listener<String> updateListener) {
        this.updateListener = updateListener;
    }
}
