
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.Kune;

public class ContentSubTitlePresenter implements ContentSubTitleComponent {

    private ContentSubTitleView view;

    public void init(final ContentSubTitleView view) {
        this.view = view;
    }

    public void setState(final StateDTO state) {
        if (state.hasDocument()) {
            view.setContentSubTitleLeft(Kune.I18N.tWithNT("by: [%s]", "used in a list of authors",
                    ((UserSimpleDTO) state.getAuthors().get(0)).getName()));
            view.setContentSubTitleLeftVisible(true);
        } else {
            view.setContentSubTitleLeftVisible(false);
        }
        if (state.getLanguage() != null) {
            String langName = state.getLanguage().getEnglishName();
            setContentLanguage(langName);
            view.setContentSubTitleRightVisible(true);
        } else {
            view.setContentSubTitleRightVisible(false);
        }
    }

    public void setContentLanguage(final String langName) {
        view.setContentSubTitleRight(Kune.I18N.t("Language: [%s]", langName));
    }

    public View getView() {
        return view;
    }

}
