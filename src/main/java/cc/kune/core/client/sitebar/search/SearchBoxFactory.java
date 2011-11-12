package cc.kune.core.client.sitebar.search;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.shared.SearcherConstants;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class SearchBoxFactory {

  public static MultivalueSuggestBox create(final I18nUITranslationService i18n,
      final boolean searchOnlyUsers, final OnEntitySelectedInSearch callback) {
    final MultivalueSuggestBox multivalueSBox = new MultivalueSuggestBox(
        searchOnlyUsers ? SearcherConstants.USER_DATA_PROXY_URL : SearcherConstants.GROUP_DATA_PROXY_URL,
        false, new OnExactMatch() {

          @Override
          public void onExactMatch(final String match) {
            // NotifyUser.info(match);
          }
        }) {

      @Override
      public void onSelection(
          final com.google.gwt.event.logical.shared.SelectionEvent<com.google.gwt.user.client.ui.SuggestOracle.Suggestion> event) {
        super.onSelection(event);
        final Suggestion suggestion = event.getSelectedItem();
        if (suggestion instanceof OptionSuggestion) {
          final OptionSuggestion osugg = (OptionSuggestion) suggestion;
          final String value = osugg.getValue();
          if (!OptionSuggestion.NEXT_VALUE.equals(value)
              && !OptionSuggestion.PREVIOUS_VALUE.equals(value)) {
            callback.onSeleted(value);
            this.getSuggestBox().setValue("", false);
          }
        }
      };
    };
    final String siteCommonName = i18n.getSiteCommonName();
    final SuggestBox suggestBox = multivalueSBox.getSuggestBox();
    Tooltip.to(
        suggestBox,
        searchOnlyUsers ? i18n.t("Type something to search for users in [%s]", siteCommonName) : i18n.t(
            "Type something to search for users and groups in [%s]", siteCommonName));
    suggestBox.getTextBox().setDirection(i18n.isRTL() ? Direction.RTL : Direction.LTR);
    return multivalueSBox;
  }

}
