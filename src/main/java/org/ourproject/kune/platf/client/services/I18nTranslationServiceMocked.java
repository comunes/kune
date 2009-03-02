package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.ui.TextUtils;

@Deprecated
public class I18nTranslationServiceMocked extends I18nTranslationService {
    public String t(final String text) {
        String encodeText = TextUtils.escapeHtmlLight(text);
        String translation = removeNT(encodeText);
        return decodeHtml(translation);
    }
}
