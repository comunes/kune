package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.platf.client.group.NewGroupPanel;
import org.ourproject.kune.platf.client.group.NewGroupPresenter;
import org.ourproject.kune.sitebar.client.login.LoginPanel;
import org.ourproject.kune.sitebar.client.login.LoginPresenter;
import org.ourproject.kune.sitebar.client.services.Images;
import org.ourproject.kune.sitebar.client.services.Translate;

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

public class SiteBarPanel extends Composite implements SiteBarView {

    private static final String IMAGE_SPIN = "images/spin-kune-thund-green.gif";
    private final HorizontalPanel siteBarHP;
    private final Image spinProcessing;
    private final Label textProcessingLabel;
    private final Hyperlink loginHyperlink;
    private final Translate t;
    private final TextBox searchTextBox;
    private final Image logoImage;
    private final Hyperlink newGroupHyperlink;
    private final SiteBarPresenter presenter;
    private final PushButton searchButton;
    private LoginPanel loginPanel;
    private NewGroupPanel newGroupPanel;
    DialogBox loginDialog;
    private final Hyperlink logoutHyperlink;
    private final HTML pipeSeparatorHtml2;
    private DialogBox newGroupDialog;

    public SiteBarPanel(final SiteBarPresenter presenter) {

        // Initialize
        siteBarHP = new HorizontalPanel();
        initWidget(siteBarHP);
        this.presenter = presenter;
        final Images img = Images.App.getInstance();
        spinProcessing = new Image();
        img.spinKuneThundGreen().applyTo(spinProcessing);
        spinProcessing.setUrl(IMAGE_SPIN);
        textProcessingLabel = new Label();
        final Label expandLabel = new Label("");
        newGroupHyperlink = new Hyperlink();
        final HTML pipeSeparatorHtml = new HTML();
        pipeSeparatorHtml2 = new HTML();
        loginHyperlink = new Hyperlink();
        logoutHyperlink = new Hyperlink();
        searchButton = new PushButton(img.kuneSearchIco().createImage(), img.kuneSearchIcoPush().createImage());
        searchTextBox = new TextBox();
        logoImage = new Image();

        // Layout
        siteBarHP.add(spinProcessing);
        siteBarHP.add(textProcessingLabel);
        siteBarHP.add(expandLabel);
        siteBarHP.add(loginHyperlink);
        siteBarHP.add(pipeSeparatorHtml);
        siteBarHP.add(logoutHyperlink);
        siteBarHP.add(pipeSeparatorHtml2);
        siteBarHP.add(newGroupHyperlink);
        siteBarHP.add(searchButton);
        siteBarHP.add(searchTextBox);
        siteBarHP.add(logoImage);

        // Set properties
        siteBarHP.addStyleName("kune-SiteBarPanel");
        siteBarHP.setCellWidth(expandLabel, "100%");
        spinProcessing.addStyleName("kune-Progress");
        t = SiteBarTrans.getInstance().t;
        textProcessingLabel.setText(t.Processing());
        textProcessingLabel.addStyleName("kune-Progress");
        newGroupHyperlink.setText(t.NewGroup());
        newGroupHyperlink.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.doNewGroup();
            }
        });
        pipeSeparatorHtml.setHTML("|");
        pipeSeparatorHtml.setStyleName("kune-SiteBarPanel-Separator");
        pipeSeparatorHtml2.setHTML("|");
        pipeSeparatorHtml2.setStyleName("kune-SiteBarPanel-Separator");
        loginHyperlink.setText(t.Login());
        loginHyperlink.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.doLogin();
            }
        });
        logoutHyperlink.setText(t.Logout());
        searchButton.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.doSearch(searchTextBox.getText());
            }
        });
        logoutHyperlink.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.doLogout();
            }
        });
        searchTextBox.addKeyboardListener(new KeyboardListener() {
            public void onKeyDown(final Widget arg0, final char arg1, final int arg2) {
            }
            public void onKeyPress(final Widget arg0, final char arg1, final int arg2) {
            }
            public void onKeyUp(final Widget widget, final char key, final int mod) {
                if (key == KEY_ENTER) {
                    if (searchTextBox.getText().length() > 0) {
                        presenter.doSearch(searchTextBox.getText());
                    }
                }
            }
        });
        searchTextBox.setWidth("180");
        searchTextBox.setText(t.Search());

        // TODO: externalize this
        img.kuneLogo16px().applyTo(logoImage);

        this.hideProgress();
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

    public void setSearchText(final String text) {
        searchTextBox.setText(text);
    }

    public void showLoggedUserName(final String user) {
        loginHyperlink.setText(user);
    }

    public void showLoginDialog() {
        if (loginPanel == null) {
            LoginPresenter loginPresenter = new LoginPresenter(presenter);
            loginPanel = new LoginPanel(loginPresenter);
            loginPresenter.init(loginPanel);
        }
        loginDialog = new DialogBox();
        loginDialog.setWidget(loginPanel);
        loginDialog.setText(t.Login()); // TODO: Better description
        loginDialog.show();
        loginDialog.center();
    }

    public void hideLoginDialog() {
        loginDialog.hide();
    }

    public void showNewGroupDialog() {
        if (newGroupPanel == null) {
            NewGroupPresenter newGroupPresenter = new NewGroupPresenter(presenter);
            newGroupPanel = new NewGroupPanel(newGroupPresenter);
            newGroupPresenter.init(newGroupPanel);
        }
        newGroupDialog = new DialogBox();
        newGroupDialog.setWidget(newGroupPanel);
        newGroupDialog.setText(t.CreateNewGroup()); // TODO: Better description
        newGroupDialog.show();
        newGroupDialog.center();
    }

    public void hideNewGroupDialog() {
        newGroupDialog.hide();

    }

    public void setLogoutLinkVisible(final boolean visible) {
        logoutHyperlink.setVisible(visible);
        pipeSeparatorHtml2.setVisible(visible);
    }

    public void restoreLoginLink() {
        loginHyperlink.setText(t.Login());
    }

    public void showProgress(final String text) {
        textProcessingLabel.setText(text);
        spinProcessing.setVisible(true);
        textProcessingLabel.setVisible(true);
    }

    public void hideProgress() {
        spinProcessing.setVisible(false);
        textProcessingLabel.setVisible(false);
    }

}
