package org.ourproject.kune.blogs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public interface BlogReaderControl {

    void setRights(AccessRightsDTO accessRights);

    View getView();

}
