package org.ourproject.kune.workspace.client.sitebar.msg;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteMessagePanel extends HorizontalPanel implements SiteMessageView {
    private static final int TIMEVISIBLE = 6000;

    final Images images = Images.App.getInstance();
    AbstractImagePrototype[] messageIcons = new AbstractImagePrototype[] { images.error(), images.important(),
            images.emblemImportant(), images.info() };
    String[] messageStyle = new String[] { "error", "veryimp", "imp", "info" };

    HTML message = null;
    Image messageIcon = null;

    private final Timer timer;

    public SiteMessagePanel(final MessagePresenter presenter, final boolean closable) {
        // Initialize
        message = new HTML();
        messageIcon = new Image();
        final Images images = Images.App.getInstance();

        // Layout
        add(messageIcon);
        add(message);

        // Set properties
        setCellVerticalAlignment(messageIcon, VerticalPanel.ALIGN_MIDDLE);

        timer = new Timer() {
            public void run() {
                hide();
                if (presenter != null) {
                    presenter.resetMessage();
                }
            }
        };
        setVisible(false);
        setStyleName("kune-SiteMessagePanel");
        addStyleDependentName("info");
        images.info().applyTo(messageIcon);
        messageIcon.addStyleName("gwt-Image");
        message.setWidth("100%");
        this.setCellWidth(message, "100%");

        if (closable) {
            final PushButton closeIcon = new PushButton(images.cross().createImage(), images.crossDark().createImage());
            add(closeIcon);
            closeIcon.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    if (sender == closeIcon) {
                        setVisible(false);
                        hide();
                        if (presenter != null) {
                            presenter.resetMessage();
                        }
                    }
                }
            });
            setCellVerticalAlignment(closeIcon, VerticalPanel.ALIGN_BOTTOM);
        }

    }

    public void setMessage(final String text, final int lastMessageType, final int type) {
        messageIcons[type].applyTo(messageIcon);
        removeStyleDependentName(messageStyle[lastMessageType]);
        addStyleDependentName(messageStyle[type]);
        setMessage(text);
    }

    public void setMessage(final String text) {
        this.message.setHTML(text);
    }

    public void adjustWidth(final int windowWidth) {
        int messageWidth = windowWidth * 60 / 100 - 3;
        this.setWidth("" + messageWidth);
    }

    public void show() {
        this.setVisible(true);
        timer.schedule(TIMEVISIBLE);
    }

    public void hide() {
        message.setText("");
        this.setVisible(false);
        timer.cancel();
    }
}
