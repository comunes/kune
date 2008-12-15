package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.calclab.suco.client.listener.Listener;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.Template;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

public class AbstractLiveSearcherPanel extends FormPanel {

    protected static final String TEMPLATE_TEXT_PREFIX = "<div class=\"search-item\"><span class=\"kune-IconHyperlink\"><img src=\"";
    protected static final String TEMPLATE_TEXT_SUFFIX = "\" style=\"height: 16px; width: 16px;\" />{shortName}:&nbsp;{longName}</span></div>";

    private static final String LONG_NAME_FIELD = "longName";
    private static final String SHORT_NAME_FIELD = "shortName";
    private static final String ICON_URL_FIELD = "iconUrl";
    private static final String LINK_FIELD = "link";

    public static final int PAGINATION_SIZE = 10;

    public AbstractLiveSearcherPanel(I18nTranslationService i18n, String templateText, String dataProxyUrl,
            final Listener<LinkDTO> listener) {
        DataProxy dataProxy = new HttpProxy(dataProxyUrl, Connection.POST);

        final JsonReader reader = new JsonReader(new RecordDef(
                new FieldDef[] { new StringFieldDef(SHORT_NAME_FIELD), new StringFieldDef(LONG_NAME_FIELD),
                        new StringFieldDef(LINK_FIELD), new StringFieldDef(ICON_URL_FIELD) }));
        reader.setRoot("list");
        reader.setTotalProperty("size");
        reader.setId(SHORT_NAME_FIELD);

        final Store store = new Store(dataProxy, reader);

        store.load(new UrlParam[] { new UrlParam("query", "."), new UrlParam("first", 1),
                new UrlParam("max", PAGINATION_SIZE) });

        super.setBorder(false);
        super.setWidth(275);
        super.setHideLabels(true);

        final Template resultTpl = new Template(templateText);
        final ComboBox cb = new ComboBox();
        cb.setStore(store);
        cb.setEmptyText(i18n.t("Write here to search"));
        cb.setDisplayField(LONG_NAME_FIELD);
        cb.setTypeAhead(true);
        cb.setLoadingText(i18n.t("Searching..."));
        cb.setWidth(268);
        cb.setPageSize(PAGINATION_SIZE);
        cb.setTpl(resultTpl);
        cb.setMode(ComboBox.REMOTE);
        cb.setMinChars(2);
        cb.setSelectOnFocus(false);
        cb.setHideTrigger(true);
        cb.setHideLabel(true);
        // setTitle(i18n.t("User or group"));
        cb.setItemSelector("div.search-item");

        cb.addListener(new ComboBoxListenerAdapter() {
            @Override
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                DeferredCommand.addCommand(new Command() {
                    public void execute() {
                        final LinkDTO link = new LinkDTO(record.getAsString(SHORT_NAME_FIELD),
                                record.getAsString(LONG_NAME_FIELD), record.getAsString(ICON_URL_FIELD),
                                record.getAsString(LINK_FIELD));
                        listener.onEvent(link);
                    }
                });
            }
        });

        super.add(cb);
    }

    public void reset() {
        super.getForm().reset();
    }

}
