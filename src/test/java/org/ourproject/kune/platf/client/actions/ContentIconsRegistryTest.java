package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;

public class ContentIconsRegistryTest {

    private static final String CONTENT_TYPE_TEST = "somecontenttype";
    private static final String ICON = "someicon";
    private static final String OTHERICON = "othericon";
    private static final String JUSTANOTHERICON = "justanothericon";

    private ContentIconsRegistry reg;

    @Before
    public void before() {
	reg = new ContentIconsRegistry();
    }

    @Test
    public void testBasic() {
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, ICON);
	assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
    }

    @Test
    public void testBasicMimeType() {
	final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, mimeType, ICON);
	assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
	assertEquals("", reg.getContentTypeIcon(CONTENT_TYPE_TEST));
    }

    @Test
    public void testBasicMimeTypeWithDef() {
	final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, mimeType, ICON);
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, OTHERICON);
	assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
	assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
    }

    @Test
    public void testBasicMimeTypeWithDefType() {
	final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
	final BasicMimeTypeDTO genericMimeType = new BasicMimeTypeDTO("image");
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, mimeType, ICON);
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType, OTHERICON);
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, JUSTANOTHERICON);
	assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
	assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType));
	assertEquals(JUSTANOTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
    }

    @Test
    public void testBasicMimeTypeWithOnlyDefType() {
	final BasicMimeTypeDTO mimeType = new BasicMimeTypeDTO("image/png");
	final BasicMimeTypeDTO genericMimeType = new BasicMimeTypeDTO("image");
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType, OTHERICON);
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, JUSTANOTHERICON);
	assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
	assertEquals(OTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, genericMimeType));
	assertEquals(JUSTANOTHERICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST));
    }

    @Test
    public void testNoResult() {
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, new BasicMimeTypeDTO("text", "plain"), ICON);
	assertEquals("", reg.getContentTypeIcon(CONTENT_TYPE_TEST, new BasicMimeTypeDTO("text", "rtf")));
    }

    @Test
    public void testNullBasicMimeType() {
	final BasicMimeTypeDTO mimeType = null;
	reg.registerContentTypeIcon(CONTENT_TYPE_TEST, ICON);
	assertEquals(ICON, reg.getContentTypeIcon(CONTENT_TYPE_TEST, mimeType));
    }
}
