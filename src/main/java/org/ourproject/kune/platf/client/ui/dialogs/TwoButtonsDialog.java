
package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.ui.CustomButton;
import org.ourproject.kune.platf.client.ui.form.FormListener;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Window;

public class TwoButtonsDialog {

    private final Window dialog;

    public TwoButtonsDialog(final String caption, final String firstButton, final String secondButton,
            final boolean modal, final boolean minimizable, final int width, final int height, final int minWidth,
            final int minHeight, final FormListener listener) {

        dialog = new Window();

        // Param values
        dialog.setTitle(caption);
        dialog.setModal(modal);
        dialog.setWidth(width);
        dialog.setHeight(height);
        dialog.setMinWidth(minWidth);
        dialog.setMinHeight(minHeight);
        dialog.setCollapsible(minimizable);

        // Def values
        dialog.

        setShadow(true);

        dialog.addButton(new CustomButton(firstButton, new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onAccept();
            }
        }).getButton());

        dialog.addButton(new CustomButton(secondButton, new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onCancel();
            }
        }).getButton());

    }

    public TwoButtonsDialog(final String caption, final String firstButton, final String secondButton,
            final boolean modal, final boolean minimizable, final int width, final int height,
            final FormListener listener) {
        this(caption, firstButton, secondButton, modal, minimizable, width, height, width, height, listener);

    }

    public void add(final Widget widget) {
        dialog.add(widget);
    }

    public void show() {
        dialog.show();
    }

    public void center() {
        dialog.center();
    }

    public void hide() {
        dialog.hide();
    }
}
