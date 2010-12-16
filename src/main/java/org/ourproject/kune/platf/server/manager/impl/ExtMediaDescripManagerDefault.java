package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.manager.ExtMediaDescripManager;

import cc.kune.domain.ExtMediaDescrip;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ExtMediaDescripManagerDefault extends DefaultManager<ExtMediaDescrip, Long> implements
        ExtMediaDescripManager {

    private final ExtMediaDescrip extMediaFinder;

    @Inject
    public ExtMediaDescripManagerDefault(final Provider<EntityManager> provider, final ExtMediaDescrip extMediaFinder) {
        super(provider, ExtMediaDescrip.class);
        this.extMediaFinder = extMediaFinder;
    }

    public List<ExtMediaDescrip> getAll() {
        return extMediaFinder.getAll();
    }
}
