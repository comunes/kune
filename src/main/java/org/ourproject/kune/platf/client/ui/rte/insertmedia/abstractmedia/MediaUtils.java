package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;

public class MediaUtils {

    public static final String DOC_FLV_URL_TAG = "###DOC_FLV_URL###";

    private final Session session;

    private final FileDownloadUtils downloadUtils;

    public MediaUtils(final Session session, final FileDownloadUtils downloadUtils) {
        this.session = session;
        this.downloadUtils = downloadUtils;
    }

    public String getEmbed(final StateToken token) {
        return session.getInitData().getMediaEmbedObject().replace(DOC_FLV_URL_TAG,
                session.getSiteUrl() + downloadUtils.getUrl(token));
    }
}
