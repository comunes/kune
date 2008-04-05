package org.ourproject.kune.workspace.client.i18n;

import org.ourproject.kune.platf.client.View;

public interface LanguageSelectorView extends View {

    void reset();

    String getLanguage();

    void setLanguage(String code);

}
