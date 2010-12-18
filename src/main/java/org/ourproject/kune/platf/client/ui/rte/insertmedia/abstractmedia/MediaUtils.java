package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ContentPosition;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;

public class MediaUtils {

    public static final String DOC_URL_TAG = "###DOC_URL###";

    private final Session session;

    private final FileDownloadUtils downloadUtils;

    public MediaUtils(final Session session, final FileDownloadUtils downloadUtils) {
        this.session = session;
        this.downloadUtils = downloadUtils;
    }

    public String getAviEmbed(final StateToken token) {
        return setCenterPosition(session.getInitData().getAviEmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token)));
    }

    public String getFlvEmbed(final StateToken token) {
        return setCenterPosition(session.getInitData().getFlvEmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token)));
    }

    public String getMp3Embed(final StateToken token) {
        return setCenterPosition(session.getInitData().getMp3EmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token)));
    }

    public String getOggEmbed(final StateToken token) {
        return setCenterPosition(session.getInitData().getOggEmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token)));
    }

    private String setCenterPosition(final String elementCode) {
        return ContentPosition.setCenterPosition(elementCode);
    }

}
