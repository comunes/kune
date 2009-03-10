package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

public class TestRTEDialog {

    private final RTESavingEditor editor;
    private final BasicDialog dialog;

    public TestRTEDialog(RTESavingEditor editor) {
        this.editor = editor;
        dialog = new BasicDialog("Testing RTE", false, false, 650, 200);
        final VerticalPanel vp = new VerticalPanel();

        RTEditor basicEditor = editor.getBasicEditor();
        vp.add((Widget) ((ActionToolbarPanel<Object>) basicEditor.getTopBar().getView()).getToolbar());
        vp.add((Widget) ((ActionToolbarPanel<Object>) basicEditor.getSndBar().getView()).getToolbar());
        final RTEditorPanel editorPanel = (RTEditorPanel) basicEditor.getEditorArea();
        vp.add(editorPanel.getRTE());
        basicEditor.setExtended(true);
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
        editor.getBasicEditor().setExtended(extended);
    }

    public void show() {
        editor.getBasicEditor().attach();
        dialog.show();
    }
}
