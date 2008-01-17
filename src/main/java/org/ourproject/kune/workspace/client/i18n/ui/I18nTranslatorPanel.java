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
import org.ourproject.kune.platf.client.ui.AbstractSearcherPanel;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorComponent;
import org.ourproject.kune.workspace.client.workspace.ui.BottomTrayIcon;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.LoadMaskConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DialogListenerAdapter;
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
import com.gwtext.client.widgets.layout.LayoutRegion;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class I18nTranslatorPanel extends AbstractSearcherPanel implements I18nTranslatorView {

    private static final String NOTE_FOR_TRANSLATORS_IMAGE_HTML = Images.App.getInstance().noteForTranslators()
            .getHTML();

    private LayoutDialog dialog;
    private final I18nTranslatorPresenter presenter;
    private LanguageSelectorPanel languageSelectorPanel;

    private Store unTransStore;
    private Store transStore;

    private BottomTrayIcon bottomIcon;

    public I18nTranslatorPanel(final I18nTranslatorPresenter initPresenter) {
        this.presenter = initPresenter;
    }

    public void show() {
        if (dialog == null) {
            dialog = createDialog();
        }
        // By default we use the user lang to help in translation
        I18nLanguageDTO lang = presenter.getLanguage();
        setLanguage(lang);
        String languageNativeNameIfAvailable = lang.getNativeName().length() > 0 ? lang.getNativeName() : lang
                .getEnglishName();
        dialog.setTitle(Kune.I18N.t("Help to translate kune to [%s]", languageNativeNameIfAvailable));
        dialog.show();
        dialog.expand();
        dialog.center();
        if (bottomIcon == null) {
            bottomIcon = new BottomTrayIcon(Kune.I18N.t("Show/hide translator"));
            bottomIcon.addMainButton(Images.App.getInstance().language(), new Command() {
                public void execute() {
                    if (dialog.isVisible()) {
                        dialog.hide();
                    } else {
                        dialog.show();
                    }
                }
            });
            presenter.attachIconToBottomBar(bottomIcon);
        }
    }

    public void hide() {
        dialog.hide();
    }

    public void close() {
        dialog.hide();
        // dialog.destroy(true);
    }

    private void setLanguage(final String language) {
        Site.showProgressLoading();
        query(unTransStore, unTransGrid, language);
        query(transStore, transGrid, language);
        Site.hideProgress();
    }

    private void setLanguage(final I18nLanguageDTO language) {
        languageSelectorPanel.setLanguage(language.getCode());
        setLanguage(language.getCode());
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
                setModal(false);
                setWidth(720);
                setHeight(330);
                setShadow(true);
                setCollapsible(true);
                setProxyDrag(true);
                setResizable(false);
            }
        }, north, null, null, null, center);

        dialog.addButton(new Button(new ButtonConfig() {
            {
                setText(Kune.I18N.tWithNT("Close", "used in button"));
                setButtonListener(new ButtonListenerAdapter() {
                    public void onClick(final Button button, final EventObject e) {
                        presenter.onClose();
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

        ContentPanel unTrasnCenterPanel = new ContentPanel(Ext.generateId(), Kune.I18N.t("Untranslated"),
                new ContentPanelConfig() {
                    {
                        setAutoScroll(false);
                        setFitToContainer(true);
                        setFitToFrame(true);
                    }
                });

        ContentPanel trasnCenterPanel = new ContentPanel(Ext.generateId(), Kune.I18N.t("Translated"),
                new ContentPanelConfig() {
                    {
                        setAutoScroll(false);
                        setFitToContainer(true);
                        setFitToFrame(true);
                    }
                });

        ContentPanel recommendationPanel = new ContentPanel(Ext.generateId(), Kune.I18N.t("Recommendations"),
                new ContentPanelConfig() {
                    {
                        setAutoScroll(true);
                        setFitToContainer(true);
                        setFitToFrame(true);
                    }
                });

        HTML recommendations = createRecomendations();

        transGrid = createGridPanel(true);
        unTransGrid = createGridPanel(false);
        HorizontalPanel hp = new HorizontalPanel();
        LanguageSelectorComponent langComponent = WorkspaceFactory.createLanguageSelectorComponent();
        languageSelectorPanel = (LanguageSelectorPanel) langComponent.getView();
        languageSelectorPanel.addChangeListener(new ComboBoxListenerAdapter() {
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                setLanguage(record.getAsString(LanguageSelectorPanel.LANG_ID));
                dialog.setTitle(Kune.I18N.t("Help to translate kune to [%s]", record.getAsString("language")));
            }
        });
        hp.add(languageSelectorPanel);
        hp.addStyleName("kune-Margin-Large-trbl");
        northPanel.add(hp);

        unTrasnCenterPanel.add(unTransGrid);
        trasnCenterPanel.add(transGrid);
        recommendationPanel.add(recommendations);

        layout.add(LayoutRegionConfig.NORTH, northPanel);
        layout.add(LayoutRegionConfig.CENTER, unTrasnCenterPanel);
        layout.add(LayoutRegionConfig.CENTER, trasnCenterPanel);
        layout.add(LayoutRegionConfig.CENTER, recommendationPanel);

        layout.endUpdate();

        LayoutRegion centerRegion = layout.getRegion(LayoutRegionConfig.CENTER);
        String panelId = unTrasnCenterPanel.getId();
        centerRegion.showPanel(panelId);

        dialog.addDialogListener(new DialogListenerAdapter() {
            public void onResize(final LayoutDialog dialog, final int width, final int height) {
                if (height < 40) {
                    // There is no a minimize event, then when resize has less
                    // than this height, is equivalent to a minimize, and we put
                    // the dialog in the bottom of the screen
                    dialog.hide();
                }
            }

            public void onHide(final LayoutDialog dialog) {
                dialog.destroy();
            }
        });

        return dialog;
    }

    private EditorGrid createGridPanel(final boolean translated) {

        Store store;
        final String id = "id";
        FieldDef[] fieldDefs = new FieldDef[] { new StringFieldDef("trKey"), new StringFieldDef("text"),
                new StringFieldDef(id) };

        if (translated) {
            String url = "/kune/json/I18nTranslationJSONService/searchtranslated";
            transStore = createStore(fieldDefs, url, id);
            store = transStore;
        } else {
            String url = "/kune/json/I18nTranslationJSONService/search";
            unTransStore = createStore(fieldDefs, url, id);
            store = unTransStore;
        }

        ColumnModel columnModel = new ColumnModel(new ColumnConfig[] { new ColumnConfig() {
            {
                setHeader(Kune.I18N.t("Text to translate"));
                setDataIndex("trKey");
                setWidth(335);
                setTooltip(Kune.I18N.t("Click to sort"));
                setRenderer(renderNT);
            }
        }, new ColumnConfig() {
            {
                setHeader(Kune.I18N.t("Translation (click to edit)"));
                setDataIndex("text");
                setWidth(335);
                setEditor(new GridEditor(new TextField(new TextFieldConfig() {
                    {
                        setAllowBlank(true);
                    }
                })));
            }
        } });

        columnModel.setDefaultSortable(true);

        EditorGrid grid = new EditorGrid((translated ? "grid-translated" : "grid-untranslated"), "695px", "180px",
                store, columnModel, new EditorGridConfig() {
                    {
                        setClicksToEdit(1);
                        setLoadMask(true);
                        setLoadMask(new LoadMaskConfig(Kune.I18N.t("Loading")));
                    }
                });

        grid.addEditorGridListener(new EditorGridListenerAdapter() {
            public void onAfterEdit(final Grid grid, final Record record, final String field, final Object newValue,
                    final Object oldValue, final int rowIndex, final int colIndex) {
                String idValue = record.getAsString(id);
                String trKey = record.getAsString("trKey");
                presenter.doTranslation(idValue, trKey, oldValue, newValue);
                record.set(field, KuneStringUtils.escapeHtmlLight((String) newValue));
            }
        });

        grid.addGridCellListener(new GridCellListenerAdapter() {
            public void onCellDblClick(final Grid grid, final int rowIndex, final int colIndex, final EventObject e) {
                Record record = unTransStore.getRecordAt(rowIndex);
                String idValue = record.getAsString(id);
                String trKey = record.getAsString("trKey");
                String text = record.getAsString("text");
                if (text.length() == 0) {
                    String trWithoutNT = removeNT(trKey);
                    record.set("text", trWithoutNT);
                    presenter.doTranslation(idValue, trKey, trWithoutNT, trWithoutNT);
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
                String tip = "<div style='min-width: 75px'>" + splitted[1] + "</div>";
                cellMetadata.setHtmlAttribute("ext:qtip=\"" + tip + "\" ext:qtitle=\"Note for translators\"");
            } else {
                renderer = "{0}";
            }
            return Format.format(renderer, splitted);
        }
    };

    private EditorGrid transGrid;

    private EditorGrid unTransGrid;

    private HTML createRecomendations() {
        HTML recommendations = new HTML(
                "<h1><a name=\"common_translation_errors\" id=\"common_translation_errors\">Common Translation Errors</a></h1>\n"
                        + "<div class=\"level1\">\n"
                        + "\n"
                        + "<p>\n"
                        + " Translators - even professional translators - usually make these error when translating computer software.\n"
                        + "</p>\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h2><a name=\"overview_of_common_errors\" id=\"overview_of_common_errors\">Overview of common errors</a></h2>\n"
                        + "<div class=\"level2\">\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h3><a name=\"capitalisation\" id=\"capitalisation\">Capitalisation</a></h3>\n"
                        + "<div class=\"level3\">\n"
                        + "\n"
                        + "<p>\n"
                        + " <strong>Wrong</strong>: no capitalisation in translation<br/>\n"
                        + "\n"
                        + " <strong>Correct</strong>: translation follows translation language capitalisation convention\n"
                        + "</p>\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h3><a name=\"double_words\" id=\"double_words\">Double words</a></h3>\n"
                        + "<div class=\"level3\">\n"
                        + "\n"
                        + "<p>\n"
                        + " <em>Translator could not decide which word was more appropriate</em>\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: two translated words for one original word<br/>\n"
                        + " <strong>Correct</strong>: choose one word if you have two options\n"
                        + "</p>\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h3><a name=\"translated_things_that_should_stay_in_english\" id=\"translated_things_that_should_stay_in_english\">Translated things that should stay in English</a></h3>\n"
                        + "<div class=\"level3\">\n"
                        + "\n"
                        + "<p>\n"
                        + "  <strong>Wrong</strong>: translating program function name like get_file_attributes()<br/>\n"
                        + "\n"
                        + " <strong>Correct</strong>: leave program syntax or function names untranslated\n"
                        + "</p>\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h3><a name=\"variables\" id=\"variables\">Variables</a></h3>\n"
                        + "<div class=\"level3\">\n"
                        + "\n"
                        + "<p>\n"
                        + " <strong>Wrong</strong>: variables left out of translation<br/>\n"
                        + " <strong>Correct</strong>: variable in translation in the correct order for the language\n"
                        + "\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: environment variable names (eg. EDITOR) and possible fixed values (eg. COLOR, TRANSPARENT) translated<br/>\n"
                        + " <strong>Correct</strong>: left in English and perhaps surrounded by single quotes to indicate that they are not in the target language on purpose.\n"
                        + "</p>\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h3><a name=\"punctuation\" id=\"punctuation\">Punctuation</a></h3>\n"
                        + "<div class=\"level3\">\n"
                        + "\n"
                        + "<p>\n"
                        + " <strong>Wrong</strong>: leaving out end punctuation and whitespace (eg. <code>“File: ”</code>)<br/>\n"
                        + " <strong>Correct</strong>: copying end punctuation and whitespace almost exactly as the original into the translation.\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: adding missing fullstops to translated sentence<br/>\n"
                        + " <strong>Correct</strong>: if full sentences do not contain a fullstop in the original do not add one to the translation.\n"
                        + "\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: adding exclamation marks (!) to the translation when they occur in the original<br/>\n"
                        + " <strong>Correct</strong>: use exclamation marks to correspond with the tone of the application and the convention of the translated language.  Ie you may leave them out of the translation.\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: leaving out ellipses (&hellip;)<br/>\n"
                        + " <strong>Correct</strong>: always add ellipses to the translation\n"
                        + "\n"
                        + "</p>\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h3><a name=\"accelerators\" id=\"accelerators\">Accelerators</a></h3>\n"
                        + "<div class=\"level3\">\n"
                        + "\n"
                        + "<p>\n"
                        + " <strong>Wrong</strong>: leaving out accelerators (either _&amp;~ depending on the application)<br/>\n"
                        + " <strong>Correct</strong>: if the original has an accelerator so should the translation\n"
                        + "\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: placing the accelerator exactly in the same position as the original<br/>\n"
                        + " <strong>Correct</strong>: place the accelerator on the word / syllable / part of the sentence that is accented in its pronunciation or is the focus of the sentence.\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: using the same letter for accelerator keys<br/>\n"
                        + " <strong>Correct</strong>: try to vary the letters chosen as the accelerator key (in some languages almost all words start with U or I.  In this case make an effort to choose other letters)\n"
                        + "\n"
                        + "</p>\n"
                        + "\n"
                        + "</div>\n"
                        + "\n"
                        + "<h3><a name=\"html\" id=\"html\">HTML</a></h3>\n"
                        + "<div class=\"level3\">\n"
                        + "\n"
                        + "<p>\n"
                        + " <strong>Wrong</strong>: <acronym title=\"HyperText Markup Language\">HTML</acronym> tags are translated<br/>\n"
                        + " <strong>Correct</strong>: <acronym title=\"HyperText Markup Language\">HTML</acronym> tags are not translated\n"
                        + "\n"
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + "<strong>Wrong</strong>: translatable <acronym title=\"HyperText Markup Language\">HTML</acronym> entities are not translated<br/>\n"
                        + " <strong>Correct</strong>: translate items that will be viewable.  Eg <acronym title=\"HyperText Markup Language\">HTML</acronym> img tag&#039;s alt entity &lt;img alt=“translate me”&gt;, a tag&#039;s title entity &lt;a href=blah title=“translate me”&gt;.\n"
                        + "\n"
                        + "</p>\n"
                        + "<p>\n"
                        + "This recommendations are from "
                        + KuneStringUtils.generateHtmlLink("http://translate.sourceforge.net",
                                "Translate Toolkit & Pootle Project")
                        + " under "
                        + KuneStringUtils.generateHtmlLink("http://creativecommons.org/licenses/by-sa/3.0/",
                                "cc-by-sa license") + "." + "</p>\n" + "\n" + "</div>\n" + "");
        recommendations.setStyleName("kune-i18nTranslator-recommend");
        return recommendations;
    }

}