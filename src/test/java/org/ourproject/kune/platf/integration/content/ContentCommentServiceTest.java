package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

public class ContentCommentServiceTest extends ContentServiceIntegrationTest {

    private StateContainerDTO defaultContent;

    @Test
    public void commentDefaultContent() throws Exception {
        doLogin();
        final String commentText = "Some comment";
        final CommentDTO commentDTO = contentService.addComment(session.getHash(), defaultContent.getStateToken(),
                commentText);
        assertEquals(commentDTO.getText(), commentText);
        assertEquals(commentDTO.getPositiveVotersCount(), 0);
        assertEquals(commentDTO.getNegativeVotersCount(), 0);
        assertEquals(commentDTO.getAbuseInformersCount(), 0);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void commentWithoutLoginMustFail() throws DefaultException {
        final String commentText = "Some comment";
        contentService.addComment(session.getHash(), defaultContent.getStateToken(), commentText);
    }

    @Before
    public void init() throws Exception {
        new IntegrationTestHelper(this);
        defaultContent = getSiteDefaultContent();
    }

}
