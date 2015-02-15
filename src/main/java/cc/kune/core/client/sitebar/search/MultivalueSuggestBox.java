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
/*******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Source: http://www.zackgrossbart.com/hackito/gwt-rest-auto/
 *
 ******************************************************************************/
package cc.kune.core.client.sitebar.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.dto.GroupType;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

// TODO: Auto-generated Javadoc
/**
 * A SuggestBox that uses REST and allows for multiple values, autocomplete and
 * browsing.
 * 
 * @author Bess Siegal <bsiegal@novell.com>
 */
public class MultivalueSuggestBox extends Composite implements SelectionHandler<Suggestion>, Focusable,
    KeyUpHandler {

  /**
   * Bean for name-value pairs.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  private class Option {

    private String mname;
    private String mvalue;

    /**
     * No argument constructor
     */
    public Option() {
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
      return mname;
    }

    /**
     * @return Returns the value.
     */
    public String getValue() {
      return mvalue;
    }

    /**
     * @param name
     *          The name to set.
     */
    public void setName(final String name) {
      mname = name;
    }

    /**
     * @param value
     *          The value to set.
     */
    public void setValue(final String value) {
      mvalue = value;
    }

  }
  /**
   * An abstract class that handles success and error conditions from the REST
   * call
   */
  private abstract class OptionQueryCallback {
    abstract void error(Throwable exception);

    abstract void success(OptionResultSet optResults);
  }
  /**
   * Bean for total size and options
   */
  protected class OptionResultSet {
    /** JSON key for DisplayName */
    public static final String DISPLAY_NAME = "longName";
    /** JSON key for Options */
    public static final String OPTIONS = "list";
    /** JSON key for the size of the Results */
    public static final String TOTAL_SIZE = "size";

    /** JSON key for Value */
    public static final String VALUE = "shortName";

    private final List<Option> m_options = new ArrayList<Option>();
    private int mtotalSize;

    /**
     * Constructor. Must pass in the total size.
     * 
     * @param totalSize
     *          the total size of the template
     */
    public OptionResultSet(final int totalSize) {
      setTotalSize(totalSize); // NOPMD by vjrj on 4/05/11 19:45
    }

    /**
     * Add an option
     * 
     * @param option
     *          - the Option to add
     */
    public void addOption(final Option option) {
      m_options.add(option);
    }

    /**
     * @return an array of Options
     */
    public Option[] getOptions() {
      return m_options.toArray(new Option[m_options.size()]);
    }

    /**
     * @return Returns the totalSize.
     */
    public int getTotalSize() {
      return mtotalSize;
    }

    /**
     * @param totalSize
     *          The totalSize to set.
     */
    public void setTotalSize(final int totalSize) {
      mtotalSize = totalSize;
    }
  }
  /**
   * A bean to serve as a custom suggestion so that the value is available and
   * the replace will look like it is supporting multivalues
   */
  class OptionSuggestion implements SuggestOracle.Suggestion {
    static final String NEXT_VALUE = "NEXT";
    static final String PREVIOUS_VALUE = "PREVIOUS";
    private String mdisplay;
    private String mname;
    private final String mreplace;
    private final String mvalue;

    /**
     * Constructor for navigation options
     * 
     * @param nav
     *          - next or previous value
     * @param currentTextValue
     *          - the current contents of the text box
     */
    OptionSuggestion(final String nav, final String currentTextValue) {
      if (NEXT_VALUE.equals(nav)) {
        mdisplay = "<div class=\"autocompleterNext\" title=\"Next\"></div>";
      } else {
        mdisplay = "<div class=\"autocompleterPrev\" title=\"Previous\"></div>";
      }
      mreplace = currentTextValue;
      mvalue = nav;
    }

    /**
     * Constructor for regular options
     * 
     * @param displ
     *          - the name of the option
     * @param val
     *          - the value of the option
     * @param replacePre
     *          - the current contents of the text box
     * @param query
     *          - the query
     */
    OptionSuggestion(final String displ, final String val, final String replacePre, final String query) {
      mname = displ;
      final int begin = displ.toLowerCase().indexOf(query.toLowerCase());
      if (begin >= 0) {
        final int end = begin + query.length();
        final String match = displ.substring(begin, end);
        mdisplay = displ.replaceFirst(match, "<b>" + match + "</b>");
      } else {
        // may not necessarily be a part of the query, for example if "*" was
        // typed.
        mdisplay = displ;
      }
      mreplace = getFullReplaceText(displ, replacePre);
      mvalue = val;
    }

    @Override
    public String getDisplayString() {
      return mdisplay;
    }

    /**
     * Get the name of the option. (when not multivalued, this will be the same
     * as getReplacementString)
     * 
     * @return name
     */
    public String getName() {
      return mname;
    }

    @Override
    public String getReplacementString() {
      return mreplace;
    }

    /**
     * Get the value of the option
     * 
     * @return value
     */
    public String getValue() {
      return mvalue;
    }
  }
  /**
   * A custom callback that has the original SuggestOracle.Request and
   * SuggestOracle.Callback
   */
  private class RestSuggestCallback extends OptionQueryCallback {
    private final SuggestOracle.Callback m_callback;
    private final String m_query; // this may be different from
                                  // m_request.getQuery when multivalued it's
                                  // only the substring after the last delimiter
    private final SuggestOracle.Request m_request;

    RestSuggestCallback(final Request request, final Callback callback, final String query) {
      m_request = request;
      m_callback = callback;
      m_query = query;
    }

    @Override
    public void error(final Throwable exception) {
      updateFormFeedback(FormFeedback.ERROR, "Invalid: " + m_query);
    }

    @Override
    public void success(final OptionResultSet optResults) {
      final SuggestOracle.Response resp = new SuggestOracle.Response();
      final List<OptionSuggestion> suggs = new ArrayList<OptionSuggestion>();
      final int totSize = optResults.getTotalSize();

      if (totSize < 1) {
        // if there were no suggestions, then it's an invalid value
        updateFormFeedback(FormFeedback.ERROR, "Invalid: " + m_query);
        if (showNoResult) {
          final OptionSuggestion sugg = new OptionSuggestion(i18n.t("No results"), "#",
              m_request.getQuery(), m_query);
          suggs.add(sugg);
        }
      } else if (false && totSize == 1) {
        // Patch to show always the suggestions
        // it's an exact match, so do not bother with showing suggestions,
        final Option o = optResults.getOptions()[0];
        final String displ = o.getName();

        // remove the last bit up to separator
        // mfield.setText(getFullReplaceText(displ, m_request.getQuery()));

        Log.info("RestSuggestCallback.success! exact match found for displ = " + displ);
        // onExactMatch.onExactMatch(o.getValue());
        // it's valid!
        updateFormFeedback(FormFeedback.VALID, null);

        // set the value into the valueMap
        putValue(displ, o.getValue());

      } else {
        // more than 1 so show the suggestions

        // if not at the first page, show PREVIOUS
        if (mindexFrom > 0) {
          final OptionSuggestion prev = new OptionSuggestion(OptionSuggestion.PREVIOUS_VALUE,
              m_request.getQuery());
          suggs.add(prev);
        }

        // show the suggestions
        for (final Option o : optResults.getOptions()) {
          final OptionSuggestion sugg = new OptionSuggestion(o.getName(), o.getValue(),
              m_request.getQuery(), m_query);
          suggs.add(sugg);
        }

        // if there are more pages, show NEXT
        if (mindexTo < totSize) {
          final OptionSuggestion next = new OptionSuggestion(OptionSuggestion.NEXT_VALUE,
              m_request.getQuery());
          suggs.add(next);
        }

        // nothing has been picked yet, so let the feedback show an error
        // (unsaveable)
        updateFormFeedback(FormFeedback.ERROR, "Invalid: " + m_query);
      }

      // it's ok (and good) to pass an empty suggestion list back to the suggest
      // box's callback method
      // the list is not shown at all if the list is empty.
      resp.setSuggestions(suggs);
      m_callback.onSuggestionsReady(m_request, resp);
    }

  }
  /*
   * Some custom inner classes for our SuggestOracle
   */
  /**
   * A custom Suggest Oracle
   */
  private class RestSuggestOracle extends SuggestOracle {
    private SuggestOracle.Callback mcallback;
    private SuggestOracle.Request mrequest;
    private final Timer mtimer;

    RestSuggestOracle() {
      mtimer = new Timer() {

        @Override
        public void run() {
          /*
           * The reason we check for empty string is found at
           * http://development.lombardi.com/?p=39 -- paraphrased, if you
           * backspace quickly the contents of the field are emptied but a query
           * for a single character is still executed. Workaround for this is to
           * check for an empty string field here.
           */

          if (!mfield.getText().trim().isEmpty()) {
            if (misMultivalued) {
              // calling this here in case a user is trying to correct the "kev"
              // value of Allison Andrews, Kev, Josh Nolan or pasted in multiple
              // values
              findExactMatches();
            }
            getSuggestions();
          }
        }
      };
    }

    private void getSuggestions() {
      String query = mrequest.getQuery();

      // find the last thing entered up to the last separator
      // and use that as the query
      if (misMultivalued) {
        final int sep = query.lastIndexOf(DISPLAY_SEPARATOR);
        if (sep > 0) {
          query = query.substring(sep + DISPLAY_SEPARATOR.length());
        }
      }
      query = query.trim();

      // do not query if it's just an empty String
      // also do not get suggestions you've already got an exact match for this
      // string in the m_valueMap
      if (query.length() > 0 && mvalueMap.get(query) == null) {
        // JSUtil.println("getting Suggestions for: " + query);
        updateFormFeedback(FormFeedback.LOADING, null);
        queryOptions(query, mindexFrom, mindexTo, new RestSuggestCallback(mrequest, mcallback, query));
      }
    }

    @Override
    public boolean isDisplayStringHTML() {
      return true;
    }

    @Override
    public void requestSuggestions(final SuggestOracle.Request request,
        final SuggestOracle.Callback callback) {
      // This is the method that gets called by the SuggestBox whenever some
      // types into the text field
      mrequest = request;
      mcallback = callback;

      // reset the indexes (b/c NEXT and PREV call getSuggestions directly)
      resetPageIndices();

      // If the user keeps triggering this event (e.g., keeps typing), cancel
      // and restart the timer
      mtimer.cancel();
      mtimer.schedule(DELAY);
    }
  }
  private static final int DELAY = 500;

  private static final String DISPLAY_SEPARATOR = ", ";
  private static final int FIND_EXACT_MATCH_QUERY_LIMIT = 20;
  private static final int PAGE_SIZE = 15;
  private static final String VALUE_DELIM = ";";

  /**
   * Returns a String without the last delimiter
   * 
   * @param str
   *          - String to trim
   * @param delim
   *          - the delimiter
   * @return the String without the last delimter
   */
  private static String trimLastDelimiter(String str, final String delim) { // NOPMD
                                                                            // by
                                                                            // vjrj
                                                                            // on
                                                                            // 4/05/11
                                                                            // 19:46
    if (str.length() > 0) {
      str = str.substring(0, str.length() - delim.length());
    }
    return str;
  }

  private final I18nTranslationService i18n;
  private com.google.gwt.http.client.Request lastQuery;
  private final FormFeedback mfeedback;

  private final SuggestBox mfield;

  private int mfindExactMatchesFound = 0;

  private final ArrayList<String> mfindExactMatchesNot = new ArrayList<String>();

  private int mfindExactMatchesTotal = 0;

  private int mindexFrom = 0;

  private int mindexTo = 0;
  private boolean misMultivalued = false;

  private String mrestEndpointUrl;

  private final Map<String, String> mvalueMap;

  private final boolean showNoResult;

  // private final OnExactMatch onExactMatch;

  /**
   * Constructor.
   * 
   * @param i18n
   * 
   * @param the
   *          URL for the REST endpoint. This URL should accept the parameters q
   *          (for query), indexFrom and indexTo
   * @param isMultivalued
   *          - true for allowing multiple values
   * @param onExactMatch
   * @param showNoResult
   *          if we have to show noResult message when the search is empty or
   *          not
   */
  public MultivalueSuggestBox(final I18nTranslationService i18n, final boolean showNoResult,
      final String restEndpointUrl, final boolean isMultivalued, final OnExactMatch onExactMatch) {
    this.i18n = i18n;
    this.showNoResult = showNoResult;
    mrestEndpointUrl = restEndpointUrl;
    misMultivalued = isMultivalued;
    // this.onExactMatch = onExactMatch;

    final FlowPanel panel = new FlowPanel();
    TextBoxBase textfield;
    if (isMultivalued) {
      panel.addStyleName("textarearow");
      textfield = new TextArea();
    } else {
      panel.addStyleName("textfieldrow");
      textfield = new TextBox();
    }

    // Create our own SuggestOracle that queries REST endpoint
    final SuggestOracle oracle = new RestSuggestOracle();
    // intialize the SuggestBox
    mfield = new SuggestBox(oracle, textfield);
    if (isMultivalued) {
      // have to do this here b/c gwt suggest box wipes
      // style name if added in previous if
      textfield.addStyleName("multivalue");
    }
    mfield.addStyleName("wideTextField");
    mfield.addSelectionHandler(this);
    mfield.addKeyUpHandler(this);

    panel.add(mfield);
    mfeedback = new FormFeedback();
    // panel.add(mfeedback);

    initWidget(panel);

    /*
     * Create a Map that holds the values that should be stored. It will be
     * keyed on "display value", so that any time a "display value" is added or
     * removed the valueMap can be updated.
     */
    mvalueMap = new HashMap<String, String>();

    resetPageIndices();
  }

  // private final OnExactMatch onExactMatch;

  private void findExactMatch(final String displayValue, final int position) {
    updateFormFeedback(FormFeedback.LOADING, null);

    queryOptions(displayValue, 0, FIND_EXACT_MATCH_QUERY_LIMIT, // return a
                                                                // relatively
                                                                // small amount
                                                                // in case
                                                                // wanted "Red"
                                                                // and
                                                                // "Brick Red"
                                                                // is the first
                                                                // thing
                                                                // returned
        new OptionQueryCallback() {

          @Override
          public void error(final Throwable exception) {
            // an exact match couldn't be found, just increment not found
            mfindExactMatchesNot.add(displayValue);
            finalizeFindExactMatches();
          }

          private void extactMatchFound(final int position, final Option option) {
            putValue(option.getName(), option.getValue());
            Log.info("extactMatchFound ! exact match found for displ = " + displayValue);

            // onExactMatch.onExactMatch(option.getValue());
            // and replace the text
            final String text = mfield.getText();
            final String[] keys = text.split(DISPLAY_SEPARATOR.trim());
            keys[position] = option.getName();
            String join = "";
            for (final String n : keys) {
              join += n.trim() + DISPLAY_SEPARATOR;
            }
            join = trimLastDelimiter(join, DISPLAY_SEPARATOR);
            // Commented mfield.setText(join);

            mfindExactMatchesFound++;
          }

          private void finalizeFindExactMatches() {
            if (mfindExactMatchesFound + mfindExactMatchesNot.size() == mfindExactMatchesTotal) {
              // when the found + not = total, we're done
              if (mfindExactMatchesNot.size() > 0) {
                String join = "";
                for (final String val : mfindExactMatchesNot) {
                  join += val.trim() + DISPLAY_SEPARATOR;
                }
                join = trimLastDelimiter(join, DISPLAY_SEPARATOR);
                updateFormFeedback(FormFeedback.ERROR, "Invalid:" + join);
              } else {
                updateFormFeedback(FormFeedback.VALID, null);
              }
            }
          }

          @Override
          public void success(final OptionResultSet optResults) {
            final int totSize = optResults.getTotalSize();
            if (totSize == 1) {
              // an exact match was found, so place it in the value map
              final Option option = optResults.getOptions()[0];
              extactMatchFound(position, option);
            } else {
              // try to find the exact matches within the results
              boolean found = false;
              for (final Option option : optResults.getOptions()) {
                if (displayValue.equalsIgnoreCase(option.getName())) {
                  extactMatchFound(position, option);
                  found = true;
                  break;
                }
              }
              if (!found) {
                mfindExactMatchesNot.add(displayValue);
                Log.info("RestExactMatchCallback -- exact match not found for displ = " + displayValue);
              }
            }
            finalizeFindExactMatches();
          }
        });
  }

  /**
   * If there is more than one key in the text field, check that every key has a
   * value in the map. For any that do not, try to find its exact match.
   */
  private void findExactMatches() {
    final String text = mfield.getText();
    final String[] keys = text.split(DISPLAY_SEPARATOR.trim());
    final int len = keys.length;
    if (len < 2) {
      // do not continue. if there's 1, it is the last one, and getSuggestions
      // can handle it
      return;
    }

    mfindExactMatchesTotal = 0;
    mfindExactMatchesFound = 0;
    mfindExactMatchesNot.clear();
    for (int pos = 0; pos < len; pos++) {
      final String key = keys[pos].trim();

      if (!key.isEmpty()) {
        final String v = mvalueMap.get(key);
        if (null == v) {
          mfindExactMatchesTotal++;
        }
      }
    }
    // then loop through again and try to find them
    /*
     * We may have invalid values due to a multi-value copy-n-paste, or going
     * back and messing with a middle or first key; so for each invalid value,
     * try to find an exact match. *
     */
    for (int pos = 0; pos < len; pos++) {
      final String key = keys[pos].trim();
      if (!key.isEmpty()) {
        final String v = mvalueMap.get(key);
        if (null == v) {
          findExactMatch(key, pos);
        }
      }
    }
  }

  private String getFullReplaceText(final String displ, String replacePre) { // NOPMD
                                                                             // by
                                                                             // vjrj
                                                                             // on
                                                                             // 4/05/11
                                                                             // 19:45
    // replace the last bit after the last comma
    if (replacePre.lastIndexOf(DISPLAY_SEPARATOR) > 0) {
      replacePre = replacePre.substring(0, replacePre.lastIndexOf(DISPLAY_SEPARATOR))
          + DISPLAY_SEPARATOR;
    } else {
      replacePre = "";
    }
    // then add a comma
    if (misMultivalued) {
      return replacePre + displ + DISPLAY_SEPARATOR;
    } else {
      return displ;
    }
  }

  public SuggestBox getSuggestBox() {
    return mfield;
  }

  @Override
  public int getTabIndex() {
    return mfield.getTabIndex();
  }

  /**
   * Get the value(s) as a String. If allowing multivalues, separated by the
   * VALUE_DELIM
   * 
   * @return value(s) as a String
   */
  public String getValue() {
    // String together all the values in the valueMap
    // based on the display values shown in the field
    final String text = mfield.getText();

    String values = "";
    String invalids = "";
    String newKeys = "";
    if (misMultivalued) {
      final String[] keys = text.split(DISPLAY_SEPARATOR);
      for (String key : keys) {
        key = key.trim();
        if (!key.isEmpty()) {
          final String v = mvalueMap.get(key);
          Log.info("getValue for key = " + key + " is v = " + v);
          if (null != v) {
            values += v + VALUE_DELIM;
            // rebuild newKeys removing invalids and dups
            newKeys += key + DISPLAY_SEPARATOR;
          } else {
            invalids += key + DISPLAY_SEPARATOR;
          }
        }
      }
      values = trimLastDelimiter(values, VALUE_DELIM);
      // set the new display values
      mfield.setText(newKeys);
    } else {
      values = mvalueMap.get(text);
    }

    // if there were any invalid show warning
    if (!invalids.isEmpty()) {
      // trim last separator
      invalids = trimLastDelimiter(invalids, DISPLAY_SEPARATOR);
      updateFormFeedback(FormFeedback.ERROR, "Invalids: " + invalids);
    }
    return values;
  }

  /**
   * Get the value map
   * 
   * @return value map
   */
  public Map<String, String> getValueMap() {
    return mvalueMap;
  }

  @Override
  public void onKeyUp(final KeyUpEvent event) {
    /*
     * Because SuggestOracle.requestSuggestions does not get called when the
     * text field is empty this key up handler is necessary for handling the
     * case when there is an empty text field... Here, the FormFeedback is
     * reset.
     */
    updateFormFeedback(FormFeedback.NONE, null);
  }

  @Override
  public void onSelection(final SelectionEvent<Suggestion> event) {
    final Suggestion suggestion = event.getSelectedItem();
    if (suggestion instanceof OptionSuggestion) {
      final OptionSuggestion osugg = (OptionSuggestion) suggestion;
      // if NEXT or PREVIOUS were selected, requery but bypass the timer
      final String value = osugg.getValue();
      if (OptionSuggestion.NEXT_VALUE.equals(value)) {
        mindexFrom += PAGE_SIZE;
        mindexTo += PAGE_SIZE;

        final RestSuggestOracle oracle = (RestSuggestOracle) mfield.getSuggestOracle();
        oracle.getSuggestions();

      } else if (OptionSuggestion.PREVIOUS_VALUE.equals(value)) {
        mindexFrom -= PAGE_SIZE;
        mindexTo -= PAGE_SIZE;

        final RestSuggestOracle oracle = (RestSuggestOracle) mfield.getSuggestOracle();
        oracle.getSuggestions();

      } else {
        // made a valid selection
        updateFormFeedback(FormFeedback.VALID, null);

        // add the option's value to the value map
        putValue(osugg.getName(), value);

        // put the focus back into the textfield so user
        // can enter more
        // Commented mfield.setFocus(true);
      }
    }
  }

  private void putValue(final String key, final String value) {
    Log.info("putting key = " + key + "; value = " + value);
    mvalueMap.put(key, value);
  }

  /**
   * Retrieve Options (name-value pairs) that are suggested from the REST
   * endpoint
   * 
   * @param query
   *          - the String search term
   * @param from
   *          - the 0-based begin index int
   * @param to
   *          - the end index inclusive int
   * @param callback
   *          - the OptionQueryCallback to handle the response
   */
  private void queryOptions(final String query, final int from, final int to,
      final OptionQueryCallback callback) {
    final RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(mrestEndpointUrl
        + "?" + SearcherConstants.QUERY_PARAM + "=" + query + "&" + SearcherConstants.START_PARAM + "="
        + from + "&" + SearcherConstants.LIMIT_PARAM + "=" + PAGE_SIZE));

    // Set our headers
    builder.setHeader("Accept", "application/json");
    builder.setHeader("Accept-Charset", "UTF-8");

    builder.setCallback(new RequestCallback() {

      @Override
      public void onError(final com.google.gwt.http.client.Request request, final Throwable exception) {
        callback.error(exception);
      }

      @Override
      public void onResponseReceived(final com.google.gwt.http.client.Request request,
          final Response response) {
        final JSONValue val = JSONParser.parse(response.getText());
        final JSONObject obj = val.isObject();
        final int totSize = (int) obj.get(OptionResultSet.TOTAL_SIZE).isNumber().doubleValue();
        final OptionResultSet options = new OptionResultSet(totSize);
        final JSONArray optionsArray = obj.get(OptionResultSet.OPTIONS).isArray();

        if (options.getTotalSize() > 0 && optionsArray != null) {

          for (int i = 0; i < optionsArray.size(); i++) {
            if (optionsArray.get(i) == null) {
              /*
               * This happens when a JSON array has an invalid trailing comma
               */
              continue;
            }

            final JSONObject jsonOpt = optionsArray.get(i).isObject();
            final Option option = new Option();

            final String longName = jsonOpt.get(OptionResultSet.DISPLAY_NAME).isString().stringValue();
            final String shortName = jsonOpt.get(OptionResultSet.VALUE).isString().stringValue();
            final JSONValue groupTypeJsonValue = jsonOpt.get("groupType");
            final String prefix = groupTypeJsonValue.isString() == null ? ""
                : GroupType.PERSONAL.name().equals(groupTypeJsonValue.isString().stringValue()) ? I18n.t("User")
                    + ": "
                    : I18n.t("Group") + ": ";
            option.setName(prefix
                + (!longName.equals(shortName) ? longName + " (" + shortName + ")" : shortName));
            option.setValue(jsonOpt.get(OptionResultSet.VALUE).isString().stringValue());
            options.addOption(option);
          }
        }
        callback.success(options);
      }
    });

    try {
      if (lastQuery != null && lastQuery.isPending()) {
        lastQuery.cancel();
      }
      lastQuery = builder.send();
    } catch (final RequestException e) {
      updateFormFeedback(FormFeedback.ERROR, "Error: " + e.getMessage());
    }

  }

  private void resetPageIndices() {
    mindexFrom = 0;
    mindexTo = mindexFrom + PAGE_SIZE - 1;
  }

  @Override
  public void setAccessKey(final char key) {
    mfield.setAccessKey(key);
  }

  @Override
  public void setFocus(final boolean focused) {
    mfield.setFocus(focused);
  }

  public void setSearchUrl(final String searchUrl) {
    mrestEndpointUrl = searchUrl;
  }

  @Override
  public void setTabIndex(final int index) {
    mfield.setTabIndex(index);
  }

  /**
   * Convenience method to set the status and tooltip of the FormFeedback
   * 
   * @param status
   *          - a FormFeedback status
   * @param tooltip
   *          - a String tooltip
   */
  public void updateFormFeedback(final int status, final String tooltip) {
    mfeedback.setStatus(status);
    if (tooltip != null) {
      mfeedback.setTitle(tooltip);
    }

    final TextBoxBase textBox = mfield.getTextBox();
    if (FormFeedback.LOADING == status) {
      NotifyUser.showProgressSearching();
      // textBox.setEnabled(false);
    } else {
      new Timer() {
        @Override
        public void run() {
          NotifyUser.hideProgress();
        }
      }.schedule(1500);
      // textBox.setEnabled(true);
      textBox.setFocus(false); // Blur then focus b/c of a strange problem with
                               // the cursor or selection highlights no longer
                               // visible within the textfield (this is a
                               // workaround)
      textBox.setFocus(true);
    }
  }
}
