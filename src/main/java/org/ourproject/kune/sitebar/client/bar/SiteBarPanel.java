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
import org.ourproject.kune.platf.client.newgroup.NewGroup;
import org.ourproject.kune.platf.client.newgroup.ui.NewGroupPanel;
import org.ourproject.kune.platf.client.search.SearchSite;
import org.ourproject.kune.platf.client.search.ui.SearchSitePanel;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.IconHyperlink;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.login.Login;
import org.ourproject.kune.sitebar.client.login.LoginPanel;
import org.ourproject.kune.sitebar.client.services.Images;
import org.ourproject.kune.sitebar.client.services.Translate;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.MessageBox;

public class SiteBarPanel extends Composite implements SiteBarView {

    private static final String SEARCH_TEXT_WIDTH_SMALL = "120";
    private static final String SEARCH_TEXT_WIDTH_BIG = "180";
    private final SiteBarPresenter presenter;
    private final Translate t;
    private final Image logoImage;

    private final HorizontalPanel siteBarHP;

    private final IconHyperlink gotoPublic;
    private final Hyperlink loginHyperlink;
    private final Hyperlink loggedUserHyperlink;
    private final Hyperlink newGroupHyperlink;
    private final HTML pipeSeparatorHtml2;
    private PushButton searchButton;
    private TextBox searchTextBox;

    private final Label logoutLabel;
    private LoginPanel loginPanel;
    private final Images img;
    private final MenuBar optionsSubmenu;
    private MenuItem linkHelpInTrans;
    private MenuItem linkHelp;
    private final MenuBar yourGroupsSubmenu;
    private NewGroupPanel newGroupPanel;
    private final IconLabel contentNoPublic;
    private final Widget progressPanel;
    private final Widget progressText;
    private final HorizontalPanel publicHP;
    private SearchSitePanel searchPanel;

    public SiteBarPanel(final SiteBarPresenter initPresenter) {
        t = SiteBarTrans.getInstance().t;
        img = Images.App.getInstance();

        // Initialize
        siteBarHP = new HorizontalPanel();

        initWidget(siteBarHP);
        this.presenter = initPresenter;

        progressPanel = RootPanel.get("kuneprogresspanel");
        progressText = RootPanel.get("kuneprogresstext");
        publicHP = new HorizontalPanel();
        gotoPublic = new IconHyperlink(img.anybody(), Kune.I18N.t("Go to Public Space"), Site.FIXME_TOKEN);
        contentNoPublic = new IconLabel(img.anybody(), Kune.I18N.t("This content is not public"));
        final Label expandLabel = new Label("");
        newGroupHyperlink = new Hyperlink();
        final HTML pipeSeparatorHtml = new HTML();
        pipeSeparatorHtml2 = new HTML();
        loginHyperlink = new Hyperlink();
        loggedUserHyperlink = new Hyperlink();
        logoutLabel = new Label();
        HTML spaceSeparator1 = new HTML("<b></b>");
        MenuBar options = new MenuBar();
        optionsSubmenu = new MenuBar(true);
        yourGroupsSubmenu = new MenuBar(true);
        RoundedBorderDecorator optionsButton = new RoundedBorderDecorator(options, RoundedBorderDecorator.ALL,
                RoundedBorderDecorator.SIMPLE);
        HTML spaceSeparator2 = new HTML("<b></b>");
        logoImage = new Image();

        // Layout
        siteBarHP.add(publicHP);
        publicHP.add(gotoPublic);
        publicHP.add(contentNoPublic);
        siteBarHP.add(expandLabel);
        siteBarHP.add(loginHyperlink);
        siteBarHP.add(loggedUserHyperlink);
        siteBarHP.add(pipeSeparatorHtml);
        siteBarHP.add(logoutLabel);
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
        showProgress(t.Processing());
        gotoPublic.addStyleName("kune-Margin-Medium-r");
        contentNoPublic.setVisible(false);
        contentNoPublic.addStyleName("kune-Margin-Medium-r");
        newGroupHyperlink.setText(t.CreateNewGroup());
        newGroupHyperlink.setTargetHistoryToken(Site.NEWGROUP_TOKEN);
        loggedUserHyperlink.setVisible(false);
        pipeSeparatorHtml.setHTML("|");
        pipeSeparatorHtml.setStyleName("kune-SiteBarPanel-Separator");
        pipeSeparatorHtml2.setHTML("|");
        pipeSeparatorHtml2.setStyleName("kune-SiteBarPanel-Separator");
        loginHyperlink.setText(t.SignInToCollaborate());
        loginHyperlink.setTargetHistoryToken(Site.LOGIN_TOKEN);
        logoutLabel.setText(t.SignOut());
        logoutLabel.addStyleName("kune-SiteBarPanel-LabelLink");
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
                presenter.onHelpInTranslation();
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

        setTextSearchSmall();
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
        loginHyperlink.setText(t.SignInToCollaborate());
    }

    public void setLogo(final Image logo) {
        // TODO
    }

    public void setSearchText(final String text) {
        searchTextBox.setText(text);
    }

    public void setTextSearchSmall() {
        searchTextBox.setWidth(SEARCH_TEXT_WIDTH_SMALL);
    }

    public void setTextSearchBig() {
        searchTextBox.setWidth(SEARCH_TEXT_WIDTH_BIG);
    }

    public void showLoggedUserName(final String name, final String homePage) {
        loginHyperlink.setVisible(false);
        loggedUserHyperlink.setVisible(true);
        loggedUserHyperlink.setText(name);
        loggedUserHyperlink.setTargetHistoryToken(homePage);
    }

    public void showLoginDialog() {
        final Login login = SiteBarFactory.getLoginForm(presenter);
        loginPanel = (LoginPanel) login.getView();
        loginPanel.show();
    }

    public void hideLoginDialog() {
        loginPanel.hide();
    }

    public void showNewGroupDialog() {
        final NewGroup newGroupForm = SiteBarFactory.getNewGroupForm(presenter);
        newGroupPanel = (NewGroupPanel) newGroupForm.getView();
        newGroupPanel.show();
        Site.hideProgress();
    }

    public void showSearchPanel(final String termToSearch) {
        final SearchSite search = SiteBarFactory.getSearch();
        search.doSearch(termToSearch);
        searchPanel = (SearchSitePanel) search.getView();
        searchPanel.show();
        Site.hideProgress();
    }

    public void hideNewGroupDialog() {
        newGroupPanel.hide();
    }

    public void setLogoutLinkVisible(final boolean visible) {
        logoutLabel.setVisible(visible);
        pipeSeparatorHtml2.setVisible(visible);
    }

    public void restoreLoginLink() {
        loginHyperlink.setVisible(true);
        loggedUserHyperlink.setVisible(false);
        loginHyperlink.setTargetHistoryToken(Site.LOGIN_TOKEN);
    }

    public void showProgress(final String text) {
        publicHP.setVisible(false);
        progressPanel.setVisible(true);
        DOM.setInnerText(progressText.getElement(), text);
    }

    public void hideProgress() {
        progressPanel.setVisible(false);
        publicHP.setVisible(true);
    }

    public void centerLoginDialog() {
        loginPanel.center();
    }

    public void centerNewGroupDialog() {
        newGroupPanel.center();
    }

    private void createListeners() {
        searchButton.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.doSearch(searchTextBox.getText());
            }
        });
        logoutLabel.addClickListener(new ClickListener() {
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

    public void setGroupsIsMember(final List groupsIsAdmin, final List groupsIsCollab) {
        optionsSubmenu.clearItems();
        yourGroupsSubmenu.clearItems();

        int isAdminCount = groupsIsAdmin.size();
        int isCollabCount = groupsIsCollab.size();
        if (isAdminCount > 0 || isCollabCount > 0) {
            optionsSubmenu.addItem(t.MyGroups() + " »", yourGroupsSubmenu);
            for (int i = 0; i < isAdminCount; i++) {
                final LinkDTO link = (LinkDTO) groupsIsAdmin.get(i);
                addItemToYourGroupSubmenu(link);
            }
            for (int i = 0; i < isCollabCount; i++) {
                final LinkDTO link = (LinkDTO) groupsIsCollab.get(i);
                addItemToYourGroupSubmenu(link);
            }
        }
        addDefaultItemsToOptions();
    }

    public void showAlertMessage(final String message) {
        MessageBox.alert(Kune.I18N.t("Alert"), message, new MessageBox.AlertCallback() {
            public void execute() {
                // Do nothing
            }
        });
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
