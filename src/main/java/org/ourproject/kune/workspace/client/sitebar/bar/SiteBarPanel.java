package org.ourproject.kune.workspace.client.sitebar.bar;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;
import org.ourproject.kune.workspace.client.newgroup.NewGroup;
import org.ourproject.kune.workspace.client.newgroup.ui.NewGroupPanel;
import org.ourproject.kune.workspace.client.search.SearchSite;
import org.ourproject.kune.workspace.client.search.ui.SearchSitePanel;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.sitebar.SiteBarFactory;
import org.ourproject.kune.workspace.client.sitebar.login.Login;
import org.ourproject.kune.workspace.client.sitebar.login.LoginPanel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.MessageBox;

public class SiteBarPanel extends Composite implements SiteBarView {

    private static final int MAX_TIME_PROGRESS_BAR = 20000;
    private static final String SEARCH_TEXT_WIDTH_SMALL = "120";
    private static final String SEARCH_TEXT_WIDTH_BIG = "180";
    private final SiteBarPresenter presenter;
    private final Image logoImage;

    private final HorizontalPanel siteBarHP;

    private final IconLabel gotoPublic;
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
    private MenuItem linkKuneBugs;
    private final MenuBar yourGroupsSubmenu;
    private NewGroupPanel newGroupPanel;
    private final IconLabel contentNoPublic;
    private final Widget progressPanel;
    private final Widget progressText;
    private final HorizontalPanel publicHP;
    private SearchSitePanel searchPanel;
    private final ExtElement extRootBody;
    private String publicUrl;
    private Timer timeProgressMaxTime;

    public SiteBarPanel(final SiteBarPresenter initPresenter) {
        img = Images.App.getInstance();

        // Initialize
        siteBarHP = new HorizontalPanel();

        initWidget(siteBarHP);
        this.presenter = initPresenter;

        progressPanel = RootPanel.get("kuneprogresspanel");
        progressText = RootPanel.get("kuneprogresstext");

        publicHP = new HorizontalPanel();
        gotoPublic = new IconLabel(img.anybody(), Kune.I18N.t("Go to Public Space"), false);
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
        showProgress(Kune.I18N.t("Processing"));
        gotoPublic.addStyleName("kune-Margin-Medium-r");
        setContentPublic(true);
        gotoPublic.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                gotoPublic();
            }
        });
        gotoPublic.setTitle(Kune.I18N.t("Leave the workspace and go to the public space of this group")
                + Site.IN_DEVELOPMENT);
        gotoPublic.addStyleName("kune-SiteBarPanel-LabelLink");
        contentNoPublic.addStyleName("kune-Margin-Medium-r");
        newGroupHyperlink.setText(Kune.I18N.t("Create New Group"));
        newGroupHyperlink.setTargetHistoryToken(Site.NEWGROUP_TOKEN);
        loggedUserHyperlink.setVisible(false);
        pipeSeparatorHtml.setHTML("|");
        pipeSeparatorHtml.setStyleName("kune-SiteBarPanel-Separator");
        pipeSeparatorHtml2.setHTML("|");
        pipeSeparatorHtml2.setStyleName("kune-SiteBarPanel-Separator");
        loginHyperlink.setText(Kune.I18N.t("Sign in to collaborate"));
        loginHyperlink.setTargetHistoryToken(Site.LOGIN_TOKEN);
        logoutLabel.setText(Kune.I18N.t("Sign out"));
        logoutLabel.addStyleName("kune-SiteBarPanel-LabelLink");
        options.addItem(Kune.I18N.t("Options"), true, optionsSubmenu);
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
        extRootBody = new ExtElement(RootPanel.getBodyElement());
    }

    public void setContentPublic(boolean visible) {
        gotoPublic.setVisible(visible);
        contentNoPublic.setVisible(!visible);
    }

    public void setContentGotoPublicUrl(final String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public void gotoPublic() {
        Window.open(publicUrl, "_blank", "");
    }

    private void createOptionsSubmenu() {
        linkHelpInTrans = new MenuItem(img.language().getHTML() + Kune.I18N.t("Help with the translation"), true,
                new Command() {
                    public void execute() {
                        presenter.onHelpInTranslation();
                    }
                });
        linkKuneBugs = new MenuItem(img.kuneIcon16().getHTML() + Kune.I18N.t("Report kune bugs"), true, new Command() {
            public void execute() {
                Window.open("http://code.google.com/p/kune/issues/list", "_blank", null);
            }
        });
        // linkHelp = new MenuItem(img.kuneIcon16().getHTML() +
        // Kune.I18N.t("Help"), true, new Command() {
        // public void execute() {
        // presenter.changeState(new StateToken("site.docs"));
        // }
        // });
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
        searchTextBox.setText(Kune.I18N.t("Search"));
    }

    public void clearSearchText() {
        searchTextBox.setText("");
    }

    public void clearUserName() {
        loginHyperlink.setText(Kune.I18N.t("Sign in to collaborate"));
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
        if (timeProgressMaxTime == null) {
            timeProgressMaxTime = new Timer() {
                public void run() {
                    hideProgress();
                }
            };
        }
        timeProgressMaxTime.schedule(MAX_TIME_PROGRESS_BAR);
        publicHP.setVisible(false);
        progressPanel.setVisible(true);
        DOM.setInnerText(progressText.getElement(), text);
    }

    public void hideProgress() {
        timeProgressMaxTime.cancel();
        progressPanel.setVisible(false);
        publicHP.setVisible(true);
    }

    public void centerLoginDialog() {
        loginPanel.center();
    }

    public void centerNewGroupDialog() {
        newGroupPanel.center();
    }

    public void mask() {
        extRootBody.mask();
    }

    public void mask(final String message) {
        extRootBody.mask(message, "x-mask-loading");
    }

    public void unMask() {
        extRootBody.unmask();
    }

    private void createListeners() {
        searchButton.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                Site.showProgressProcessing();
                presenter.doSearch(searchTextBox.getText());
            }
        });
        logoutLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                Site.showProgressProcessing();
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
                        Site.showProgressProcessing();
                        presenter.doSearch(searchTextBox.getText());
                    }
                }
            }
        });
    }

    public void setGroupsIsMember(final List<LinkDTO> groupsIsAdmin, final List<LinkDTO> groupsIsCollab) {
        optionsSubmenu.clearItems();
        yourGroupsSubmenu.clearItems();

        int isAdminCount = groupsIsAdmin.size();
        int isCollabCount = groupsIsCollab.size();
        if (isAdminCount > 0 || isCollabCount > 0) {
            optionsSubmenu.addItem(Kune.I18N.t("My Groups") + " »", yourGroupsSubmenu);
            for (int i = 0; i < isAdminCount; i++) {
                final LinkDTO link = groupsIsAdmin.get(i);
                addItemToYourGroupSubmenu(link);
            }
            for (int i = 0; i < isCollabCount; i++) {
                final LinkDTO link = groupsIsCollab.get(i);
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
        optionsSubmenu.addItem(linkKuneBugs);
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
