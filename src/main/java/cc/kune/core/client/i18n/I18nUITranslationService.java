/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
 */
package cc.kune.core.client.i18n;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Set;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.MetaUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.Pair;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteParameters;
import cc.kune.core.shared.dto.I18nLanguageDTO;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nUITranslationService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class I18nUITranslationService extends I18nTranslationService {

  /**
   * The Interface I18nLanguageChangeNeeded.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface I18nLanguageChangeNeeded {

    /**
     * On change needed.
     */
    void onChangeNeeded();

    /**
     * On change not needed.
     */
    void onChangeNotNeeded();
  }

  /** The current lang. */
  private I18nLanguageDTO currentLang;

  /** The current language code. */
  private String currentLanguageCode;

  /** The early texts. */
  private final Set<Pair<String, String>> earlyTexts;

  /** The i18n service. */
  private final I18nServiceAsync i18nService;

  /** The is current lang rtl. */
  private boolean isCurrentLangRTL = false;

  /** The is lang in properties. */
  private boolean isLangInProperties;

  /** The kune constants. */
  private final KuneConstants kuneConstants;

  /** The lexicon. */
  private HashMap<String, String> lexicon;

  /** The session. */
  private final Session session;

  /** The site common name. */
  private String siteCommonName;

  /**
   * Instantiates a new i18n ui translation service.
   * 
   * @param session
   *          the session
   * @param i18nService
   *          the i18n service
   * @param eventBus
   *          the event bus
   * @param kuneConstants
   *          the kune constants
   */
  @Inject
  public I18nUITranslationService(final Session session, final I18nServiceAsync i18nService,
      final EventBus eventBus, final KuneConstants kuneConstants) {
    this.session = session;
    this.i18nService = i18nService;
    this.kuneConstants = kuneConstants;
    final String locale = WindowUtils.getParameter(SiteParameters.LOCALE);
    final LocaleInfo currentLocale = LocaleInfo.getCurrentLocale();
    Log.info("Workspace starting with language: " + currentLocale.getLocaleName() + ", isRTL: "
        + LocaleInfo.getCurrentLocale().isRTL() + ", translated langs: "
        + Arrays.toString(LocaleInfo.getAvailableLocaleNames()));
    isLangInProperties = isInConstantProperties(currentLocale.getLocaleName());
    earlyTexts = new HashSet<Pair<String, String>>();

    i18nService.getInitialLanguage(locale, new AsyncCallback<I18nLanguageDTO>() {

      @Override
      public void onFailure(final Throwable caught) {
        Log.error("Workspace adaptation to your language failed: " + caught.getMessage());
      }

      @Override
      public void onSuccess(final I18nLanguageDTO result) {
        currentLang = result;
        currentLanguageCode = currentLang.getCode();
        session.setCurrentLanguage(currentLang);
        isLangInProperties = isInConstantProperties(currentLang.getCode());
        i18nService.getLexicon(currentLang.getCode(), new AsyncCallback<HashMap<String, String>>() {
          @Override
          public void onFailure(final Throwable caught) {
            Log.error("Workspace adaptation to server proposed language failed: " + caught.getMessage());
          }

          @Override
          public void onSuccess(final HashMap<String, String> result) {
            lexicon = result;
            session.setCurrentLanguage(currentLang);
            Log.info("Workspace adaptation to server proposed language: " + currentLang.getEnglishName()
                + ", isRTL: " + currentLang.getDirection() + " use properties: "
                + shouldIuseProperties());

            changeToLanguageIfNecessary(getCurrentGWTlanguage(), currentLang.getCode(),
                currentLang.getEnglishName(), false, new I18nLanguageChangeNeeded() {

                  @Override
                  public void onChangeNeeded() {
                  }

                  @Override
                  public void onChangeNotNeeded() {
                    isCurrentLangRTL = currentLang.getDirection().equals(RTL);
                    eventBus.fireEvent(new I18nReadyEvent());
                    I18nStyles.setRTL(isCurrentLangRTL);
                  }
                });
          }
        });

        session.onUserSignIn(true, new UserSignInHandler() {
          @Override
          public void onUserSignIn(final UserSignInEvent event) {
            Scheduler.get().scheduleIncremental(new RepeatingCommand() {
              @Override
              public boolean execute() {
                if (!earlyTexts.isEmpty()) {
                  final Pair<String, String> pair = earlyTexts.iterator().next();
                  save(pair.getLeft(), pair.getRight());
                  earlyTexts.remove(pair);
                }
                return !earlyTexts.isEmpty();
              }
            });
          }
        });
      }
    });
  }

  /**
   * See in:
   * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread
   * /5e4e25050d3be984/7035ec39354d06aa?lnk=gst&q=get+locale&rnum=23
   * 
   * JSNI method to change the locale of the application - it effectively parses
   * the existing URL and creates a new one for the chosen locale.
   * 
   * @param newLocale
   *          String value of the new locale to go to.
   */
  private void changeLanguageInUrl(final String newLocale) {
    final String hash = WindowUtils.getHash();
    final String query = WindowUtils.getQueryString();
    final String path = WindowUtils.getPath();
    final String protocol = WindowUtils.getProtocol();
    final String newUrl = I18nUrlUtils.changeLang(query + (TextUtils.notEmpty(hash) ? hash : ""),
        newLocale);
    Log.info("Locale current query: " + query);
    Log.info("Locale current hash: " + hash);
    Log.info("Locale current path: " + path);
    Log.info("Locale new Url: " + path + newUrl);
    WindowUtils.changeHrefKeepHash(protocol + "//" + WindowUtils.getHost() + path + newUrl);
  }

  /**
   * Change to language if necessary.
   * 
   * @param wantedLang
   *          the wanted lang
   * @param wantedLangEnglishName
   *          the wanted lang english name
   * @param listener
   *          the listener
   */
  public void changeToLanguageIfNecessary(final String wantedLang, final String wantedLangEnglishName,
      final boolean ask, final I18nLanguageChangeNeeded listener) {
    changeToLanguageIfNecessary(currentLang.getCode(), wantedLang, wantedLangEnglishName, ask, listener);
  }

  /**
   * Change to language if necessary.
   * 
   * @param currentLangCode
   *          the current lang code
   * @param wantedLang
   *          the wanted lang
   * @param wantedLangEnglishName
   *          the wanted lang english name
   * @param listener
   *          the listener
   * @return true if we should reload the client with the new language
   */
  private void changeToLanguageIfNecessary(final String currentLangCode, final String wantedLang,
      final String wantedLangEnglishName, final boolean ask, final I18nLanguageChangeNeeded listener) {
    if (!currentLangCode.equals(wantedLang) && isInConstantProperties(wantedLang)) {
      if (!ask) {
        listener.onChangeNeeded();
        setCurrentLanguage(wantedLang);
        changeLanguageInUrl(wantedLang);
      } else {
        NotifyUser.askConfirmation(t("Confirm please"),
            t("Do you want to reload this page to use '[%s]' language?", wantedLangEnglishName),
            new SimpleResponseCallback() {
              @Override
              public void onCancel() {
                // User no accepted to change the language...
                listener.onChangeNotNeeded();
              }

              @Override
              public void onSuccess() {
                // User accepted to change the language...
                listener.onChangeNeeded();
                setCurrentLanguage(wantedLang);
                changeLanguageInUrl(wantedLang);
              }
            });
      }
    } else {
      listener.onChangeNotNeeded();
    }
  }

  /**
   * Format date with locale.
   * 
   * @param date
   *          the date
   * @return the string
   */
  public String formatDateWithLocale(final Date date) {
    return formatDateWithLocale(date, false);
  }

  /**
   * Format date with locale.
   * 
   * @param date
   *          the date
   * @param shortFormat
   *          the short format
   * @return the string
   */
  public String formatDateWithLocale(final Date date, final boolean shortFormat) {
    String dateFormat = shortFormat ? currentLang.getDateFormatShort() : currentLang.getDateFormat();

    final DateTimeFormat fmt;
    if (dateFormat == null) {
      fmt = DateTimeFormat.getFormat("M/d/yyyy h:mm a");
    } else {
      if (shortFormat) {
        fmt = DateTimeFormat.getFormat(dateFormat + " h:mm a");
      } else {
        final String abrevMonthInEnglish = DateTimeFormat.getFormat("MMM").format(date);
        final String monthToTranslate = abrevMonthInEnglish + " [%NT abbreviated month]";
        dateFormat = dateFormat.replaceFirst("MMM", "'" + t(monthToTranslate) + "'");
        fmt = DateTimeFormat.getFormat(dateFormat + " h:mm a");
      }
    }
    final String dateFormated = fmt.format(date);
    return dateFormated;
  }

  /**
   * Gets the current gw tlanguage.
   * 
   * @return the current gw tlanguage
   */
  private String getCurrentGWTlanguage() {
    String gwtLang = LocaleInfo.getCurrentLocale().getLocaleName();
    gwtLang = gwtLang.equals("default") ? "en" : gwtLang;
    return gwtLang;
  }

  /**
   * Gets the current language.
   * 
   * @return the current language
   */
  public String getCurrentLanguage() {
    return currentLanguageCode;
  }

  /**
   * Gets the lexicon.
   * 
   * @return the lexicon
   */
  public HashMap<String, String> getLexicon() {
    return lexicon;
  }

  /**
   * Gets the site common name.
   * 
   * @return the site common name
   */
  @Override
  public String getSiteCommonName() {
    if (siteCommonName == null) {
      final String meta = MetaUtils.get("kune.default.site.commonname");
      siteCommonName = (meta == null ? t("this site") : t(meta));
    }
    return siteCommonName;
  }

  /**
   * Gets the trans from bd.
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @param encodeText
   *          the encode text
   * @return the trans from bd
   */
  private String getTransFromBD(final String text, final String noteForTranslators,
      final String encodeText) {
    if (lexicon == null) {
      Log.info("i18n not initialized: " + text);
      earlyTexts.add(Pair.create(text, noteForTranslators));
      Log.info("i18n pending translations: " + earlyTexts.size());
      return text;
    }
    String translation = lexicon.get(encodeText);
    if (lexicon.containsKey(encodeText)) {
      if (translation == UNTRANSLATED_VALUE) {
        // Not translated but in db, return text
        translation = encodeText;
      }
    } else {
      // Not translated and not in db, make a petition for translation (if
      // enabled)
      if (session.isLogged() && session.getInitData().getStoreUntranslatedStrings()) {
        save(text, noteForTranslators);
        lexicon.put(encodeText, UNTRANSLATED_VALUE);
      } else {
        earlyTexts.add(Pair.create(text, noteForTranslators));
      }
      translation = encodeText;
    }
    return decodeHtml(translation);
  }

  /**
   * Checks if is in constant properties.
   * 
   * @param currentLang
   *          the current lang
   * @return true, if is in constant properties
   */
  private boolean isInConstantProperties(final String currentLang) {
    for (final String lang : LocaleInfo.getAvailableLocaleNames()) {
      if (lang.equals(currentLang)) {
        Log.info("Workspace adaptation to language: " + currentLang + " is in KuneConstants*properties");
        return true;
      }
    }
    Log.info("Workspace adaptation to language: " + currentLang + " is not in KuneConstants*properties");
    return false;
  }

  /**
   * Checks if is ready.
   * 
   * @return true, if is ready
   */
  public boolean isReady() {
    return lexicon != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.shared.i18n.I18nTranslationService#isRTL()
   */
  @Override
  public boolean isRTL() {
    return isCurrentLangRTL;
  }

  /**
   * Save.
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   */
  private void save(final String text, final String noteForTranslators) {
    i18nService.getTranslation(session.getUserHash(), currentLanguageCode, text, noteForTranslators,
        new AsyncCallback<String>() {
          @Override
          public void onFailure(final Throwable caught) {
          }

          @Override
          public void onSuccess(final String result) {
            Log.debug("Registered in db '" + text + "' as pending translation");
          }
        });
  }

  /**
   * Sets the current language.
   * 
   * @param newLanguage
   *          the new current language
   */
  public void setCurrentLanguage(final String newLanguage) {
    this.currentLanguageCode = newLanguage;
  }

  /**
   * Sets the lexicon.
   * 
   * @param lexicon
   *          the lexicon
   */
  public void setLexicon(final HashMap<String, String> lexicon) {
    this.lexicon = lexicon;
  }

  /**
   * Sets the translation after save.
   * 
   * @param text
   *          the text
   * @param translation
   *          the translation
   */
  public void setTranslationAfterSave(final String text, final String translation) {
    lexicon.put(text, translation);
  }

  /**
   * Should iuse properties.
   * 
   * @return true, if successful
   */
  private boolean shouldIuseProperties() {
    return isLangInProperties;
  }

  /**
   * In production, this method uses a hashmap. In development, if the text is
   * not in the hashmap, it makes a server petition (that stores the text
   * pending for translation in db).
   * 
   * Warning: text is escaped as html before insert in the db. Don't use html
   * here (o user this method with params).
   * 
   * @param text
   *          the text
   * @param noteForTranslators
   *          the note for translators
   * @return text translated in the current language
   */
  @Override
  public String tWithNT(final String text, final String noteForTranslators) {
    if (TextUtils.empty(text)) {
      return text;
    }
    final String encodeText = TextUtils.escapeHtmlLight(text);

    if (shouldIuseProperties()) {
      // The db translations now are in properties files (more stable)
      try {
        return kuneConstants.getString(I18nUtils.convertMethodName(text, noteForTranslators));
      } catch (final MissingResourceException e) {
        // Ok this concrete translation is not yet available, we use DB
        return getTransFromBD(text, noteForTranslators, encodeText);
      }
    } else {
      return getTransFromBD(text, noteForTranslators, encodeText);
    }
  }
}
