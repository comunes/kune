/*
 * Copyright 2010, 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cc.kune.gspace.client.i18n;

import java.util.List;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.I18nTranslationDTO;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslationDataProvider.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nTranslationDataProvider {

  /** The data provider. */
  private final ListDataProvider<I18nTranslationDTO> dataProvider = new ListDataProvider<I18nTranslationDTO>();

  /** The i18n. */
  private final I18nServiceAsync i18n;

  /** The load callback. */
  private SimpleCallback loadCallback;

  /** The selection model. */
  private SingleSelectionModel<I18nTranslationDTO> selectionModel;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new i18n translation data provider.
   * 
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   */
  @Inject
  private I18nTranslationDataProvider(final Session session, final I18nServiceAsync i18n) {
    this.session = session;
    this.i18n = i18n;
  }

  /**
   * Adds the data display.
   * 
   * @param displayList
   *          the display list
   */
  public void addDataDisplay(final CellList<I18nTranslationDTO> displayList) {
    dataProvider.addDataDisplay(displayList);
  }

  /**
   * Avance.
   * 
   * @param increment
   *          the increment
   */
  private void avance(final int increment) {
    final I18nTranslationDTO selected = selectionModel.getSelectedObject();
    final List<I18nTranslationDTO> list = dataProvider.getList();
    final int pos = list.indexOf(selected);
    final int next = pos + increment;
    if (next >= 0 && next < list.size()) {
      selectionModel.setSelected(list.get(next), true);
    }
  }

  /**
   * Refresh displays.
   */
  public void refreshDisplays() {
    dataProvider.refresh();
  }

  /**
   * Select next.
   */
  public void selectNext() {
    avance(1);
  }

  /**
   * Select previous.
   */
  public void selectPrevious() {
    avance(-1);
  }

  /**
   * Sets the language.
   * 
   * @param fromLanguage
   *          the from language
   * @param toLanguage
   *          the to language
   * @param toTranslate
   *          the to translate
   */
  public void setLanguage(final I18nLanguageSimpleDTO fromLanguage,
      final I18nLanguageSimpleDTO toLanguage, final boolean toTranslate) {
    NotifyUser.showProgressLoading();
    dataProvider.getList().clear();
    dataProvider.refresh();
    i18n.getTranslatedLexicon(session.getUserHash(), toLanguage.getCode(), fromLanguage == null ? null
        : fromLanguage.getCode(), toTranslate, new AsyncCallbackSimple<List<I18nTranslationDTO>>() {
      @Override
      public void onSuccess(final List<I18nTranslationDTO> result) {
        dataProvider.setList(result);
        dataProvider.refresh();
        if (result.size() > 0) {
          selectionModel.setSelected(result.get(0), true);
        }
        loadCallback.onCallback();
      }
    });
  }

  /**
   * Sets the load callback.
   * 
   * @param loadCallback
   *          the new load callback
   */
  public void setLoadCallback(final SimpleCallback loadCallback) {
    this.loadCallback = loadCallback;
  }

  /**
   * Sets the selection mode.
   * 
   * @param selectionModel
   *          the new selection mode
   */
  public void setSelectionMode(final SingleSelectionModel<I18nTranslationDTO> selectionModel) {
    this.selectionModel = selectionModel;
  }

}
