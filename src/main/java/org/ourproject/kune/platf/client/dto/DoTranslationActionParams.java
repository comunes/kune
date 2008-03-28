package org.ourproject.kune.platf.client.dto;

public class DoTranslationActionParams {

    String id;
    String trKey;
    String text;

    public DoTranslationActionParams(final String id, final String trKey, final String text) {
        this.id = id;
        this.trKey = trKey;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getTrKey() {
        return trKey;
    }

    public String getText() {
        return text;
    }

}
