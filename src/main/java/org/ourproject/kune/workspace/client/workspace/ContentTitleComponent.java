
package org.ourproject.kune.workspace.client.workspace;

import java.util.Date;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.StateDTO;

public interface ContentTitleComponent extends Component {

    void setState(StateDTO state);

    void setContentDate(Date publishedOn);

}
