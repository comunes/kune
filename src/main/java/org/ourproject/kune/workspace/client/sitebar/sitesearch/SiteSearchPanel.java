package org.ourproject.kune.workspace.client.sitebar.sitesearch;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.SiteBar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SiteSearchPanel implements SiteSearchView {
    private static final String SEARCH_TEXT_WIDTH_SMALL = "120";
    private static final String SEARCH_TEXT_WIDTH_BIG = "180";

    private final PushButton searchButton;
    private final TextBox searchTextBox;
    private final I18nUITranslationService i18n;

    public SiteSearchPanel(final SiteSearchPresenter presenter, final WorkspaceSkeleton ws,
	    final I18nUITranslationService i18n) {
	this.i18n = i18n;
	final Images img = Images.App.getInstance();
	final SiteBar siteBar = ws.getSiteBar();
	siteBar.addSpacer();
	siteBar.addSpacer();
	searchButton = new PushButton(img.kuneSearchIco().createImage(), img.kuneSearchIcoPush().createImage());
	searchTextBox = new TextBox();

	siteBar.add(searchButton);
	siteBar.addSpacer();
	siteBar.add(searchTextBox);
	siteBar.addSpacer();

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

	searchButton.addClickListener(new ClickListener() {
	    public void onClick(final Widget arg0) {
		Site.showProgressProcessing();
		presenter.doSearch(searchTextBox.getText());
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

    public void clearSearchText() {
	searchTextBox.setText("");
    }

    public void setDefaultTextSearch() {
	searchTextBox.setText(i18n.t("Search"));
    }

    public void setSearchText(final String text) {
	searchTextBox.setText(text);
    }

    public void setTextSearchBig() {
	searchTextBox.setWidth(SEARCH_TEXT_WIDTH_BIG);
    }

    public void setTextSearchSmall() {
	searchTextBox.setWidth(SEARCH_TEXT_WIDTH_SMALL);
    }
}
