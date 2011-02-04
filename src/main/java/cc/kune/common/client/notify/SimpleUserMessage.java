package cc.kune.common.client.notify;

import org.cobogw.gwt.user.client.ui.RoundedPanel;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class SimpleUserMessage extends Composite {
    private static final int SHOWTIME = 3500;
    private final RoundedPanel rp;
    private final Label msg;
    private PopupPanel popupPalette;
    private final Timer timer;

    public SimpleUserMessage() {
        msg = new Label();
        msg.addStyleName("oc-user-msg");
        rp = new RoundedPanel(msg, RoundedPanel.ALL, 2);
        rp.setBorderColor("#FFCC00");
        timer = new Timer() {
            @Override
            public void run() {
                hide();
            }
        };

    }

    public void hide() {
        if (popupPalette != null) {
            popupPalette.hide();
        }
    }

    public void show(final String message) {
        msg.setText(message);
        popupPalette = new PopupPanel(true, false);
        popupPalette.setWidget(rp);
        popupPalette.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                popupPalette.setPopupPosition((Window.getClientWidth() - msg.getOffsetWidth()) / 2,
                        Window.getClientHeight() / 3);
            }
        });
        popupPalette.setStyleName("oc-user-msg-popup");
        popupPalette.setAnimationEnabled(true);
        timer.schedule(SHOWTIME);
    }
}
