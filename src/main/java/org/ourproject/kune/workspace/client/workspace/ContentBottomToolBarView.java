
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public interface ContentBottomToolBarView extends View {

    void setRate(Double value, Integer rateByUsers);

    void setRateIt(Double currentUserRate);

    void setRateVisible(boolean b);

    void setRateItVisible(boolean visible);

}
