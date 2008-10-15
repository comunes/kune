package org.ourproject.kune.platf.client.ui.download;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class FileDownloadUtils {

    private static final String DOWNLOADSERVLET = "/kune/servlets/FileDownloadManager";

    private final Session session;

    public FileDownloadUtils(final Session session) {
        this.session = session;
    }

    public void downloadFile(final StateToken token) {
        final String url = calculateUrl(token, true);
        DOM.setElementAttribute(RootPanel.get("__download").getElement(), "src", url);
    }

    public String getImageResizedUrl(final StateToken token, ImageSize imageSize) {
        return calculateUrl(token, false) + "&" + FileParams.IMGSIZE + "=" + imageSize;
    }

    public String getImageUrl(final StateToken token) {
        return calculateUrl(token, false);
    }

    private String calculateUrl(final StateToken token, final boolean download) {
        final String url = DOWNLOADSERVLET + "?" + FileParams.TOKEN + "=" + token + "&" + FileParams.HASH + "="
                + session.getUserHash() + "&" + FileParams.DOWNLOAD + "=" + (download ? "true" : "false");
        return url;
    }

}
