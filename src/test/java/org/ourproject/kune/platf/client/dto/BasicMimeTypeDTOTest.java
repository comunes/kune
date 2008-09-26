package org.ourproject.kune.platf.client.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicMimeTypeDTOTest {

    @Test
    public void testNoSubtype() {
	final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime");
	assertEquals("somesimplemime", mime.getType());
	assertEquals(null, mime.getSubtype());
	assertEquals("somesimplemime", mime.toString());
    }

    @Test
    public void testNoSubtypeWithSlash() {
	final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime/");
	assertEquals("somesimplemime", mime.getType());
	assertEquals(null, mime.getSubtype());
	assertEquals("somesimplemime", mime.toString());
    }

    @Test
    public void testNull() {
	final BasicMimeTypeDTO mime = new BasicMimeTypeDTO(null);
	assertEquals(null, mime.getType());
	assertEquals(null, mime.getSubtype());
    }

    @Test
    public void testSeveralSlashs() {
	final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime/subtype/thisisposible?");
	assertEquals("somesimplemime", mime.getType());
	assertEquals("subtype/thisisposible?", mime.getSubtype());
	assertEquals("somesimplemime/subtype/thisisposible?", mime.toString());
    }

    @Test
    public void testSubtype() {
	final BasicMimeTypeDTO mime = new BasicMimeTypeDTO("somesimplemime/subtype");
	assertEquals("somesimplemime", mime.getType());
	assertEquals("subtype", mime.getSubtype());
	assertEquals("somesimplemime/subtype", mime.toString());
    }
}
