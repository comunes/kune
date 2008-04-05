package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Tag;
import org.ourproject.kune.platf.server.domain.TagResult;
import org.ourproject.kune.platf.server.manager.TagManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class TagManagerDefault extends DefaultManager<Tag, Long> implements TagManager {

    private final Tag tagFinder;
    private final Provider<EntityManager> provider;

    @Inject
    public TagManagerDefault(final Provider<EntityManager> provider, final Tag tagFinder) {
        super(provider, Tag.class);
        this.provider = provider;
        this.tagFinder = tagFinder;
    }

    public Tag findByTagName(final String tag) {
        return tagFinder.findByTagName(tag);
    }

    @SuppressWarnings("unchecked")
    public List<TagResult> getSummaryByGroup(final Group group) {
        Query q = provider.get().createNamedQuery(Tag.TAGSGROUPED);
        q.setParameter("group", group);
        List<TagResult> results = q.getResultList();
        return results;
    }
}
