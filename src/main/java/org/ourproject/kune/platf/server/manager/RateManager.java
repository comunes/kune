
package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.User;

public interface RateManager extends Manager<Rate, Long> {

    Rate find(User user, Content content);

    Double getRateAvg(Content content);

    Long getRateByUsers(Content content);

}
