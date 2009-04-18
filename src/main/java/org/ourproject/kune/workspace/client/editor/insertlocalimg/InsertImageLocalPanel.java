package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPanel;
import org.ourproject.kune.workspace.client.search.AbstractLiveSearcherField;
import org.ourproject.kune.workspace.client.search.SearcherContants;

import com.calclab.suco.client.events.Listener;
import com.gwtext.client.core.UrlParam;

public class InsertImageLocalPanel extends InsertImageAbstractPanel implements InsertImageLocalView {

    protected String src;

    public InsertImageLocalPanel(final InsertImageLocalPresenter presenter, final I18nTranslationService i18n,
            final FileDownloadUtils downloadUtils) {
        super(i18n.t("Local"), presenter);

        AbstractLiveSearcherField cb = new AbstractLiveSearcherField(i18n,
                SearcherContants.CONTENT_TEMPLATE_TEXT_PREFIX
                        + downloadUtils.getLogoImageUrl(new StateToken("{shortName}"))
                        + SearcherContants.CONTENT_TEMPLATE_TEXT_SUFFIX, SearcherContants.CONTENT_DATA_PROXY_URL,
                new Listener<LinkDTO>() {
                    public void onEvent(final LinkDTO link) {
                        src = WindowUtils.getPublicHost() + downloadUtils.getImageUrl(new StateToken(link.getLink()));
                    }
                });
        cb.setLabel(i18n.t("Local images"));
        cb.setHideLabel(false);
        cb.setAllowBlank(false);
        cb.setWidth(220);
        cb.setStoreBaseParams(new UrlParam[] { new UrlParam(SearcherContants.MIMETYPE_PARAM, BasicMimeTypeDTO.IMAGE) });

        super.insert(0, cb);
    }

    @Override
    public String getSrc() {
        return src;
    }
}
