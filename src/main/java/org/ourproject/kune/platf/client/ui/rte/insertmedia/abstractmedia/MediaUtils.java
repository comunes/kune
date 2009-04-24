package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;

public class MediaUtils {

    public static final String DOC_URL_TAG = "###DOC_URL###";

    private final Session session;

    private final FileDownloadUtils downloadUtils;

    public MediaUtils(final Session session, final FileDownloadUtils downloadUtils) {
        this.session = session;
        this.downloadUtils = downloadUtils;
    }

    public String getAviEmbed(final StateToken token) {
        return session.getInitData().getAviEmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token));
    }

    public String getFlvEmbed(final StateToken token) {
        return session.getInitData().getFlvEmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token));
    }

    public String getMp3Embed(final StateToken token) {
        return session.getInitData().getMp3EmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token));
    }

    public String getOggEmbed(final StateToken token) {
        return session.getInitData().getOggEmbedObject().replace(DOC_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token));
    }

}
