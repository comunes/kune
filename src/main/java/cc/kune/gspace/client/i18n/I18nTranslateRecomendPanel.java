package cc.kune.gspace.client.i18n;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class I18nTranslateRecomendPanel extends Composite {

  private final Frame recommFrame;
  private final Label tabTitle;

  @Inject
  public I18nTranslateRecomendPanel(final I18nTranslationService i18n) {
    tabTitle = new Label(i18n.t("Recommendations"));
    recommFrame = new Frame("ws/i18n-recom.html");
    // recommFrame.setHeight("auto");
    recommFrame.setStyleName("k-i18n-recommend");
    initWidget(recommFrame);
  }

  public IsWidget getTabTitle() {
    return tabTitle;
  }

  public void setSize(final int width, final int height) {
    final String widthS = width + "px";
    final String heightS = height + "px";
    recommFrame.setWidth(widthS);
    recommFrame.setHeight(heightS);
    // super.setSize(widthS, heightS);
  }
}
