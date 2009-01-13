package org.ourproject.kune.workspace.client.editor.insert.linklocal;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElementView;
import org.ourproject.kune.workspace.client.search.AbstractLiveSearcherPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener;

public class TextEditorInsertLinkLocalPanel extends AbstractLiveSearcherPanel implements TextEditorInsertLinkLocalView {

    private static final String DATA_PROXY_URL = "/kune/json/ContentJSONService/search";

    public TextEditorInsertLinkLocalPanel(final TextEditorInsertLinkLocalPresenter presenter,
            final WorkspaceSkeleton ws, I18nTranslationService i18n, FileDownloadUtils downloadUtils) {
        super(i18n, TEMPLATE_TEXT_PREFIX + downloadUtils.getLogoImageUrl(new StateToken("{shortName}"))
                + TEMPLATE_TEXT_SUFFIX, DATA_PROXY_URL, new Listener<LinkDTO>() {
            public void onEvent(LinkDTO link) {
                // FIXME
                presenter.onInsert("", WindowUtils.getLocation().getHref() + link.getLink());
            }
        });
        super.setTitle(i18n.t("Local link"));
        super.setFrame(true);
        super.setHeight(TextEditorInsertElementView.HEIGHT);
        super.setPaddings(20);
    }
}
