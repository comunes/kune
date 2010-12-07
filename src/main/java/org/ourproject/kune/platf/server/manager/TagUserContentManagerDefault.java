package org.ourproject.kune.platf.server.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Tag;
import org.ourproject.kune.platf.server.domain.TagCloudResult;
import org.ourproject.kune.platf.server.domain.TagCount;
import org.ourproject.kune.platf.server.domain.TagUserContent;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class TagUserContentManagerDefault extends DefaultManager<TagUserContent, Long> implements TagUserContentManager {
    private final Provider<EntityManager> provider;
    private final TagManager tagManager;
    private final TagUserContent finder;

    @Inject
    public TagUserContentManagerDefault(final Provider<EntityManager> provider, final TagManager tagManager,
            final TagUserContent finder) {
        super(provider, TagUserContent.class);
        this.provider = provider;
        this.tagManager = tagManager;
        this.finder = finder;
    }

    public List<Tag> find(final User user, final Content content) {
        return finder.findTags(user, content);
    }

    public TagCloudResult getTagCloudResultByGroup(final Group group) {
        return new TagCloudResult(getSummaryByGroup(group), getMaxCount(group), getMinCount(group));
    }

    public String getTagsAsString(final User user, final Content content) {
        String tagConcatenated = "";
        if (user.getId() != null) {
            // FIXME: User must be persisted (this fails on tests)
            final List<Tag> tags = find(user, content);
            for (final Tag tag : tags) {
                tagConcatenated += " " + tag.getName();
            }
        }
        return tagConcatenated.replaceFirst(" ", "");
    }

    public void remove(final User user, final Content content) {
        for (final TagUserContent item : finder.find(user, content)) {
            provider.get().remove(item);
        }
    }

    public void setTags(final User user, final Content content, final String tags) {
        final ArrayList<String> tagsStripped = TextUtils.splitTags(tags);
        final ArrayList<Tag> tagList = new ArrayList<Tag>();

        for (final String tagString : tagsStripped) {
            Tag tag;
            try {
                tag = tagManager.findByTagName(tagString);
            } catch (final NoResultException e) {
                tag = new Tag(tagString);
                tagManager.persist(tag);
            }
            if (!tagList.contains(tag)) {
                tagList.add(tag);
            }
        }
        remove(user, content);
        for (final Tag tag : tagList) {
            final TagUserContent tagUserContent = new TagUserContent(tag, user, content);
            persist(tagUserContent);
        }
    }

    private int getMaxCount(final Group group) {
        final Query q = provider.get().createNamedQuery(TagUserContent.TAGSMAXGROUPED);
        q.setParameter("group", group);
        final List resultList = q.getResultList();
        return (resultList.size() == 0 ? 0 : ((Long) resultList.get(0)).intValue());
    }

    @SuppressWarnings("rawtypes")
    private int getMinCount(final Group group) {
        final Query q = provider.get().createNamedQuery(TagUserContent.TAGSMINGROUPED);
        q.setParameter("group", group);
        final List resultList = q.getResultList();
        return (resultList.size() == 0 ? 0 : ((Long) resultList.get(0)).intValue());
    }

    private List<TagCount> getSummaryByGroup(final Group group) {
        final Query q = provider.get().createNamedQuery(TagUserContent.TAGSGROUPED);
        q.setParameter("group", group);
        final List<TagCount> results = q.getResultList();
        return results;
    }
}
