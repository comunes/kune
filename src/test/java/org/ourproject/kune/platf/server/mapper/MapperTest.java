package org.ourproject.kune.platf.server.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.state.State;
import org.ourproject.kune.workspace.client.dto.StateDTO;

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
	State c = new State();
	c.setContentRights(new AccessRights(true, true, true));

	StateDTO dto = mapper.map(c, StateDTO.class);
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
	Content d = new Content();
	d.setId(1l);
	Revision revision = new Revision();
	revision.getData().setTitle("title");
	d.addRevision(revision);

	ContentDTO dto = mapper.map(d, ContentDTO.class);
	assertEquals(1l, dto.getId());
	assertEquals("title", dto.getTitle());
    }

    @Test
    public void testFolderMapping() {
	Container container = new Container();
	container.addFolder(new Container());
	container.addFolder(new Container());
	container.addContent(new Content());
	container.addContent(new Content());
	container.addContent(new Content());

	ContainerDTO dto = mapper.map(container, ContainerDTO.class);
	assertEquals(2, dto.getChilds().size());
	assertEquals(3, dto.getContents().size());
	assertTrue(dto.getContents().get(0) instanceof ContentDTO);
	assertTrue(dto.getChilds().get(0) instanceof ContainerDTO);
    }

    @Test
    public void testLicenseMapping() {
	License licenseCC = new License("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "cc1",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "cc2", "cc3");

	License licenseNotCC = new License("gfdl", "GNU Free Documentation License", "nocc1",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "nocc2", "nocc3");

	LicenseDTO dtoCC = mapper.map(licenseCC, LicenseDTO.class);
	LicenseDTO dtoNotCC = mapper.map(licenseNotCC, LicenseDTO.class);

	assertEquals("by-nc-nd", dtoCC.getShortName());
	assertEquals("gfdl", dtoNotCC.getShortName());
	assertEquals("Creative Commons Attribution-NonCommercial-NoDerivs", dtoCC.getLongName());
	assertEquals("GNU Free Documentation License", dtoNotCC.getLongName());
	assertEquals("http://creativecommons.org/licenses/by-nc-nd/3.0/", dtoCC.getUrl());
	assertEquals("http://www.gnu.org/copyleft/fdl.html", dtoNotCC.getUrl());
	assertTrue(dtoCC.isCC());
	assertFalse(dtoNotCC.isCC());
	assertFalse(dtoCC.isCopyleft());
	assertTrue(dtoNotCC.isCopyleft());
	assertFalse(dtoCC.isDeprecated());
	assertFalse(dtoNotCC.isDeprecated());
	assertEquals("cc1", dtoCC.getDescription());
	assertEquals("cc2", dtoCC.getRdf());
	assertEquals("cc3", dtoCC.getImageUrl());
	assertEquals("nocc1", dtoNotCC.getDescription());
	assertEquals("nocc2", dtoNotCC.getRdf());
	assertEquals("nocc3", dtoNotCC.getImageUrl());
    }
}
