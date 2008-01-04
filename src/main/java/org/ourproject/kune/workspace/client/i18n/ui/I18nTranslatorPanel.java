/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.i18n.ui;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.JsonReaderConfig;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StoreLoadConfig;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGrid;
import com.gwtext.client.widgets.grid.EditorGridConfig;
import com.gwtext.client.widgets.grid.Grid;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class I18nTranslatorPanel extends HorizontalPanel implements I18nTranslatorView {

    private static final String NOTE_FOR_TRANSLATORS_IMAGE_HTML = Images.App.getInstance().noteForTranslators()
            .getHTML();

    private final LayoutDialog dialog;
    private final I18nTranslatorPresenter presenter;
    private Store store;
    private LanguageSelectorPanel languageSelectorPanel;

    public I18nTranslatorPanel(final I18nTranslatorPresenter initPresenter) {
        this.presenter = initPresenter;
        dialog = createDialog();
    }

    public void show() {
        // By default we use the user lang to help in translation
        I18nLanguageDTO lang = presenter.getLanguage();
        setLanguage(lang.getCode());
        String languageNativeNameIfAvailable = lang.getNativeName().length() > 0 ? lang.getNativeName() : lang
                .getEnglishName();
        dialog.setTitle(Kune.I18N.t("Help to translate kune to [%s]", languageNativeNameIfAvailable));
        dialog.center();
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    private void setLanguage(final String language) {
        Site.showProgressLoading();
        store.load(new StoreLoadConfig() {
            {
                setParams(new UrlParam[] { new UrlParam("language", language) });
            }
        });
        languageSelectorPanel.selectLanguage(language);
        Site.hideProgress();
    }

    private LayoutDialog createDialog() {

        LayoutRegionConfig north = new LayoutRegionConfig() {
            {
                setSplit(false);
                setInitialSize(50);
            }
        };

        LayoutRegionConfig center = new LayoutRegionConfig() {
            {
                setAutoScroll(false);
                setTabPosition("top");
                setCloseOnTab(false);
                setAlwaysShowTabs(true);
            }
        };

        final LayoutDialog dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                setModal(true);
                setWidth(720);
                setHeight(330);
                setShadow(true);
                setCollapsible(false);
                setResizable(false);
            }
        }, north, null, null, null, center);

        dialog.addButton(new Button(new ButtonConfig() {
            {
                setText(Kune.I18N.tWithNT("Close", "used in button"));
                setButtonListener(new ButtonListenerAdapter() {
                    public void onClick(final Button button, final EventObject e) {
                        presenter.onHide();
                    }
                });
            }
        }));

        BorderLayout layout = dialog.getLayout();

        layout.beginUpdate();

        ContentPanel northPanel = new ContentPanel(Ext.generateId(), new ContentPanelConfig() {
            {
                // setAutoScroll(true);
            }
        });

        ContentPanel centerPanel = new ContentPanel(Ext.generateId(), Kune.I18N.t("Untranslated"),
                new ContentPanelConfig() {
                    {
                        setAutoScroll(false);
                        setFitToContainer(true);
                        setFitToFrame(true);
                    }
                });

        EditorGrid grid = createGridPanel();

        HorizontalPanel hp = new HorizontalPanel();
        languageSelectorPanel = new LanguageSelectorPanel(null, presenter.getLanguages(), Kune.I18N
                .t("Change language"));
        languageSelectorPanel.addChangeListener(new ComboBoxListenerAdapter() {
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                setLanguage(record.getAsString("abbr"));
                dialog.setTitle(Kune.I18N.t("Help to translate kune to [%s]", record.getAsString("language")));
            }
        });
        hp.add(languageSelectorPanel);
        hp.addStyleName("kune-Margin-Large-trbl");
        northPanel.add(hp);

        centerPanel.add(grid);

        layout.add(LayoutRegionConfig.NORTH, northPanel);
        layout.add(LayoutRegionConfig.CENTER, centerPanel);

        layout.endUpdate();

        return dialog;
    }

    private EditorGrid createGridPanel() {

        HttpProxy proxy = new HttpProxy("/kune/json/I18nTranslationJSONService/search", Connection.POST);

        JsonReader reader = new JsonReader(new JsonReaderConfig() {
            {
                setId("id");
            }
        }, new RecordDef(new FieldDef[] { new StringFieldDef("trKey"), new StringFieldDef("text"),
                new StringFieldDef("id") }));

        store = new Store(proxy, reader);

        ColumnModel columnModel = new ColumnModel(new ColumnConfig[] { new ColumnConfig() {
            {
                setHeader(Kune.I18N.t("Text to traslate"));
                setDataIndex("trKey");
                setWidth(335);
                // setCss("kune-I18nTranslatorCell");
                setTooltip(Kune.I18N.t("Click to sort"));
                setRenderer(renderNT);
            }
        }, new ColumnConfig() {
            {
                setHeader(Kune.I18N.t("Translation (click to edit)"));
                setDataIndex("text");
                setWidth(335);
                // setCss("kune-I18nTranslatorCell");
                setTooltip(Kune.I18N.t("Click to sort"));
                setEditor(new GridEditor(new TextField(new TextFieldConfig() {
                    {
                        setAllowBlank(true);
                    }
                })));
            }
        } });

        columnModel.setDefaultSortable(true);

        EditorGrid grid = new EditorGrid("grid-translation", "695px", "180px", store, columnModel,
                new EditorGridConfig() {
                    {
                        setClicksToEdit(1);
                        // setAutoExpandColumn(1);
                        // setAutoWidth(true);
                        setEnableRowHeightSync(true);
                    }
                });

        grid.addEditorGridListener(new EditorGridListenerAdapter() {

            // public boolean doValidateEdit(Grid grid, Record record, String
            // field, Object value, Object originalValue,

            // public boolean doBeforeEdit(final Grid grid, final Record record,
            // final String field, final Object value,
            // final int rowIndex, final int colIndex) {

            public void onAfterEdit(final Grid grid, final Record record, final String field, final Object newValue,
                    final Object oldValue, final int rowIndex, final int colIndex) {
                String id = record.getAsString("id");
                String trKey = record.getAsString("trKey");
                presenter.doTranslation(id, trKey, oldValue, newValue);
                record.set(field, KuneStringUtils.escapeHtmlLight((String) newValue));
            }
        });

        grid.addGridCellListener(new GridCellListenerAdapter() {
            public void onCellDblClick(final Grid grid, final int rowIndex, final int colIndex, final EventObject e) {
                Record record = store.getRecordAt(rowIndex);
                String id = record.getAsString("id");
                String trKey = record.getAsString("trKey");
                String text = record.getAsString("text");
                if (text.length() == 0) {
                    String trWithoutNT = removeNT(trKey);
                    record.set("text", trWithoutNT);
                    presenter.doTranslation(id, trKey, trWithoutNT, trWithoutNT);
                }
            }
        });

        grid.render();

        return grid;

    }

    private String removeNT(final String string) {
        return Kune.I18N.removeNT(string);
    }

    private String[] splitNT(final String textWithNT) {
        String[] nt;
        String[] splitted = textWithNT.split(" \\[%NT ");
        if (splitted.length > 1) {
            nt = splitted[1].split("\\]$");
            splitted[1] = nt[0];
        }
        return splitted;
    }

    private final Renderer renderNT = new Renderer() {

        public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
                Store store) {
            String renderer;
            String[] splitted = splitNT((String) value);
            if (splitted.length > 1) {
                renderer = "{0} " + NOTE_FOR_TRANSLATORS_IMAGE_HTML;
                cellMetadata.setHtmlAttribute("ext:qtip=\"" + splitted[1] + "\" ext:qtitle=\"Note for translators\"");
            } else {
                renderer = "{0}";
            }
            return Format.format(renderer, splitted);
        }
    };
}