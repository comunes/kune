package org.ourproject.kune.platf.server.manager.file;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.google.inject.Inject;

public abstract class FileJsonUploadManagerAbstract extends FileUploadManagerAbstract {

    private static final long serialVersionUID = 1L;

    @Inject
    I18nTranslationService i18n;

    @Override
    protected void beforeRespond(HttpServletResponse response, Writer w) throws IOException {
        super.beforeRespond(response, w);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
    }

    protected JSONObject createJsonResponse(final boolean success, final String message) {
        return new JSONObject();
    }

    @Override
    protected void onFileUploadException(HttpServletResponse response) throws IOException {
        doResponse(response, createJsonResponse(false, i18n.t("Error: File too large")).toString());
    }

    @Override
    protected void onOtherException(HttpServletResponse response, Exception e) throws IOException {
        super.onOtherException(response, e);
        log.info("Exception: " + e.getCause());
        e.printStackTrace();
        doResponse(response, createJsonResponse(false, i18n.t("Error uploading file")).toString());
    }

    @Override
    protected void onSuccess(HttpServletResponse response) throws IOException {
        doResponse(response, createJsonResponse(true, i18n.t("Success uploading")).toString());
    }

}
