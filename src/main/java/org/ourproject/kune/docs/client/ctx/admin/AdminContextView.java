
package org.ourproject.kune.docs.client.ctx.admin;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;

public interface AdminContextView extends View {

    void setAccessLists(AccessListsDTO accessLists);

    void setTags(String tags);

    void setLanguage(I18nLanguageDTO language);

    void setAuthors(List<UserSimpleDTO> authors);

    void setPublishedOn(Date publishedOn);

    void removeLangComponent();

    void removeAccessListComponent();

    void removePublishedOnComponent();

    void removeTagsComponent();

    void removeAuthorsComponent();

}
