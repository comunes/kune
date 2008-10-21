package org.ourproject.kune.platf.client.ui.upload;

import org.ourproject.kune.platf.client.ui.download.FileParams;

import com.gwtext.client.core.UrlParam;

public class AbstractUploader {

    public UrlParam[] genUploadParams(final String userhash, final String currentStateToken) {
        // Warning take into account param[size]
        final UrlParam param[] = new UrlParam[2];
        param[0] = new UrlParam(FileParams.HASH, userhash);
        param[1] = new UrlParam(FileParams.TOKEN, currentStateToken);
        return param;
    }
}
