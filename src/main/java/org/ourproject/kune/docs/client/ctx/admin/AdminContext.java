
package org.ourproject.kune.docs.client.ctx.admin;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;

public interface AdminContext {
    View getView();

    void setState(StateDTO content);
}
