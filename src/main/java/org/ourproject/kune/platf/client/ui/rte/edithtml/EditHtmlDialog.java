package org.ourproject.kune.platf.client.ui.rte.edithtml;

import org.ourproject.kune.workspace.client.options.AbstractOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface EditHtmlDialog extends AbstractOptions {

    String getHtml();

    void setAgent(EditHtmlAgent agent);

    void setCancelListener(Listener0 cancelListener);

    void setHtml(String html);

    void setUpdateListener(Listener<String> updateListener);

}
