package org.ourproject.kune.platf.client.dto;

public class GetTranslationActionParams {

    private final String language;
    private final String text;

    public GetTranslationActionParams(final String language, final String text) {
        this.language = language;
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public String getText() {
        return text;
    }

}
