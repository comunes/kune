package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;

import com.google.gwt.user.client.ui.VerticalPanel;

public class TestRTEDialog {

    public TestRTEDialog(RTEditor editor) {
        BasicDialog dialog = new BasicDialog("Testing RTE", false, false, 400, 200);
        VerticalPanel vp = new VerticalPanel();

        vp.add(((ActionToolbarPanel<Object>) editor.getTopBar().getView()).getToolbar(ActionToolbarPosition.topbar));
        vp.add(((ActionToolbarPanel<Object>) editor.getSndBar().getView()).getToolbar(ActionToolbarPosition.topbar));
        vp.add(((RTEditorPanel) editor.getEditorArea()).getRTE());
        dialog.add(vp);
        dialog.show();
    }
}
