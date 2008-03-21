package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.ui.KuneStringUtils;

public class I18nTranslationServiceMocked extends I18nTranslationService {
    public String t(final String text) {
        String encodeText = KuneStringUtils.escapeHtmlLight(text);
        String translation = removeNT(encodeText);
        return decodeHtml(translation);
    }
}
