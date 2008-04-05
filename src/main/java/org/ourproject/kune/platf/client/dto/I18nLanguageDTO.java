
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class I18nLanguageDTO implements IsSerializable {

    private String code;
    private String englishName;
    private String nativeName;
    private String direction;
    private String pluralization;

    public String getCode() {
        return code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getDirection() {
        return direction;
    }

    public String getPluralization() {
        return pluralization;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setEnglishName(final String englishName) {
        this.englishName = englishName;
    }

    public void setNativeName(final String nativeName) {
        this.nativeName = nativeName;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public void setPluralization(final String pluralization) {
        this.pluralization = pluralization;
    }

}
