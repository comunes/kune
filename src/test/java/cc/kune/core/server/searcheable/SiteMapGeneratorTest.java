package cc.kune.core.server.searcheable;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;

import com.google.inject.Inject;


public class SiteMapGeneratorTest extends IntegrationTest {

    @Inject
    SiteMapGenerator generator;

    @Before
    public void before() {
        new IntegrationTestHelper(true, this);
    }

    @Test
    public void generateSitemap() {
        generator.generate();
    }
}
