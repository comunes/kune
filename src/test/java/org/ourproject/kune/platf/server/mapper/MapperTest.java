package org.ourproject.kune.platf.server.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.ContentDescriptorDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.inject.Inject;

public class MapperTest {
    @Inject
    Mapper mapper;

    @Before
    public void inject() {
	TestHelper.inject(this);
    }

    @Test
    public void testContentMapping() {
	Content c = new Content();
	c.setContentRights(new AccessRights(true, true, true));

	ContentDTO dto = mapper.map(c, ContentDTO.class);
	assertEquals(c.getContentRights().isAdministrable(), dto.getContentRights().isAdministrable);
    }

    @Test
    public void testGroupMapping() {
	Group group = new Group("shortName", "name");
	GroupDTO dto = mapper.map(group, GroupDTO.class);
	assertEquals(group.getLongName(), dto.getLongName());
	assertEquals(group.getShortName(), dto.getShortName());
    }

    @Test
    public void testContentDescriptorMapping() {
	ContentDescriptor d = new ContentDescriptor();
	d.setId(1l);
	Revision revision = new Revision();
	revision.getData().setTitle("title");
	d.addRevision(revision);

	ContentDescriptorDTO dto = mapper.map(d, ContentDescriptorDTO.class);
	assertEquals(1l, dto.getId());
	assertEquals("title", dto.getTitle());
    }

    @Test
    public void testFolderMapping() {
	Folder folder = new Folder();
	folder.addFolder(new Folder());
	folder.addFolder(new Folder());
	folder.addContent(new ContentDescriptor());
	folder.addContent(new ContentDescriptor());
	folder.addContent(new ContentDescriptor());

	FolderDTO dto = mapper.map(folder, FolderDTO.class);
	assertEquals(2, dto.getChilds().size());
	assertEquals(3, dto.getContents().size());
	assertTrue(dto.getContents().get(0) instanceof ContentDescriptorDTO);
	assertTrue(dto.getChilds().get(0) instanceof FolderDTO);
    }
}
