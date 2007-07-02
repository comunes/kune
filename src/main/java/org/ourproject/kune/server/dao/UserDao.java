package org.ourproject.kune.server.dao;

import java.util.List;

import org.ourproject.kune.server.model.KUser;

public interface UserDao {
    public KUser get(Long id);
    public KUser persist(KUser user);
    public KUser update(KUser user);
    public List<KUser> getAll();
}
