package org.ourproject.kune.platf.client.ui.rte.edithtml;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class EditHtmlDialogPresenter extends AbstractTabbedDialogPresenter implements EditHtmlDialog {

    private Listener<String> updateListener;
    private Listener0 cancelListener;
    private EditHtmlAgent agent;

    public EditHtmlDialogPresenter() {
    }

    public String getHtml() {
        return agent.getHtml();
    }

    public void onCancel() {
        if (cancelListener != null) {
            cancelListener.onEvent();
        }
        hide();
    }

    public void onUpdate() {
        if (updateListener != null) {
            // FIXME
            updateListener.onEvent(agent.getHtml());
        }
        hide();
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
