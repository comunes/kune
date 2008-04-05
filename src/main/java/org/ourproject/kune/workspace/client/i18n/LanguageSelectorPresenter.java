package org.ourproject.kune.workspace.client.i18n;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.state.Session;

public class LanguageSelectorPresenter implements LanguageSelectorComponent {

    private LanguageSelectorView view;
    private final Session session;

    public LanguageSelectorPresenter(final Session session) {
        this.session = session;

    }

    public void init(final LanguageSelectorView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public String getSelectedLanguage() {
        return view.getLanguage();
    }

    public Object[][] getLanguages() {
        return session.getLanguagesArray();
    }

    public void setLanguage(final I18nLanguageDTO language) {
        view.setLanguage(language.getCode());
    }
}
