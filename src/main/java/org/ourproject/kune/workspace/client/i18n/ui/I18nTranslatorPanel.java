/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.i18n.ui;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.AbstractSearcherPanel;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;
import org.ourproject.kune.workspace.client.i18n.LanguageSelector;
import org.ourproject.kune.workspace.client.search.SiteBottomTrayButton;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class I18nTranslatorPanel extends AbstractSearcherPanel implements I18nTranslatorView {

    private static final String NOTE_FOR_TRANSLATORS_IMAGE_HTML = Images.App.getInstance().nt().getHTML();

    private Window dialog;
    private final I18nTranslatorPresenter presenter;
    private LanguageSelectorPanel languageSelectorPanel;
    private Store unTransStore;
    private Store transStore;
    private GridPanel transGrid;
    private GridPanel unTransGrid;
    private final LanguageSelector languageSelector;
    private final WorkspaceSkeleton ws;
    private SiteBottomTrayButton traybarButton;

    public I18nTranslatorPanel(final I18nTranslatorPresenter initPresenter, final I18nTranslationService i18n,
	    final LanguageSelector languageSelector, final WorkspaceSkeleton ws) {
	super(i18n);
	this.presenter = initPresenter;
	this.languageSelector = languageSelector;
	this.ws = ws;
    }

    public void close() {
	dialog.hide();
    }

    public void hide() {
	dialog.hide();
    }

    public void hideTranslatorAndIcon() {
	if (dialog.isRendered()) {
	    dialog.hide();
	}
	if (traybarButton != null) {
	    traybarButton.destroy();
	    traybarButton = null;
	}
    }

    public void show() {
	if (dialog == null) {
	    dialog = createDialog();
	}
	// By default we use the user lang to help in translation
	final I18nLanguageDTO lang = presenter.getLanguage();
	setLanguage(lang);
	final String languageNativeNameIfAvailable = lang.getNativeName().length() > 0 ? lang.getNativeName() : lang
		.getEnglishName();
	dialog.setTitle(i18n.t("Help to translate kune to [%s]", languageNativeNameIfAvailable));
	dialog.show();
	dialog.expand();
	dialog.center();
	if (traybarButton == null) {
	    traybarButton = new SiteBottomTrayButton("images/language.gif", i18n.t("Show/hide translator"), dialog, ws);
	}
    }

    private Window createDialog() {
	final Panel north = new Panel();
	north.setBorder(false);

	final Panel center = new TabPanel();
	center.setAutoScroll(false);
	center.setClosable(false);
	center.setBorder(false);

	final Window dialog = new BasicDialog("", false, false, 720, 330);
	// dialog.setResizable(false);
	dialog.setIconCls("i18n-icon");

	final Button close = new Button();
	close.setText(i18n.tWithNT("Close", "used in button"));
	close.addListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.doClose();
	    }
	});
	dialog.addButton(close);

	final Panel unTransCenterPanel = new Panel(i18n.t("Untranslated"));
	unTransCenterPanel.setAutoScroll(false);
	unTransCenterPanel.setLayout(new FitLayout());

	final Panel transCenterPanel = new Panel(i18n.t("Translated"));
	transCenterPanel.setAutoScroll(false);
	transCenterPanel.setLayout(new FitLayout());

	final Panel recommendationPanel = new Panel(i18n.t("Recommendations"));
	recommendationPanel.setAutoScroll(true);
	recommendationPanel.setLayout(new FitLayout());

	transGrid = createGridPanel(true);
	unTransGrid = createGridPanel(false);
	final HorizontalPanel hp = new HorizontalPanel();
	languageSelectorPanel = (LanguageSelectorPanel) languageSelector.getView();
	languageSelectorPanel.addChangeListener(new ComboBoxListenerAdapter() {
	    public void onSelect(final ComboBox comboBox, final Record record, final int index) {
		setLanguage(record.getAsString(LanguageSelectorPanel.LANG_ID));
		dialog.setTitle(i18n.t("Help to translate kune to [%s]", record.getAsString("language")));
	    }
	});
	hp.add(languageSelectorPanel);
	hp.addStyleName("kune-Margin-Large-trbl");
	north.add(hp);

	unTransCenterPanel.add(unTransGrid);
	transCenterPanel.add(transGrid);
	final Frame recommFrame = new Frame("html/i18n-recom.html");
	// recommFrame.setHeight("220");
	recommendationPanel.add(recommFrame);

	center.add(unTransCenterPanel);
	center.add(transCenterPanel);
	center.add(recommendationPanel);
	dialog.add(north, new BorderLayoutData(RegionPosition.NORTH));
	dialog.add(center, new BorderLayoutData(RegionPosition.CENTER));

	center.setActiveItemID(unTransCenterPanel.getId());

	return dialog;
    }

    private GridPanel createGridPanel(final boolean translated) {

	final Renderer renderNT = new Renderer() {
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

	Store store;
	final String id = "id";
	final FieldDef[] fieldDefs = new FieldDef[] { new StringFieldDef("trKey"), new StringFieldDef("text"),
		new StringFieldDef(id) };

	if (translated) {
	    final String url = "/kune/json/I18nTranslationJSONService/searchtranslated";
	    transStore = createStore(fieldDefs, url, id);
	    store = transStore;
	} else {
	    final String url = "/kune/json/I18nTranslationJSONService/search";
	    unTransStore = createStore(fieldDefs, url, id);
	    store = unTransStore;
	}

	final ColumnConfig trKeyColumn = new ColumnConfig() {
	    {
		setHeader(i18n.t("Text to translate"));
		setDataIndex("trKey");
		setWidth(335);
		setTooltip(i18n.t("Click to sort"));
		setRenderer(renderNT);
	    }
	};

	final GridEditor textColumnEditor = new GridEditor(new TextField());

	final ColumnConfig textColumn = new ColumnConfig() {
	    {
		setHeader(i18n.t("Translation (click to edit)"));
		setDataIndex("text");
		setWidth(335);
		setEditor(textColumnEditor);
	    }
	};

	final ColumnModel columnModel = new ColumnModel(new ColumnConfig[] { trKeyColumn, textColumn });

	columnModel.setDefaultSortable(true);

	final EditorGridPanel grid = new EditorGridPanel((translated ? "grid-translated" : "grid-untranslated"), 695,
		180, store, columnModel);
	createPagingToolbar(store, grid);

	// final PagingToolbar pag = new PagingToolbar(store);
	// pag.setPageSize(PAGINATION_SIZE);
	// pag.setDisplayInfo(true);
	// pag.setDisplayMsg(i18n.tWithNT("Displaying results {0} - {1} of
	// {2}",
	// "Respect {} values in translations, "
	// + "these will produce: 'Displaying results 1 - 25 of 95465' for
	// instance"));
	// pag.setEmptyMsg(i18n.t("No results to display"));
	// pag.setAfterPageText(i18n.tWithNT("of {0}", "Used to show
	// multiple results: '1 of 30'"));
	// pag.setBeforePageText(i18n.t("Page"));
	// pag.setFirstText(i18n.t("First Page"));
	// pag.setLastText(i18n.t("Last Page"));
	// pag.setNextText(i18n.t("Next Page"));
	// pag.setPrevText(i18n.t("Previous Page"));
	// pag.setRefreshText(i18n.t("Refresh"));
	// grid.setBottomToolbar(pag);
	// grid.setLoadMask(true);
	// grid.setLoadMask(i18n.t("Loading"));
	grid.setClicksToEdit(1);
	// grid.setStripeRows(true);
	// grid.setFrame(true);
	// grid.setSelectionModel(new RowSelectionModel());

	grid.addEditorGridListener(new EditorGridListenerAdapter() {
	    public void onAfterEdit(final GridPanel grid, final Record record, final String field,
		    final Object newValue, final Object oldValue, final int rowIndex, final int colIndex) {
		final String idValue = record.getAsString(id);
		final String trKey = record.getAsString("trKey");
		presenter.doTranslation(idValue, trKey, (String) newValue);
		record.set(field, KuneStringUtils.escapeHtmlLight((String) newValue));
	    }
	});

	grid.addGridCellListener(new GridCellListenerAdapter() {
	    public void onCellDblClick(final GridPanel grid, final int rowIndex, final int colIndex, final EventObject e) {
		final Record record = unTransStore.getRecordAt(rowIndex);
		final String idValue = record.getAsString(id);
		final String trKey = record.getAsString("trKey");
		final String text = record.getAsString("text");
		if (text == null || text.length() == 0) {
		    final String trWithoutNT = removeNT(trKey);
		    record.set("text", trWithoutNT);
		    presenter.doTranslation(idValue, trKey, trWithoutNT);
		}
	    }
	});

	return grid;
    }

    private String removeNT(final String string) {
	return i18n.removeNT(string);
    }

    private void setLanguage(final I18nLanguageDTO language) {
	languageSelectorPanel.setLanguage(language.getCode());
	setLanguage(language.getCode());
    }

    private void setLanguage(final String language) {
	Site.showProgressLoading();
	query(unTransStore, unTransGrid, language);
	query(transStore, transGrid, language);
	Site.hideProgress();
    }

    private String[] splitNT(final String textWithNT) {
	String[] nt;
	final String[] splitted = textWithNT.split(" \\[%NT ");
	if (splitted.length > 1) {
	    nt = splitted[1].split("\\]$");
	    splitted[1] = nt[0];
	}
	return splitted;
    }

}
