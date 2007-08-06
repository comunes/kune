package org.ourproject.kune.sitebar.client.msg;

import org.ourproject.kune.sitebar.client.services.Images;

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
    private static final int TIMEVISIBLE = 4000;

    final Images images = Images.App.getInstance();
    AbstractImagePrototype[] messageIcons = new AbstractImagePrototype[] { images.error(), images.important(),
            images.emblemImportant(), images.info() };
    String[] messageStyle = new String[] { "error", "veryimp", "imp", "info" };

    HTML message = null;
    Image messageIcon = null;
    private final PushButton closeIcon;
    private final SiteMessagePresenter presenter;

    private final Timer timer;

    public SiteMessagePanel(final SiteMessagePresenter sitePresenter) {
        // Initialize
        this.presenter = sitePresenter;
        message = new HTML();
        messageIcon = new Image();
        final Images images = Images.App.getInstance();
        closeIcon = new PushButton(images.cross().createImage(), images.crossDark().createImage());

        // Layout
        add(messageIcon);
        add(message);
        add(closeIcon);

        // Set properties
        setCellVerticalAlignment(messageIcon, VerticalPanel.ALIGN_MIDDLE);
        setCellVerticalAlignment(closeIcon, VerticalPanel.ALIGN_BOTTOM);
        closeIcon.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                if (sender == closeIcon) {
                    setVisible(false);
                    presenter.onClose();
                }
            }
        });
        timer = new Timer() {
            public void run() {
                presenter.reset();
            }
        };
        setVisible(false);
        addStyleName("kune-SiteMessagePanel");
        addStyleName("info");
        images.info().applyTo(messageIcon);
        messageIcon.addStyleName("gwt-Image");
    }

    public void setMessage(final String text, final int lastMessageType, final int type) {

        messageIcons[type].applyTo(messageIcon);
        removeStyleName(messageStyle[lastMessageType]);
        addStyleName(messageStyle[type]);
        setMessage(text);
    }

    public void setMessage(final String text) {
        this.message.setHTML(text);
    }

    public void adjustWidth(final int windowWidth) {
        int messageWidth = windowWidth * 60 / 100 - 3;
        this.setWidth("" + messageWidth);
        message.setWidth("" + (messageWidth - 16 - 40));
    }

    public void hide() {
        this.setVisible(false);
        timer.cancel();
    }

    public void show() {
        this.setVisible(true);
        timer.schedule(TIMEVISIBLE);
    }

    public void reset() {
        message.setText("");
    }
}
