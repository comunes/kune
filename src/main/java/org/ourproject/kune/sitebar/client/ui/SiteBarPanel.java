package org.ourproject.kune.sitebar.client.ui;

import org.gwm.client.impl.DefaultGFrame;
import org.ourproject.kune.sitebar.client.Images;
import org.ourproject.kune.sitebar.client.SiteBar;
import org.ourproject.kune.sitebar.client.Translate;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SiteBarPanel extends Composite implements SiteBarView, ClickListener {

    private static final String IMAGE_SPIN = "images/spin-kune-thund-green.gif";
    private final HorizontalPanel siteBarHP;
    private final Image spinProcessing;
    private final Label textProcessingLabel;
    private final Hyperlink loginHyperlink;
    private final Translate t;
    private final TextBox searchTextBox;
    private final Image logoImage;
    private Hyperlink newGroupHyperlink;
    private SiteBarListener listener;
    private PushButton searchButton;
    private LoginPanel loginPanel;
    DialogBox dialog;

    public SiteBarPanel(final SiteBarListener listener) {

        // Initialize
        siteBarHP = new HorizontalPanel();
        initWidget(siteBarHP);
        this.listener = listener;
        final Images img = Images.App.getInstance();
        spinProcessing = new Image();
        img.spinKuneThundGreen().applyTo(spinProcessing);
        spinProcessing.setUrl(IMAGE_SPIN);
        textProcessingLabel = new Label();
        final Label expandLabel = new Label("");
        newGroupHyperlink = new Hyperlink();
        final HTML pipeSeparatorHtml = new HTML();
        loginHyperlink = new Hyperlink();
        searchButton = new PushButton(img.kuneSearchIco().createImage(), img.kuneSearchIcoPush()
                .createImage());
        searchTextBox = new TextBox();
        logoImage = new Image();

        // Layout
        siteBarHP.add(spinProcessing);
        siteBarHP.add(textProcessingLabel);
        siteBarHP.add(expandLabel);
        siteBarHP.add(newGroupHyperlink);
        siteBarHP.add(pipeSeparatorHtml);
        siteBarHP.add(loginHyperlink);
        siteBarHP.add(searchButton);
        siteBarHP.add(searchTextBox);
        siteBarHP.add(logoImage);

        // Set properties
        siteBarHP.addStyleName("kune-SiteBarPanel");
        siteBarHP.setCellWidth(expandLabel, "100%");
        spinProcessing.addStyleName("kune-Progress");
        t = SiteBar.getInstance().t;
        textProcessingLabel.setText(t.Processing());
        textProcessingLabel.addStyleName("kune-Progress");
        newGroupHyperlink.setText(t.NewGroup());
        newGroupHyperlink.addClickListener(this);
        pipeSeparatorHtml.setHTML("|");
        pipeSeparatorHtml.setStyleName("kune-SiteBarPanel-Separator");
        loginHyperlink.setText(t.Login());
        loginHyperlink.addClickListener(this);
        searchButton.addClickListener(this);
        searchTextBox.addKeyboardListener(new KeyboardListener() {
            public void onKeyDown(Widget arg0, char arg1, int arg2) {
            }
            public void onKeyPress(Widget arg0, char arg1, int arg2) {
            }
            public void onKeyUp(Widget widget, char key, int mod) {
                if (key == KEY_ENTER) {
                    FireLog.info("Enter pressed");
                    if (searchTextBox.getText() != "") {
                        listener.doSearch(searchTextBox.getText());
                    }
                }
            }});
        searchTextBox.setWidth("180");
        searchTextBox.setText(t.Search());

        // TODO: externalize this
        img.kuneLogo16px().applyTo(logoImage);

        // showProgress(false);

    }

    public void clearSearchText() {
        searchTextBox.setText("");
    }

    public void clearUserName() {
        loginHyperlink.setText(t.Login());
    }

    public void setLogo(final Image logo) {
        // TODO
    }

    public void setProgressText(final String text) {
        textProcessingLabel.setText(text);
    }

    public void setSearchText(final String text) {
        searchTextBox.setText(text);
    }

    public void showLoggedUserName(final String user) {
        loginHyperlink.setText(user);
    }

    public void showProgress(final boolean show) {
        DOM.setIntStyleAttribute(getElement(), "zIndex", DefaultGFrame.getLayerOfTheTopWindow() + 10);
        spinProcessing.setVisible(show);
        textProcessingLabel.setVisible(show);
    }

    public void onClick(Widget sender) {
        // TODO Auto-generated method stub
        if (sender == loginHyperlink) {
            listener.doLogin();
        } else if (sender == newGroupHyperlink) {
            listener.doNewGroup();
        }
    }

    public void showLoginDialog() {
        if (loginPanel == null) {
            LoginPresenter loginPresenter = new LoginPresenter();
            loginPanel = new LoginPanel(loginPresenter);
            loginPresenter.init(loginPanel, this);
        }
        dialog = new DialogBox();
        dialog.setWidget(loginPanel);
        dialog.show();
        dialog.center();
    }

    public void hideLoginDialog() {
        dialog.hide();
    }

}
