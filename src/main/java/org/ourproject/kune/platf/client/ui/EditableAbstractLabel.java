package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;

public class EditableAbstractLabel extends Label {

    private final boolean useDoubleClick;
    private final ClickListener listener;
    private String currentText;
    private String oldText;
    private EditableClickListener editableListener;
    private final MouseListenerAdapter mouseOverListener;

    public EditableAbstractLabel(final EditableClickListener editableListener) {
        this("", editableListener);
    }

    public EditableAbstractLabel(final String text, final EditableClickListener editableListener) {
        this(text, false, editableListener);
    }

    public EditableAbstractLabel(final String text, final boolean useDoubleClick, final EditableClickListener editableListener) {
        this(text, false, false, editableListener);
    }

    public EditableAbstractLabel(final String text, final boolean wordWrap, final boolean useDoubleClick,
            final EditableClickListener editableListenerOrig) {
        super(text, wordWrap);
        this.currentText = text;
        this.oldText = text;
        this.useDoubleClick = useDoubleClick;
        this.editableListener = editableListenerOrig;
        this.listener = new ClickListener() {
            public void onClick(final Widget sender) {
                showEditableDialog(currentText);
            }
        };
        mouseOverListener = new MouseListenerAdapter() {
            public void onMouseEnter(final Widget sender) {
                addStyleDependentName("high");
            }

            public void onMouseLeave(final Widget sender) {
                removeStyleDependentName("high");
            }
        };
        this.setStylePrimaryName("kune-EditableLabel");
        this.addStyleDependentName("editable");
        addClickListener(listener);
        addMouseListener(mouseOverListener);
        setEditable(false);
    }

    public void setEditable(final boolean editable) {
        if (editable) {
            if (useDoubleClick) {
                setTitle(Kune.I18N.t("Double click to rename"));
            } else {
                setTitle(Kune.I18N.t("Click to rename"));
            }
            removeStyleDependentName("noneditable");
            addStyleDependentName("editable");
            addClickListener(listener);
            addMouseListener(mouseOverListener);
        } else {
            setTitle("");
            removeStyleDependentName("editable");
            addStyleDependentName("noneditable");
            removeClickListener(listener);
            removeMouseListener(mouseOverListener);
        }
    }

    public void restoreOldText() {
        setText(oldText);
        this.currentText = this.oldText;
    }

    public void setText(final String text) {
        this.oldText = this.currentText;
        super.setText(text);
    }

    public void setEditableListener(final EditableClickListener editableListener) {
        this.editableListener = editableListener;
    }

    public EditableClickListener getEditableListener() {
        return editableListener;
    }

    private void showEditableDialog(final String currentText) {
        MessageBox.show(new MessageBoxConfig() {
            {
                setClosable(true);
                setModal(true);
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

}
