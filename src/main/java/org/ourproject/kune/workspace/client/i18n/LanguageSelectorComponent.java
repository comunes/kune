package org.ourproject.kune.workspace.client.i18n;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;

public interface LanguageSelectorComponent extends Component {

    String getSelectedLanguage();

    void setLanguage(I18nLanguageDTO language);

}
