/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.sitebar.search.MultivalueSuggestBox;
import cc.kune.core.client.sitebar.search.OnEntitySelectedInSearch;
import cc.kune.core.client.sitebar.search.SearchBoxFactory;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ShareToOthersPanel extends Composite implements ShareToOthersView {

  public static final String SEARCH_TEXTBOX_ID = "stop-textbox";

  @Inject
  public ShareToOthersPanel(final I18nUITranslationService i18n) {
    final FlowPanel flow = new FlowPanel();
    flow.addStyleName("k-share-others");

    final MultivalueSuggestBox multivalueSBox = SearchBoxFactory.create(i18n, false, true,
        SEARCH_TEXTBOX_ID, new OnEntitySelectedInSearch() {
          @Override
          public void onSeleted(final String shortName) {
            // TODO
            NotifyUser.info("Selected: " + shortName);
          }
        });
    final SuggestBox suggestBox = multivalueSBox.getSuggestBox();
    final ValueBoxBase<String> searchTextBox = suggestBox.getValueBox();
    final Label suggestBoxIntro = new Label(I18n.t("drag and drop to add people or"));
    final Label suggestTextWhenEmpty = new Label(I18n.t("search users or groups to add"));

    flow.add(suggestBoxIntro);
    flow.add(multivalueSBox);
    flow.add(suggestTextWhenEmpty);

    multivalueSBox.addStyleName("k-share-searchbox");
    suggestTextWhenEmpty.addStyleName("k-share-searchbox-text");
    suggestTextWhenEmpty.addStyleName("k-clean");

    initWidget(flow);

    /* Search box settings */
    suggestTextWhenEmpty.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        suggestBox.setFocus(true);
      }
    });
    searchTextBox.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(final FocusEvent event) {
        // searchLabel.setVisible(false);
        suggestTextWhenEmpty.getElement().getStyle().setVisibility(Visibility.HIDDEN);
      }
    });
    searchTextBox.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        if (searchTextBox.getValue().isEmpty()) {
          suggestTextWhenEmpty.getElement().getStyle().setVisibility(Visibility.VISIBLE);
        }
      }
    });

    /* Tooltips */
    Tooltip.to(suggestBox, I18n.t("type something to search and add users or groups in this site"));
    Tooltip.to(suggestTextWhenEmpty,
        I18n.t("type something to search and add users or groups in this site"));
    Tooltip.to(suggestBoxIntro, I18n.t("type something to search and add users or groups in this site"));
  }

}
