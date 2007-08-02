package org.ourproject.kune.platf.client.workspace.ui;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class SiteMessagePanel extends VerticalPanel {

    private static final Images IMG = Images.App.getInstance();

    private static final int TIMEVISIBLE = 4000;

    private static final String COLORINFO = "#E5FF80";
    private static final String COLORIMP = "#FFE6D5";
    private static final String COLORVERYIMP = "#FFD4AA";
    private static final String COLORERROR = "#FFB380";

    // TODO permit multiple messages
    private TextArea message = null;
    private Image icon = null;
    private HorizontalPanel messageHP = null;
    private Hyperlink closeLink = null;

    private String currentColor = COLORINFO; // Initial CSS value

    Timer timer = new Timer() {
        public void run() {
            // SiteMessageDialog.setVisible(false);
            message.setText("");
        }
    };

    public SiteMessagePanel() {
        message = new TextArea();
        icon = new Image();
        closeLink = new Hyperlink();
        messageHP = new HorizontalPanel();
        //closeLink.addClickListener(this);

        // Layout
        // this.add(new BorderPanel(messageHP, 5, 0, 0, 0));
        add(messageHP);
        add(closeLink);
        // messageHP.add(new BorderPanel(icon, 0, 10, 0, 5));
        messageHP.add(icon);
        messageHP.add(message);
        // this.setCellHorizontalAlignment(closeLink,
        // HasHorizontalAlignment.ALIGN_RIGHT);

        // Set properties
        this.setVisible(false);

        // this.setHeight("33");
        this.addStyleName("site-message-dialog");
        message.setHeight("27");
        message.addStyleName("site-message");
        closeLink.addStyleName("site-message");
        IMG.info().applyTo(icon);
        closeLink.setText(Kune.getInstance().t.Close());
    }

    public void setMessage(String message) {
        // FIXME: This mix different message levels:
        this.message.setText(this.message.getText() + "\n" + message);
        // Put on the top of all windows/popup
        // DOM.setIntStyleAttribute(getElement(), "zIndex",
        // DefaultGFrame.getLayerOfTheTopWindow() + 10);
        this.setVisible(true);
        timer.schedule(TIMEVISIBLE);
    }

    public void setBackgroundColor(String color) {
        if (currentColor != color) {
            DOM.setStyleAttribute(getElement(), "backgroundColor", color);
            DOM.setStyleAttribute(message.getElement(), "backgroundColor", color);
            DOM.setStyleAttribute(closeLink.getElement(), "backgroundColor", color);
            currentColor = color;
        }
    }

    public void setMessageError(String message) {
        setBackgroundColor(COLORERROR);
        IMG.error().applyTo(icon);
        this.setMessage(message);
    }

    public void setMessageVeryImp(String message) {
        setBackgroundColor(COLORVERYIMP);
        IMG.important().applyTo(icon);
        this.setMessage(message);
    }

    public void setMessageImp(String message) {
        setBackgroundColor(COLORIMP);
        IMG.emblemImportant().applyTo(icon);
        this.setMessage(message);
    }

    public void setMessageInfo(String message) {
        setBackgroundColor(COLORINFO);
        IMG.info().applyTo(icon);
        this.setMessage(message);
    }

    public void onClick(Widget sender) {
        if (sender == closeLink) {
            this.setVisible(false);
        }
    }

    public void adjustWidth(int windowWidth) {
        int messageWidth = windowWidth * 60 / 100 - 3;
        this.setWidth("" + messageWidth);
        message.setWidth("" + (messageWidth - 16 - 40));
    }
}
