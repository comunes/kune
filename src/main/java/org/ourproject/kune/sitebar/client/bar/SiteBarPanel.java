/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.sitebar.client.bar;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.newgroup.NewGroupForm;
import org.ourproject.kune.platf.client.newgroup.ui.NewGroupFormPanel;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.login.LoginForm;
import org.ourproject.kune.sitebar.client.login.LoginFormPanel;
import org.ourproject.kune.sitebar.client.services.Images;
import org.ourproject.kune.sitebar.client.services.Translate;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SiteBarPanel extends Composite implements SiteBarView {

    private static final String IGNORE_TOKEN = "fixme";
    private static final String IMAGE_SPIN = "images/spin-kune-thund-green.gif";
    private final SiteBarPresenter presenter;
    private final Translate t;
    private final Image logoImage;

    private final HorizontalPanel siteBarHP;

    private final Image spinProcessing;
    private final Label textProcessingLabel;
    private final Hyperlink loginHyperlink;
    private final Hyperlink loggedUserHyperlink;
    private final Hyperlink newGroupHyperlink;
    private final HTML pipeSeparatorHtml2;
    private PushButton searchButton;
    private TextBox searchTextBox;

    private final Hyperlink logoutHyperlink;
    private LoginFormPanel loginPanel;
    private final Images img;
    private final MenuBar optionsSubmenu;
    private MenuItem linkHelpInTrans;
    private MenuItem linkHelp;
    private final MenuBar yourGroupsSubmenu;
    private NewGroupFormPanel newGroupPanel;

    public SiteBarPanel(final SiteBarPresenter initPresenter) {
	t = SiteBarTrans.getInstance().t;
	img = Images.App.getInstance();

	// Initialize
	siteBarHP = new HorizontalPanel();
	initWidget(siteBarHP);
	this.presenter = initPresenter;
	spinProcessing = new Image();
	img.spinKuneThundGreen().applyTo(spinProcessing);
	spinProcessing.setUrl(IMAGE_SPIN);
	textProcessingLabel = new Label();
	final Label expandLabel = new Label("");
	newGroupHyperlink = new Hyperlink();
	final HTML pipeSeparatorHtml = new HTML();
	pipeSeparatorHtml2 = new HTML();
	loginHyperlink = new Hyperlink();
	loggedUserHyperlink = new Hyperlink();
	logoutHyperlink = new Hyperlink();
	HTML spaceSeparator1 = new HTML("<b></b>");
	MenuBar options = new MenuBar();
	optionsSubmenu = new MenuBar(true);
	yourGroupsSubmenu = new MenuBar(true);
	RoundedBorderDecorator optionsButton = new RoundedBorderDecorator(options, RoundedBorderDecorator.ALL,
		RoundedBorderDecorator.SIMPLE);
	HTML spaceSeparator2 = new HTML("<b></b>");
	logoImage = new Image();

	// Layout
	siteBarHP.add(spinProcessing);
	siteBarHP.add(textProcessingLabel);
	siteBarHP.add(expandLabel);
	siteBarHP.add(loginHyperlink);
	siteBarHP.add(loggedUserHyperlink);
	siteBarHP.add(pipeSeparatorHtml);
	siteBarHP.add(logoutHyperlink);
	siteBarHP.add(pipeSeparatorHtml2);
	siteBarHP.add(newGroupHyperlink);
	siteBarHP.add(spaceSeparator1);
	siteBarHP.add(optionsButton);
	siteBarHP.add(spaceSeparator2);
	createSearchBox();
	siteBarHP.add(logoImage);

	// Set properties
	siteBarHP.addStyleName("kune-SiteBarPanel");
	siteBarHP.setCellWidth(expandLabel, "100%");
	spinProcessing.addStyleName("kune-Progress");
	textProcessingLabel.setText(t.Processing());
	textProcessingLabel.addStyleName("kune-Progress");
	newGroupHyperlink.setText(t.NewGroup());
	newGroupHyperlink.setTargetHistoryToken(IGNORE_TOKEN);
	loggedUserHyperlink.setVisible(false);

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
	loginHyperlink.setTargetHistoryToken(IGNORE_TOKEN);
	logoutHyperlink.setText(t.Logout());
	logoutHyperlink.setTargetHistoryToken(IGNORE_TOKEN);

	options.addItem(t.Options(), true, optionsSubmenu);
	options.setStyleName("kune-MenuBar");
	optionsButton.setColor("AAA");
	optionsButton.setHeight("16px");
	createOptionsSubmenu();
	addDefaultItemsToOptions();

	spaceSeparator1.setStyleName("kune-SiteBarPanel-SpaceSeparator");
	spaceSeparator2.setStyleName("kune-SiteBarPanel-SpaceSeparator");

	createListeners();

	// TODO: externalize this
	img.kuneLogo16px().applyTo(logoImage);

	this.hideProgress();
    }

    private void createOptionsSubmenu() {
	linkHelpInTrans = new MenuItem(img.language().getHTML() + t.HelpWithTranslation(), true, new Command() {
	    public void execute() {
		Window.open("http://translate-kune.ourproject.org", "_blank", "");
	    }
	});
	linkHelp = new MenuItem(img.kuneIcon16().getHTML() + t.Help(), true, new Command() {
	    public void execute() {
		// FIXME: Url to doc
		presenter.changeState(new StateToken("site.docs"));
	    }
	});
    }

    private void createSearchBox() {
	searchButton = new PushButton(img.kuneSearchIco().createImage(), img.kuneSearchIcoPush().createImage());
	searchTextBox = new TextBox();

	siteBarHP.add(searchButton);
	siteBarHP.add(searchTextBox);

	searchTextBox.setWidth("180");
	setDefaultTextSearch();
	searchTextBox.addFocusListener(new FocusListener() {

	    public void onFocus(final Widget arg0) {
		presenter.onSearchFocus();
	    }

	    public void onLostFocus(final Widget arg0) {
		presenter.onSearchLostFocus(searchTextBox.getText());
	    }
	});
    }

    public void setDefaultTextSearch() {
	searchTextBox.setText(t.Search());
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

    public void showLoggedUserName(final String name, final String homePage) {
	loginHyperlink.setVisible(false);
	loggedUserHyperlink.setVisible(true);
	loggedUserHyperlink.setText(name);
	loggedUserHyperlink.setTargetHistoryToken(homePage);
    }

    public void showLoginDialog() {
	final LoginForm login = SiteBarFactory.createLogin(presenter);
	loginPanel = (LoginFormPanel) login.getView();
	loginPanel.show();
    }

    public void hideLoginDialog() {
	loginPanel.hide();
    }

    public void showNewGroupDialog() {
	final NewGroupForm newGroupForm = SiteBarFactory.createNewGroup(presenter);
	newGroupPanel = (NewGroupFormPanel) newGroupForm.getView();
	newGroupPanel.show();
	Site.hideProgress();
    }

    public void hideNewGroupDialog() {
	newGroupPanel.hide();
    }

    public void setLogoutLinkVisible(final boolean visible) {
	logoutHyperlink.setVisible(visible);
	pipeSeparatorHtml2.setVisible(visible);
    }

    public void restoreLoginLink() {
	loginHyperlink.setVisible(true);
	loggedUserHyperlink.setVisible(false);
	loginHyperlink.setTargetHistoryToken(IGNORE_TOKEN);
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

    private void createListeners() {
	loginHyperlink.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		presenter.doLogin();
	    }
	});
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
    }

    public void setGroupsIsMember(final List groupsIsAdmin, final List groupsIsEditor) {
	optionsSubmenu.clearItems();
	yourGroupsSubmenu.clearItems();
	optionsSubmenu.addItem(t.MyGroups() + " »", yourGroupsSubmenu);
	for (int i = 0; i < groupsIsAdmin.size(); i++) {
	    final LinkDTO link = (LinkDTO) groupsIsAdmin.get(i);
	    addItemToYourGroupSubmenu(link);
	}
	for (int i = 0; i < groupsIsEditor.size(); i++) {
	    final LinkDTO link = (LinkDTO) groupsIsEditor.get(i);
	    addItemToYourGroupSubmenu(link);
	}
	// i18n
	addDefaultItemsToOptions();
    }

    private void addDefaultItemsToOptions() {
	optionsSubmenu.addItem(linkHelpInTrans);
	optionsSubmenu.addItem(linkHelp);
    }

    private void addItemToYourGroupSubmenu(final LinkDTO link) {
	yourGroupsSubmenu.addItem(link.getShortName(), true, new Command() {
	    public void execute() {
		presenter.changeState(new StateToken(link.getLink()));
	    }
	});
    }

    public void resetOptionsSubmenu() {
	optionsSubmenu.clearItems();
	addDefaultItemsToOptions();
    }

}
