package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Tag;
import org.ourproject.kune.platf.server.manager.TagManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class TagManagerDefault extends DefaultManager<Tag, Long> implements TagManager {

    private final Tag tagFinder;

    @Inject
    public TagManagerDefault(final Provider<EntityManager> provider, final Tag tagFinder) {
        super(provider, Tag.class);
        this.tagFinder = tagFinder;
    }

    public Tag findByTagName(final String tag) {
        return tagFinder.findByTagName(tag);
    }
}
