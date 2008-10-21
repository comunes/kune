package org.ourproject.kune.platf.client.ui.download;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.utils.Url;
import org.ourproject.kune.platf.client.utils.UrlParam;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class FileDownloadUtils {

    private static final String DOWNLOADSERVLET = "/kune/servlets/FileDownloadManager";
    private static final String LOGODOWNLOADSERVLET = "/kune/servlets/EntityLogoDownloadManager";

    private final Session session;

    public FileDownloadUtils(final Session session) {
        this.session = session;
    }

    public void downloadFile(final StateToken token) {
        final String url = calculateUrl(token, true);
        DOM.setElementAttribute(RootPanel.get("__download").getElement(), "src", url);
    }

    public String getImageResizedUrl(final StateToken token, ImageSize imageSize) {
        return calculateUrl(token, false) + "&" + new UrlParam(FileParams.IMGSIZE, imageSize.toString());
    }

    public String getImageUrl(final StateToken token) {
        return calculateUrl(token, false);
    }

    public String getLogoImageUrl(StateToken token) {
        return new Url(LOGODOWNLOADSERVLET, new UrlParam(FileParams.TOKEN, token.toString())).toString();
    }

    private String calculateUrl(final StateToken token, final boolean download) {
        return new Url(DOWNLOADSERVLET, new UrlParam(FileParams.TOKEN, token.toString()), new UrlParam(FileParams.HASH,
                session.getUserHash()), new UrlParam(FileParams.DOWNLOAD, download)).toString();
    }

}
