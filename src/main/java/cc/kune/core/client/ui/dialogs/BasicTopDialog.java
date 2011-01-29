package cc.kune.core.client.ui.dialogs;

import cc.kune.common.client.ui.PopupTopPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.PopupPanel;

public class BasicTopDialog extends BasicDialog {

    private final PopupTopPanel popup;

    public BasicTopDialog(final String dialogId, final String title, final boolean autohide, final boolean modal,
            final boolean autoscroll, final int width, final int height, final String icon,
            final String firstButtonTitle, final String firstButtonId, final String cancelButtonTitle,
            final String cancelButtonId, final int tabIndexStart) {
        popup = new PopupTopPanel(autohide, modal);
        popup.add(this);
        popup.ensureDebugId(dialogId);
        super.getTitleText().setText(title);
        // super.setAutoscroll(autoscroll);
        // super.setSize(String.valueOf(width), String.valueOf(height));
        GWT.log("Not setting size of dialog to: " + String.valueOf(width) + "/" + String.valueOf(height));
        super.setTitleIcon(icon);
        super.getFirstBtnText().setText(firstButtonTitle);
        super.getSecondBtnText().setText(cancelButtonTitle);
        super.setFirstBtnId(firstButtonId);
        super.setSecondBtnId(cancelButtonId);
    }

    public HasCloseHandlers<PopupPanel> getClose() {
        return popup;
    }

    public void hide() {
        popup.hide();

    }

    public void show() {
        popup.showCentered();
    }
}
