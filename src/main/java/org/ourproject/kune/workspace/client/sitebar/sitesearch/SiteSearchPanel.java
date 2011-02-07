/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class SiteSearchPanel implements SiteSearchView {
    private static final int SEARCH_TEXT_HEIGHT = 15;
    private static final int SEARCH_TEXT_WIDTH_SMALL = 120;
    private static final int SEARCH_TEXT_WIDTH_BIG = 180;
    public static final String SITE_SEARCH_BUTTON = "kune-ssp-searchbt";
    public static final String SITE_SEARCH_TEXTBOX = "kune-ssp-tbox";

    private final PushButton searchButton;
    private final TextBox searchTextBox;
    private final AbstractToolbar siteBar;

    public SiteSearchPanel(final SiteSearchPresenter presenter, final WorkspaceSkeleton ws, final Images img) {
        siteBar = ws.getSiteBar();
        siteBar.addSpacer();
        searchButton = new PushButton(img.kuneSearchIco().createImage(), img.kuneSearchIcoPush().createImage());
        searchButton.ensureDebugId(SITE_SEARCH_BUTTON);
        searchTextBox = new TextBox();
        searchTextBox.ensureDebugId(SITE_SEARCH_TEXTBOX);

        siteBar.add(searchButton);
        siteBar.addSpacer();
        siteBar.add(searchTextBox);

        setTextSearchSmallImpl();
        searchTextBox.addBlurHandler(new BlurHandler() {
            public void onBlur(final BlurEvent event) {
                presenter.onSearchLostFocus(searchTextBox.getText());
            }
        });
        searchTextBox.addFocusHandler(new FocusHandler() {
            public void onFocus(final FocusEvent event) {
                presenter.onSearchFocus();
            }
        });
        searchButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                NotifyUser.showProgressProcessing();
                presenter.doSearch(searchTextBox.getText());
            }
        });
        searchTextBox.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(final KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
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

    public void setTextSearch(final String text) {
        searchTextBox.setText(text);
    }

    public void setTextSearchBig() {
        searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_BIG, 15);
    }

    public void setTextSearchSmall() {
        setTextSearchSmallImpl();
    }

    private void setTextSearchSmallImpl() {
        searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_SMALL, SEARCH_TEXT_HEIGHT);
    }
}
