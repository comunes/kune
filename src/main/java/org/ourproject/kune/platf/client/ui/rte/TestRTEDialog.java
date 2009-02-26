package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;

public class TestRTEDialog {

    public TestRTEDialog(RTEditor editor) {
        BasicDialog dialog = new BasicDialog("Testing RTE", false);
        dialog.add(((RTEditorPanel) editor.getView()).getRTE());
        dialog.show();
    }
}
