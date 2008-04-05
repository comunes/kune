
package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.gwtext.client.widgets.MessageBox;

public class TextEditorPanel extends Composite implements TextEditorView {
    private static final String BACKCOLOR_ENABLED = "#FFF";
    private static final String BACKCOLOR_DISABLED = "#CCC";
    private final RichTextArea gwtRTarea;
    private final TextEditorToolbar textEditorToolbar;
    private final TextEditorPresenter presenter;
    private final Timer saveTimer;

    public TextEditorPanel(final TextEditorPresenter presenter) {

        this.presenter = presenter;
        gwtRTarea = new RichTextArea();
        textEditorToolbar = new TextEditorToolbar(gwtRTarea, presenter);
        initWidget(gwtRTarea);

        gwtRTarea.setWidth("97%");
        gwtRTarea.addStyleName("kune-TexEditorPanel-TextArea");
        adjustSize("" + (Window.getClientHeight() - 265));
        Window.addWindowResizeListener(new WindowResizeListener() {
            public void onWindowResized(final int arg0, final int arg1) {
                adjustSize("" + (Window.getClientHeight() - 265));
            }
        });
        saveTimer = new Timer() {
            public void run() {
                presenter.onSave();
            }
        };
    }

    public void scheduleSave(final int delayMillis) {
        saveTimer.schedule(delayMillis);
    }

    public void saveTimerCancel() {
        saveTimer.cancel();
    }

    public void setEnabled(final boolean enabled) {
        final String bgColor = enabled ? BACKCOLOR_ENABLED : BACKCOLOR_DISABLED;
        DOM.setStyleAttribute(gwtRTarea.getElement(), "backgroundColor", bgColor);
        gwtRTarea.setEnabled(enabled);
    }

    public String getHTML() {
        return gwtRTarea.getHTML();
    }

    public String getText() {
        return gwtRTarea.getText();
    }

    public void setHTML(final String html) {
        gwtRTarea.setHTML(html);
    }

    public void setText(final String text) {
        gwtRTarea.setText(text);
    }

    public void setHeight(final String height) {
        gwtRTarea.setHeight(height);
    }

    public void setEnabledSaveButton(final boolean enabled) {
        textEditorToolbar.setEnabledSaveButton(enabled);
    }

    public void setEnabledCancelButton(final boolean enabled) {
        textEditorToolbar.setEnabledCloseButton(enabled);
    }

    public void setTextSaveButton(final String text) {
        textEditorToolbar.setTextSaveButton(text);
    }

    public void editHTML(final boolean edit) {
        textEditorToolbar.editHTML(edit);
    }

    private void adjustSize(final String height) {
        gwtRTarea.setHeight(height);
    }

    public void showSaveBeforeDialog() {
        MessageBox.confirm(Kune.I18N.t("Save confirmation"), Kune.I18N.t("Save before close?"),
                new MessageBox.ConfirmCallback() {
                    public void execute(final String btnID) {
                        if (btnID.equals("yes")) {
                            presenter.onSaveAndClose();
                        } else {
                            presenter.onCancelConfirmed();
                        }
                    }
                });

    }

    public View getToolBar() {
        return this.textEditorToolbar;
    }
}
