
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class I18nTranslationDTO implements IsSerializable {

    private Long id;
    private String trKey;
    private String text;

    public I18nTranslationDTO() {
        this(null, null, null);
    }

    public I18nTranslationDTO(final Long id, final String trKey, final String text) {
        this.id = id;
        this.trKey = trKey;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTrKey() {
        return trKey;
    }

    public void setTrKey(final String trKey) {
        this.trKey = trKey;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
