package org.ourproject.kune.sitebar.client.msg;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteMessagePanel extends VerticalPanel implements SiteMessageView {
    private static final int TIMEVISIBLE = 4000;

    final Images images = Images.App.getInstance();
    AbstractImagePrototype[] messageIcons = new AbstractImagePrototype[] { images.error(), images.important(),
            images.emblemImportant(), images.emblemImportant() };
    String[] messageStyle = new String[] { "error", "veryimp", "imp", "info" };

    HTML message = null;
    Image icon = null;
    private final PushButton closeLink;
    private final SiteMessagePresenter presenter;

    private final Timer timer;

    public SiteMessagePanel(final SiteMessagePresenter sitePresenter) {
        this.presenter = sitePresenter;
        HorizontalPanel messageHP = new HorizontalPanel();
        message = new HTML();
        icon = new Image();
        HorizontalPanel closeHP = new HorizontalPanel();
        Label expandCell = new Label("");

        final Images images = Images.App.getInstance();
        closeLink = new PushButton(images.cross().createImage(), images.crossDark().createImage());
        closeLink.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                if (sender == closeLink) {
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
        // Layout
        // this.add(new BorderPanel(messageHP, 5, 0, 0, 0));
        add(messageHP);
        add(closeHP);
        closeHP.add(expandCell);
        closeHP.add(closeLink);
        // messageHP.add(new BorderPanel(icon, 0, 10, 0, 5));
        messageHP.add(icon);
        messageHP.add(message);

        // Set properties

        setVisible(false);
        // this.setHeight("33");
        addStyleName("kune-SiteMessagePanel");
        // message.setHeight("27");
        images.info().applyTo(icon);
        closeHP.setWidth("100%");
        expandCell.setWidth("100%");
        closeHP.setCellWidth(expandCell, "100%");
    }

    public void setMessage(final String text, final int lastMessageType, final int type) {

        messageIcons[type].applyTo(icon);
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
