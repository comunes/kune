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
 */
package cc.kune.core.client.i18n;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.utils.Location;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class I18nUITranslationService extends I18nTranslationService {
  private I18nLanguageDTO currentLang;
  private String currentLanguageCode;
  private final I18nServiceAsync i18nService;
  private final KuneConstants kuneConstants;
  private HashMap<String, String> lexicon;
  private final Session session;
  private String siteCommonName;

  @Inject
  public I18nUITranslationService(final Session session, final I18nServiceAsync i18nService,
      final EventBus eventBus, final KuneConstants kuneConstants) {
    this.session = session;
    this.i18nService = i18nService;
    this.kuneConstants = kuneConstants;
    final Location loc = WindowUtils.getLocation();
    final String locale = loc.getParameter("locale");
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
        i18nService.getLexicon(currentLang.getCode(), new AsyncCallback<HashMap<String, String>>() {
          @Override
          public void onFailure(final Throwable caught) {
            Log.error("Workspace adaptation to your language failed:" + caught.getMessage());
          }

          @Override
          public void onSuccess(final HashMap<String, String> result) {
            lexicon = result;
            session.setCurrentLanguage(currentLang);
            eventBus.fireEvent(new I18nReadyEvent());
          }
        });
      }
    });
  }

  public void changeCurrentLanguage(final String newLanguage) {
    if (!newLanguage.equals(this.currentLanguageCode)) {
      setCurrentLanguage(newLanguage);
      changeLocale(newLanguage);
    }
  }

  /**
   * 
   * See in:
   * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread
   * /5e4e25050d3be984/7035ec39354d06aa?lnk=gst&q=get+locale&rnum=23
   * 
   * JSNI method to change the locale of the application - it effectively parses
   * the existing URL and creates a new one for the chosen locale.
   * 
   * It additionally launches any JavaScript debugger that might be attached to
   * the system (Windows only). To disable this functionality just remove the
   * "debugger" line.
   * 
   * @param newLocale
   *          String value of the new locale to go to.
   */
  private native void changeLocale(String newLocale)
  /*-{
		// Uncomment the "debugger;" line to see how to set debug statements in JSNI code
		// When in web mode, if your browser has a JavaScript debugger attached, it will
		// launch at this point in the code (when the user changes locale through the menu system).
		// debugger;

		// Get the current location
		var currLocation = $wnd.location.toString();
		// Get rid of any GWT History tokens that might be present
		var noHistoryCurrLocArray = currLocation.split("#");
		var noHistoryCurrLoc = noHistoryCurrLocArray[0];
		var currHistory = noHistoryCurrLocArray[1];
		// Get rid of any locale string
		var locArray = noHistoryCurrLoc.split("?");
		// Build the new href location and then send the browser there.
		// $wnd.location.href = locArray[0]+"?locale="+newLocale+"#"+currHistory;
		$wnd.location.href = locArray[0] + "?locale=" + newLocale

		// extjs part:
		// commented because the error: "Ext is not defined"
		// we have to try other way
		// var head = document.getElementsByTagName("head")[0];
		// var script = document.createElement('script');
		// script.id = 'localScript';
		// script.type = 'text/javascript';
		// script.src = "js/ext/locale/ext-lang-"+newLocale+".js";
		// head.appendChild(script);
  }-*/;

  public String formatDateWithLocale(final Date date) {
    return formatDateWithLocale(date, false);
  }

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

  public String getCurrentLanguage() {
    return currentLanguageCode;
  }

  public HashMap<String, String> getLexicon() {
    return lexicon;
  }

  public String getSiteCommonName() {
    if (siteCommonName == null) {
      siteCommonName = t(session.getSiteCommonName());
    }
    return siteCommonName;
  }

  public void init(final I18nServiceAsync i18nService, final Session session, final Listener0 onReady) {

  }

  private void save(final String text, final String noteForTranslators) {
    i18nService.getTranslation(session.getUserHash(), currentLanguageCode, text, noteForTranslators,
        new AsyncCallback<String>() {
          @Override
          public void onFailure(final Throwable caught) {
          }

          @Override
          public void onSuccess(final String result) {
          }
        });
  }

  public void setCurrentLanguage(final String newLanguage) {
    this.currentLanguageCode = newLanguage;
  }

  public void setLexicon(final HashMap<String, String> lexicon) {
    this.lexicon = lexicon;
  }

  public void setTranslationAfterSave(final String text, final String translation) {
    lexicon.put(text, translation);
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
   * @return text translated in the current language
   */
  @Override
  public String tWithNT(final String text, final String noteForTranslators) {
    final String encodeText = TextUtils.escapeHtmlLight(text);
    try {
      return kuneConstants.getString(I18nUtils.convertMethodName(text + " " + noteForTranslators));
    } catch (final MissingResourceException e) {
      if (lexicon == null) {
        Log.warn("i18n not initialized: " + text);
        return text;
      }
      String translation = lexicon.get(encodeText);
      if (lexicon.containsKey(encodeText)) {
        if (translation == UNTRANSLATED_VALUE) {
          // Not translated but in db, return text
          translation = encodeText;
        }
      } else {
        // Not translated and not in db, make a petition for translation
        if (session.isLogged()) {
          save(text, noteForTranslators);
          Log.debug("Registering in db '" + text + "' as pending translation");
          lexicon.put(encodeText, UNTRANSLATED_VALUE);
        }
        translation = encodeText;
      }
      return decodeHtml(translation);
    }
  }
}
