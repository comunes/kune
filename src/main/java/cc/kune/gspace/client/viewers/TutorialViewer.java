package cc.kune.gspace.client.viewers;

import java.util.List;

import cc.kune.common.client.log.Log;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.FileConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TutorialViewer extends Composite {

  interface TutorialViewerUiBinder extends UiBinder<Widget, TutorialViewer> {
  }

  private static final int FOOTBAR = 12;
  private static TutorialViewerUiBinder uiBinder = GWT.create(TutorialViewerUiBinder.class);
  private String defLang;
  @UiField
  public Frame frame;
  private final I18nUITranslationService i18n;
  private List<String> langs;
  private final Session session;

  @Inject
  public TutorialViewer(final I18nUITranslationService i18n, final Session session) {
    this.i18n = i18n;
    this.session = session;
    initWidget(uiBinder.createAndBindUi(this));
  }

  private String getTutorialLang() {
    if (langs == null) {
      langs = session.getInitData().getTutorialLanguages();
      defLang = session.getInitData().getDefTutorialLanguage();
    }
    final String currentLang = i18n.getCurrentLanguage();
    // We show the default tutorial lang is it's not translated (configured via
    // kune.properties)
    return langs.contains(currentLang) ? currentLang : defLang;
  }

  public void setHeigth(final Integer height) {
    if (height > FOOTBAR) {
      final String he = (height - FOOTBAR) + "px";
      frame.setWidth("100%");
      frame.setHeight(he);
      Log.info("Resizing to: " + height);
    }
  }

  public Widget show(final String tool) {
    final String currentLang = getTutorialLang();
    frame.setUrl(FileConstants.TUTORIALS_PREFIX + tool + ".svg" + "#" + currentLang);
    return this;
  }

}
