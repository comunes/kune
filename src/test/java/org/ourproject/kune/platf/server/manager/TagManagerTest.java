package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Tag;

import com.google.inject.Inject;

public class TagManagerTest extends PersistenceTest {
    @Inject
    TagManager tagManager;
    @Inject
    ContentManager contentManager;
    @Inject
    Tag tagFinder;
    private Tag tag;

    @Before
    public void insertData() {
        openTransaction();
        tag = new Tag("foo1");
        tagManager.persist(tag);
    }

    @Test
    public void testTagCreation() {
        assertNotNull(tag.getId());
        ;

        assertNotNull(tagFinder.findByTagName("foo1"));
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }
}
