/*
 * Copyright 2010 Google Inc.
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
import cc.kune.common.client.utils.SimpleCallback;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.I18nTranslationDTO;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

public class I18nTranslationDataProvider {

  private final ListDataProvider<I18nTranslationDTO> dataProvider = new ListDataProvider<I18nTranslationDTO>();
  private final I18nServiceAsync i18n;
  private SimpleCallback loadCallback;
  private SingleSelectionModel<I18nTranslationDTO> selectionModel;
  private final Session session;

  @Inject
  private I18nTranslationDataProvider(final Session session, final I18nServiceAsync i18n) {
    this.session = session;
    this.i18n = i18n;
  }

  public void addDataDisplay(final CellList<I18nTranslationDTO> displayList) {
    dataProvider.addDataDisplay(displayList);
  }

  private void avance(final int increment) {
    final I18nTranslationDTO selected = selectionModel.getSelectedObject();
    final List<I18nTranslationDTO> list = dataProvider.getList();
    final int pos = list.indexOf(selected);
    final int next = pos + increment;
    if (next >= 0 && next < list.size()) {
      selectionModel.setSelected(list.get(next), true);
    }
  }

  public void refreshDisplays() {
    dataProvider.refresh();
  }

  public void selectNext() {
    avance(1);
  }

  public void selectPrevious() {
    avance(-1);
  }

  public void setLanguage(final I18nLanguageSimpleDTO language, final boolean toTranslate) {
    NotifyUser.showProgressLoading();
    dataProvider.getList().clear();
    dataProvider.refresh();
    i18n.getTranslatedLexicon(session.getUserHash(), language.getCode(), toTranslate,
        new AsyncCallbackSimple<List<I18nTranslationDTO>>() {
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

  public void setLoadCallback(final SimpleCallback loadCallback) {
    this.loadCallback = loadCallback;
  }

  public void setSelectionMode(final SingleSelectionModel<I18nTranslationDTO> selectionModel) {
    this.selectionModel = selectionModel;
  }

}
