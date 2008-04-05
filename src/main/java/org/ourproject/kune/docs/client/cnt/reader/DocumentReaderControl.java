
package org.ourproject.kune.docs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public interface DocumentReaderControl {

    void setRights(AccessRightsDTO accessRights);

    View getView();

}
