package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;

public class EditableIconLabel extends Composite {

    private boolean useDoubleClick;
    private ClickListener listener;
    private String currentText;
    private String oldText;
    private EditableClickListener editableListener;
    private MouseListenerAdapter mouseOverListener;
    private final AbstractLabel label;

    public EditableIconLabel(final EditableClickListener editableListener) {
        this("", editableListener);
    }

    public EditableIconLabel(final String text, final EditableClickListener editableListener) {
        this(text, false, editableListener);
    }

    public EditableIconLabel(final String text, final boolean useDoubleClick,
            final EditableClickListener editableListener) {
        this(text, false, false, editableListener);
    }

    public EditableIconLabel(final String text, final boolean wordWrap, final boolean useDoubleClick,
            final EditableClickListener editableListenerOrig) {
        label = new LabelWrapper(text, wordWrap);
        init(text, useDoubleClick, editableListenerOrig);
    }

    public EditableIconLabel(final AbstractImagePrototype icon, final String text, final String targetHistoryToken,
            final boolean useDoubleClick, final EditableClickListener editableListenerOrig) {
        label = new IconHyperlink(icon, text, targetHistoryToken);
        init(text, useDoubleClick, editableListenerOrig);
    }

    public EditableIconLabel(final AbstractImagePrototype icon, final String text, final boolean useDoubleClick,
            final EditableClickListener editableListenerOrig) {
        label = new IconLabel(icon, text);
        init(text, useDoubleClick, editableListenerOrig);
    }

    private void init(final String text, final boolean useDoubleClick, final EditableClickListener editableListenerOrig) {
        initWidget((Widget) label);
        this.currentText = text;
        this.oldText = text;
        this.useDoubleClick = useDoubleClick;
        this.editableListener = editableListenerOrig;
        this.listener = new ClickListener() {
            public void onClick(final Widget sender) {
                showEditableDialog();
            }
        };
        mouseOverListener = new MouseListenerAdapter() {
            public void onMouseEnter(final Widget sender) {
                label.addStyleDependentName("high");
            }

            public void onMouseLeave(final Widget sender) {
                label.removeStyleDependentName("high");
            }
        };
        label.setStylePrimaryName("kune-EditableLabel");
        label.addMouseListener(mouseOverListener);
        setEditable(false);
    }

    public void setEditable(final boolean editable) {
        reset();
        if (editable) {
            if (useDoubleClick) {
                setQuickTip(Kune.I18N.t("Double click to rename"));
                label.addDoubleClickListener(listener);
            } else {
                setQuickTip(Kune.I18N.t("Click to rename"));
                label.addClickListener(listener);
            }
            label.addStyleDependentName("editable");
            // label.addDoubleClickListener(listener);
            label.addMouseListener(mouseOverListener);
        } else {
            setQuickTip("");
            label.addStyleDependentName("noneditable");
        }
    }

    private void setQuickTip(final String text) {
        KuneUiUtils.setQuickTip((Widget) label, text);
    }

    public void restoreOldText() {
        label.setText(oldText);
        this.currentText = this.oldText;
    }

    public void setText(final String text) {
        this.oldText = this.currentText;
        this.currentText = text;
        label.setText(text);
    }

    public void setEditableListener(final EditableClickListener editableListener) {
        this.editableListener = editableListener;
    }

    public EditableClickListener getEditableListener() {
        return editableListener;
    }

    private void showEditableDialog() {
        MessageBox.show(new MessageBoxConfig() {
            {
                setClosable(true);
                setModal(true);
                setWidth(300);
                setDefaultTextHeight(2);
                setButtons(MessageBox.OKCANCEL);
                setTitle(Kune.I18N.t("Rename"));
                setMsg(Kune.I18N.t("Write a new name:"));
                setCallback(new MessageBox.PromptCallback() {
                    public void execute(final String btnID, final String text) {
                        if (btnID.equals("ok") && text != null) {
                            editableListener.onEdited(text);
                        } else {
                            // Do nothing
                        }
                    }
                });
                setMultiline(true);
                setValue(currentText);
            }
        });
    }

    private void reset() {
        label.removeStyleDependentName("noneditable");
        label.removeStyleDependentName("editable");
        label.removeClickListener(listener);
        label.removeDoubleClickListener(listener);
        label.removeMouseListener(mouseOverListener);
    }

}
