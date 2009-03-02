/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 \*/
package org.ourproject.kune.workspace.client.sitebar.sitesearch;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.SimpleToolbar;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SiteSearchPanel implements SiteSearchView {
    private static final int SEARCH_TEXT_HEIGHT = 15;
    private static final int SEARCH_TEXT_WIDTH_SMALL = 120;
    private static final int SEARCH_TEXT_WIDTH_BIG = 180;
    public static final String SITE_SEARCH_BUTTON = "kune-ssp-searchbt";
    public static final String SITE_SEARCH_TEXTBOX = "kune-ssp-tbox";

    private final PushButton searchButton;
    private final TextBox searchTextBox;
    private final SimpleToolbar siteBar;

    public SiteSearchPanel(final SiteSearchPresenter presenter, final WorkspaceSkeleton ws, Images img) {
        siteBar = ws.getSiteBar();
        siteBar.addSpacer();
        searchButton = new PushButton(img.kuneSearchIco().createImage(), img.kuneSearchIcoPush().createImage());
        searchButton.ensureDebugId(SITE_SEARCH_BUTTON);
        searchTextBox = new TextBox();
        searchTextBox.ensureDebugId(SITE_SEARCH_TEXTBOX);

        siteBar.add(searchButton);
        siteBar.addSpacer();
        siteBar.add(searchTextBox);

        setTextSearchSmall();
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
                NotifyUser.showProgressProcessing();
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
                        NotifyUser.showProgressProcessing();
                        presenter.doSearch(searchTextBox.getText());
                    }
                }
            }
        });
    }

    public void clearSearchText() {
        searchTextBox.setText("");
    }

    public void selectSearchText() {
        searchTextBox.selectAll();
    }

    public void setSearchText(final String text) {
        searchTextBox.setText(text);
    }

    public void setTextSearch(String text) {
        searchTextBox.setText(text);
    }

    public void setTextSearchBig() {
        searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_BIG, 15);
    }

    public void setTextSearchSmall() {
        searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_SMALL, SEARCH_TEXT_HEIGHT);
    }
}
