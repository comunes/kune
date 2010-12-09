package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.services.SearcherConstants;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPanel;
import org.ourproject.kune.workspace.client.search.AbstractLiveSearcherField;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.dto.LinkDTO;
import cc.kune.core.shared.dto.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class InsertImageLocalPanel extends InsertImageAbstractPanel implements InsertImageLocalView {

    protected String src;

    public InsertImageLocalPanel(final InsertImageLocalPresenter presenter, final I18nTranslationService i18n,
            final FileDownloadUtils downloadUtils, final Session session) {
        super(i18n.t("Local"), presenter);

        final AbstractLiveSearcherField cb = new AbstractLiveSearcherField(i18n,
                SearcherConstants.CONTENT_TEMPLATE_TEXT_PREFIX
                        + downloadUtils.getLogoImageUrl(new StateToken("{shortName}"))
                        + SearcherConstants.CONTENT_TEMPLATE_TEXT_SUFFIX, SearcherConstants.CONTENT_DATA_PROXY_URL,
                new Listener<LinkDTO>() {
                    public void onEvent(final LinkDTO link) {
                        src = session.getSiteUrl() + downloadUtils.getImageUrl(new StateToken(link.getLink()));
                    }
                });
        cb.setLabel(i18n.t("Local images"));
        cb.setHideLabel(false);
        cb.setAllowBlank(false);
        cb.setWidth(220);
        cb.addListener(new FieldListenerAdapter() {
            @Override
            public void onFocus(final Field field) {
                cb.setStoreBaseParams(new UrlParam[] {
                        new UrlParam(SearcherConstants.GROUP_PARAM, presenter.getCurrentGroupName()),
                        new UrlParam(SearcherConstants.MIMETYPE_PARAM, BasicMimeTypeDTO.IMAGE) });
            }
        });

        super.insert(0, cb);
    }

    @Override
    public String getSrc() {
        return src;
    }
}
