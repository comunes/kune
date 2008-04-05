
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class I18nLanguageSimpleDTO implements IsSerializable {

    private String code;
    private String englishName;

    public String getCode() {
        return code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setEnglishName(final String englishName) {
        this.englishName = englishName;
    }

}
