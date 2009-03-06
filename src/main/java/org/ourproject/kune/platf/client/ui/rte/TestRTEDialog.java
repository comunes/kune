package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

public class TestRTEDialog {

    private final RTEditor editor;
    private final BasicDialog dialog;

    public TestRTEDialog(RTEditor editor) {
        this.editor = editor;
        dialog = new BasicDialog("Testing RTE", false, false, 650, 200);
        final VerticalPanel vp = new VerticalPanel();

        vp.add(((ActionToolbarPanel<Object>) editor.getTopBar().getView()).getToolbar(ActionToolbarPosition.topbar));
        vp.add(((ActionToolbarPanel<Object>) editor.getSndBar().getView()).getToolbar(ActionToolbarPosition.topbar));
        final RTEditorPanel editorPanel = (RTEditorPanel) editor.getEditorArea();
        vp.add(editorPanel.getRTE());
        editor.setExtended(true);
        vp.setWidth("100%");
        dialog.add(vp);
        dialog.addListener(new WindowListenerAdapter() {
            @Override
            public void onResize(Window source, int width, int height) {
                int newHeight = height - 26 * 2 - 40;
                editorPanel.adjustSize(newHeight);
                vp.setCellHeight(editorPanel.getRTE(), "" + newHeight);
            }
        });
    }

    public void setExtended(boolean extended) {
        editor.setExtended(extended);
    }

    public void show() {
        editor.attach();
        dialog.show();
    }
}
