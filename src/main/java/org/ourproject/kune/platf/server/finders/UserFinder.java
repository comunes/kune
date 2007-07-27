package org.ourproject.kune.platf.server.finders;

import java.util.List;

import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

public interface UserFinder {
    @Finder(query = "from User") List<User> getAll();
    @Finder(query = "from User where email = :email") User getByEmail(@Named("email") String email);

}
