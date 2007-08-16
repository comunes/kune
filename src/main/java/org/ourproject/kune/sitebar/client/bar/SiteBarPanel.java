package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.platf.client.group.NewGroupForm;
import org.ourproject.kune.platf.client.ui.dialogs.TwoButtonsDialog;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.login.LoginForm;
import org.ourproject.kune.sitebar.client.services.Images;
import org.ourproject.kune.sitebar.client.services.Translate;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

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
    private DialogBox currentDialog;
    private final Hyperlink logoutHyperlink;
    private final HTML pipeSeparatorHtml2;
    private TwoButtonsDialog loginDialog;
    private TwoButtonsDialog newGroupDialog;

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
	final LoginForm login = SiteBarFactory.createLogin(presenter);
	loginDialog = new TwoButtonsDialog(t.Login(), t.Login(), t.Cancel(), true, false, 350, 200, 350, 200,
		new FormListener() {
		    public void onAccept() {
			login.doLogin();
		    }

		    public void onCancel() {
			login.doCancel();
		    }
		});
	loginDialog.add((Widget) login.getView());
	loginDialog.hide();
	loginDialog.center();
    }

    public void hideLoginDialog() {
	loginDialog.hide();
    }

    public void showNewGroupDialog() {
	final NewGroupForm newGroupForm = SiteBarFactory.createNewGroup(presenter);
	newGroupDialog = new TwoButtonsDialog(t.RegisterANewGroup(), t.Register(), t.Cancel(), true, false, 450, 300,
		450, 300, new FormListener() {
		    public void onAccept() {
			newGroupForm.doCreateNewGroup();
		    }

		    public void onCancel() {
			newGroupForm.doCancel();
		    }
		});
	newGroupDialog.add((Widget) newGroupForm.getView());
	newGroupDialog.hide();
	newGroupDialog.center();
    }

    public void hideNewGroupDialog() {
	currentDialog.hide();

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
